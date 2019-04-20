# JVM类的加载机制


# 触发JVM进行Full GC的情况及应对策略
- 堆内存划分为新生代[Eden]、老年代[Survivor] 和 永久代[Tenured/Old]（1.8中无永久代，使用metaspace实现）三块区域，如下图所示：

![堆内存](https://img-blog.csdn.net/20150731163641941?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

从年轻代空间（包括 Eden 和 Survivor 区域）回收内存被称为 Minor GC，对老年代GC称为Major GC,而Full GC是对整个堆来说的，在最近几个版本的JDK里默认包括了对永生带即方法区的回收（JDK8中无永生带了），出现Full GC的时候经常伴随至少一次的Minor GC,但非绝对的。Major GC的速度一般会比Minor GC慢10倍以上。下边看看有那种情况触发JVM进行Full GC及应对策略。

## 1. System.gc()方法的调用
此方法的调用是建议JVM进行Full GC,虽然只是建议而非一定,但很多情况下它会触发 Full GC,从而增加Full GC的频率,也即增加了间歇性停顿的次数。强烈影响系建议能不使用此方法就别使用，让虚拟机自己去管理它的内存，可通过通过-XX:+ DisableExplicitGC来禁止RMI调用System.gc。

## 2. 老年代代空间不足
老年代空间只有在新生代对象转入及创建为大对象、大数组时才会出现不足的现象，当执行Full GC后空间仍然不足，则抛出如下错误：
java.lang.OutOfMemoryError: Java heap space
为避免以上两种状况引起的Full GC，调优时应尽量做到让对象在Minor GC阶段被回收、让对象在新生代多存活一段时间及不要创建过大的对象及数组。

## 3. 永生区空间不足
JVM规范中运行时数据区域中的方法区，在HotSpot虚拟机中又被习惯称为永生代或者永生区，Permanet Generation中存放的为一些class的信息、常量、静态变量等数据，当系统中要加载的类、反射的类和调用的方法较多时，Permanet Generation可能会被占满，在未配置为采用CMS GC的情况下也会执行Full GC。如果经过Full GC仍然回收不了，那么JVM会抛出如下错误信息：
java.lang.OutOfMemoryError: PermGen space
为避免Perm Gen占满造成Full GC现象，可采用的方法为增大Perm Gen空间或转为使用CMS GC。

## 4. CMS GC时出现promotion failed和concurrent mode failure
① 由于采用CMS进行老年代GC的程序而言，尤其要注意GC日志中是否有promotion failed和concurrent mode failure两种状况，当这两种状况出现时可能会触发Full GC。
② promotion failed是在进行Minor GC时，survivor space放不下、对象只能放入老年代，而此时老年代也放不下造成的；concurrent mode failure是在执行CMS GC的过程中同时有对象要放入老年代，而此时老年代空间不足造成的（有时候“空间不足”是CMS GC时当前的浮动垃圾过多导致暂时性的空间不足触发Full GC）。
对措施为：增大survivor space、老年代空间或调低触发并发GC的比率，但在JDK 5.0+、6.0+的版本中有可能会由于JDK的bug29导致CMS在remark完毕后很久才触发sweeping动作。对于这种状况，可通过设置-XX: CMSMaxAbortablePrecleanTime=5（单位为ms）来避免。

## 5. 统计得到的Minor GC晋升到旧生代的平均大小大于老年代的剩余空间
① 这是一个较为复杂的触发情况，Hotspot为了避免由于新生代对象晋升到旧生代导致旧生代空间不足的现象，在进行Minor GC时，做了一个判断，如果之前统计所得到的Minor GC晋升到旧生代的平均大小大于旧生代的剩余空间，那么就直接触发Full GC。
② 例如程序第一次触发Minor GC后，有6MB的对象晋升到旧生代，那么当下一次Minor GC发生时，首先检查旧生代的剩余空间是否大于6MB，如果小于6MB，则执行Full GC。
③ 当新生代采用PS GC时，方式稍有不同，PS GC是在Minor GC后也会检查，例如上面的例子中第一次Minor GC后，PS GC会检查此时旧生代的剩余空间是否大于6MB，如小于，则触发对旧生代的回收。
⑤ 除了以上4种状况外，对于使用RMI来进行RPC或管理的Sun JDK应用而言，默认情况下会一小时执行一次Full GC。可通过在启动时通过-java -Dsun.rmi.dgc.client.gcInterval=3600000来设置Full GC执行的间隔时间或通过-XX:+ DisableExplicitGC来禁止RMI调用System.gc

## 6. 堆中分配很大的对象
① 所谓大对象，是指需要大量连续内存空间的java对象，例如很长的数组，此种对象会直接进入老年代，而老年代虽然有很大的剩余空间，但是无法找到足够大的连续空间来分配给当前对象，此种情况就会触发JVM进行Full GC。
② 为了解决这个问题，CMS垃圾收集器提供了一个可配置的参数，即-XX:+UseCMSCompactAtFullCollection开关参数，用于在“享受”完Full GC服务之后额外免费赠送一个碎片整理的过程，内存整理的过程无法并发的，空间碎片问题没有了，但提顿时间不得不变长了，JVM设计者们还提供了另外一个参数 -XX:CMSFullGCsBeforeCompaction,这个参数用于设置在执行多少次不压缩的Full GC后,跟着来一次带压缩的。

# JVM调优
## JVM调优工具
- Jconsole：jdk自带，功能简单，但是可以在系统有一定负荷的情况下使用。对垃圾回收算法有很详细的跟踪。详细说明参考这里
- JProfiler：商业软件，需要付费。功能强大
- VisualVM：JDK自带，功能强大，与JProfiler类似。推荐

## 如何调优
观察内存释放情况、集合类检查、对象树
==上面这些调优工具都提供了强大的功能，但是总的来说一般分为以下几类功能：==

### 堆信息查看
#### PermGen
![堆信息](http://dl.iteye.com/upload/picture/pic/51401/7b7ece1a-1596-3240-a37d-c3a7d06e2c01.png)
- 可查看堆空间大小分配（年轻代、年老代、持久代分配）
- 提供即时的垃圾回收功能
- 垃圾监控（长时间监控回收情况）

#### Drop
![堆 Drop](http://dl.iteye.com/upload/picture/pic/51403/48059c43-43ff-3d91-8699-78e6ea8af8a6.png)
- 查看堆内类、对象信息查看：数量、类型等

#### java2d
![java2d](http://dl.iteye.com/upload/picture/pic/51405/dc26b52b-62d5-320d-a627-6a88e6b57d8f.png)
- 对象引用查看

#### 可以解决以下问题：

- 年老代年轻代大小划分是否合理
- 内存泄漏
- 垃圾回收算法设置是否合理

### 线程监控
![线程监控](http://dl.iteye.com/upload/picture/pic/51407/4e7705f6-75f6-3549-8976-dce68396bbc8.png)
- 线程信息监控：系统线程数量
- 线程状态监控：各个线程都处在什么样的状态下
> - Dump线程详细信息：查看线程内部运行情况
> - 死锁检查

### 热点分析
![热点分析](http://dl.iteye.com/upload/picture/pic/51413/ece5e1f6-d7a6-3aa6-96d0-5f9d43f69808.png)
- CPU热点：检查系统哪些方法占用的大量CPU时间
- 内存热点：检查哪些对象在系统中数量最大（一定时间内存活对象和销毁对象一起统计）
> 这两个东西对于系统优化很有帮助。我们可以根据找到的热点，有针对性的进行系统的瓶颈查找和进行系统优化，而不是漫无目的的进行所有代码的优化

### 快照
① 快照是系统运行到某一时刻的一个定格。在我们进行调优的时候，不可能用眼睛去跟踪所有系统变化，依赖快照功能，我们就可以进行系统两个不同运行时刻，对象（或类、线程等）的不同，以便快速找到问题
② 举例说，我要检查系统进行垃圾回收以后，是否还有该收回的对象被遗漏下来的了。那么，我可以在进行垃圾回收前后，分别进行一次堆情况的快照，然后对比两次快照的对象情况

### 内存泄漏检测
- 内存泄漏是比较常见的问题，而且解决方法也比较通用，这里可以重点说一下，而线程、热点方面的问题则是具体问题具体分析了。
- 内存泄漏一般可以理解为系统资源（各方面的资源，堆、栈、线程等）在错误使用的情况下，导致使用完毕的资源无法回收（或没有回收），从而导致新的资源分配请求无法完成，引起系统错误。
- 内存泄漏对系统危害比较大，因为他可以直接导致系统的崩溃。
- 需要区别一下，内存泄漏和系统超负荷两者是有区别的，虽然可能导致的最终结果是一样的。内存泄漏是用完的资源没有回收引起错误，而系统超负荷则是系统确实没有那么多资源可以分配了（其他的资源都在使用）
#### 年老代堆空间被占满
- 异常：java.lang.OutOfMemoryError: Java heap space
- 说明：
![poraGen](http://dl.iteye.com/upload/picture/pic/51415/49464252-97ea-3ce2-b433-d9088bafb70a.png)
如上图所示，这是非常典型的内存泄漏的垃圾回收情况图。所有峰值部分都是一次垃圾回收点，所有谷底部分表示是一次垃圾回收后剩余的内存。连接所有谷底的点，可以发现一条由底到高的线，这说明，随时间的推移，系统的堆空间被不断占满，最终会占满整个堆空间。因此可以初步认为系统内部可能有内存泄漏。（上面的图仅供示例，在实际情况下收集数据的时间需要更长，比如几个小时或者几天）
- 解决：
这种方式解决起来也比较容易，一般就是根据垃圾回收前后情况对比，同时根据对象引用情况（常见的集合对象引用）分析，基本都可以找到泄漏点
#### 持久代被占满
- 异常：java.lang.OutOfMemoryError: PermGen space
- 说明：
Perm空间被占满。无法为新的class分配存储空间而引发的异常。这个异常以前是没有的，但是在Java反射大量使用的今天这个异常比较常见了。主要原因就是大量动态反射生成的类不断被加载，最终导致Perm区被占满。
更可怕的是，不同的classLoader即便使用了相同的类，但是都会对其进行加载，相当于同一个东西，如果有N个classLoader那么他将会被加载N次。因此，某些情况下，这个问题基本视为无解。当然，存在大量classLoader和大量反射类的情况其实也不多
- 解决：
① -XX:MaxPermSize=16m
② 换用JDK。比如JRocket。

#### 堆栈一次
- 异常：java.lang.StackOverflowError
- 说明：说明：这个就不多说了，一般就是递归没返回，或者循环调用造成

#### 线程堆栈满
- 异常：Fatal: Stack size too small
- 说明：java中一个线程的空间大小是有限制的。JDK5.0以后这个值是1M。与这个线程相关的数据将会保存在其中。但是当线程空间满了以后，将会出现上面异常
- 解决：增加线程栈大小。-Xss2m。但这个配置无法解决根本问题，还要看代码部分是否有造成泄漏的部分

#### 系统内存被占满
- 异常：java.lang.OutOfMemoryError: unable to create new native thread
- 说明：
① 这个异常是由于操作系统没有足够的资源来产生这个线程造成的。系统创建线程时，除了要在Java堆中分配内存外，操作系统本身也需要分配资源来创建线程。因此，当线程数量大到一定程度以后，堆中或许还有空间，但是操作系统分配不出资源来了，就出现这个异常了。
② 分配给Java虚拟机的内存愈多，系统剩余的资源就越少，因此，当系统内存固定时，分配给Java虚拟机的内存越多，那么，系统总共能够产生的线程也就越少，两者成反比的关系。同时，可以通过修改-Xss来减少分配给单个线程的空间，也可以增加系统总共内生产的线程数
- 解决：
① 重新设计系统减少线程数量
② 线程数量不能减少的情况下，通过-Xss减小单个线程大小。以便能生产更多的线程
## 一次JVM调优实践
① 通过这一个多月的努力，将FullGC从40次/天优化到近10天才触发一次，而且YoungGC的时间也减少了一半以上，这么大的优化，有必要记录一下中间的调优过程。
② 对于JVM垃圾回收，之前一直都是处于理论阶段，就知道新生代，老年代的晋升关系，这些知识仅够应付面试使用的。前一段时间，线上服务器的FullGC非常频繁，平均一天40多次，而且隔几天就有服务器自动重启了，这表明的服务器的状态已经非常不正常了，得到这么好的机会，当然要主动请求进行调优了。未调优前的服务器GC数据，FullGC非常频繁
![图示](https://img-blog.csdn.net/20180715211605360?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2JlbGlldmVyMTIz/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
### 配置：
首先服务器的配置非常一般（2核4G），总共4台服务器集群。每台服务器的FullGC次数和时间基本差不多。其中JVM几个核心的启动参数为：
-Xms1000M -Xmx1800M -Xmn350M -Xss300K -XX:+DisableExplicitGC -XX:SurvivorRatio=4 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=70 -XX:+CMSParallelRemarkEnabled -XX:LargePageSizeInBytes=128M -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintHeapAtGC
==参数说明：==
- -Xmx1800M：设置JVM最大可用内存为1800M。
- -Xms1000m：设置JVM初始化内存为1000m。此值可以设置与-Xmx相同，以避免每次垃圾回收完成后JVM重新分配内存。
- -Xmn350M：设置年轻代大小为350M。整个JVM内存大小=年轻代大小 + 年老代大小 + 持久代大小。持久代一般固定大小为64m，所以增大年轻代后，将会减小年老代大小。此值对系统性能影响较大，Sun官方推荐配置为整个堆的3/8。
- -Xss300K：设置每个线程的堆栈大小。JDK5.0以后每个线程堆栈大小为1M，以前每个线程堆栈大小为256K。更具应用的线程所需内存大小进行调整。在相同物理内存下，减小这个值能生成更多的线程。但是操作系统对一个进程内的线程数还是有限制的，不能无限生成，经验值在3000~5000左右

## 第一次
