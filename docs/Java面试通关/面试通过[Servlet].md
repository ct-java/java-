# Servlet面试
## 1. servlet单例多线程模式的理解
- 如果不同的2个用户同时对这个网站的不同业务同时发出请求，比如注册和登陆，那么容器里有几个servlet呢？
一个web容器，可以有多个servlet。对提交到同一个servlet类的多个业务请求，共享一个servlet对象。（也就是这个servlet类只被实例化一次）
- 不同的用户同时对同一个业务（如注册），发出请求，那么这个时候容器了产生的是有几个servlet实例呢？
只有一个servlet实例，一个servlet是在第一次被访问时加载到内存并实例化的。同样的业务请求共享一个servlet实例。不同的业务请求一般对应不同的servlet。想也知道了，如果一个网站被几千万人同时登陆，如果创建几千万个实例的话服务器还怎么跑得动？

servlet体系结构是建立在java多线程机制之上的，它的生命周期是有web容器负责的，当客户端第一次请求某个servlet时，servlet容器将会根据web.xml配置并实例化这个servlet类。当有新的客户端请求servlet时，一般不会再实例化该servlet类，也就是有多个线程在使用这个实例。这样当两个或多个线程同时访问一个servlet时（关于为什么要用多线程的问题也就迎刃而解 了），可能会发生多个线程访问同一个资源的情况，数据可能也会变的不一致。所以用servlet构建的web应用时如果不注意线程安全的问题，会使所写的servlet程序有难以发现的错误。
- 实例变量不正确的使用是造成servlet线程不安全的主要原因。下面针对问题给出了三种解决方案，并对方案的选取给出了一些参考建议。
1. 实现singleThreadModel接口
该接口指定了系统如何处理同一个servlet的调用。如果一个servlet被这个接口指定，那么再这个servlet中的        service方法将不会有两个线程被同时执行，当然也就不存在线程不安全的问题。这种方法只要前面的                 
```java
 // ConcurrentTest类的类头定义更改为：
 Public class ConcurrentTest extends HttpServlet implements SingleThreadModel {
    // …………
 } 
```
2. 同步对共享数据的操作
使用synchronized关键字能保证一次只有一个线程可以访问被保护的区段，本论文中的Servlet可以通过同步         代码块操作保证线程的安全。同步后的代码如下：
```java
PrintWriter output = null;

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String username = request.getParameter("username");
    synchronized (this) {
        output = response.getWriter();
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        }
        output.println("用户名:" + username + ""); 

    }
}
```
3. 避免使用实例变量
修正上面的Servlet代码，将实例变量改为局部变量实现同样的功能，代码如下：
```java
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String username = request.getParameter("username");
    synchronized (this) {
      PrintWriter output = response.getWriter();
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        }
        output.println("用户名:" + username + ""); 

    }
}
```
## servlet存在多线程问题
- 实例变量是在堆中分配的,并被属于该实例的所有线程共享，所以不是线程安全的.
- JSP中用到的OUT,REQUEST,RESPONSE,SESSION,CONFIG,PAGE,PAGECONXT是线程安全的,APPLICATION在整个系统内被使用,所以不是线程安全的.
- 局部变量在堆栈中分配,因为每个线程都有它自己的堆栈空间,所以是线程安全的
- 静态类不用被实例化,就可直接使用,也不是线程安全的.

> 在程序中可能会有多个线程或进程同时操作同一个资源(如:多个线程或进程同时对一个文件进行写操作)此时也要注意同步问题. 使它以单线程方式执行,这时，仍然只有一个实例，所有客户端的请求以串行方式执行。这样会降低系统的性能对于存在线程不安全的类,如何避免出现线程安全问题:
> 1. 采用synchronized同步。缺点就是存在堵塞问题
> 2. 使用ThreadLocal(实际上就是一个HashMap),这样不同的线程维护自己的对象，线程之间相互不干扰

## Servlet生命周期
|  ---   |      ---       |                  ---                  |
|:------:|:--------------:|:-------------------------------------:|
| 初始化 | 调用init()方法 |          servlet一加载就执行          |
|  服务  | 调用service()  | 根据具体请求调用doGet()或doPost()方法 |
|  销毁  | 调用destory()  |           servle推出时执行            |

## jsp九大内置对象和四大作用域及servlet对应关系
|         ---         |         ---         |                     ---                     |
|:-------------------:|:-------------------:|:-------------------------------------------:|
| servlet九大内置对象 | servlet对应内置对象 |                    说明                     |
|     pageContext     |     pageContext     | 包含了另外八大对象的引用,可设置页面范围属性 |
|       request       | HttpServletRequest  |                    请求                     |
|      response       | HttpServletResponse |                    响应                     |
|       session       |     HttpSession     |                    会话                     |
|     application     |   ServletContext    |                servlet上下文                |
|       config        |    ServletConfig    |                                             |
|         out         |      JspWriter      |         内部关联一个PringWriter对象         |
|        page         |        this         |                                             |
|      exception      |      Throwable      |            只会出现于JSP错误页面            |

## jsp四大作用域[小——>大]
|     ---     |        ---         |                                   ---                                   |
|:-----------:|:------------------:|:-----------------------------------------------------------------------:|
| pageContext |    pageContext     |                               一个JSP页面                               |
|   request   | HttpServletRequest |                    请求作用域，就是客户端的一次请求                     |
|   Session   |    HttpSession     | 当用户首次访问时，产生一个新的会话;会话超时，或者服务器端强制使会话失效 |
| application |   ServletContext   |            全局作用范围，整个应用程序共享;应用程序启动到停止            |

## 自动刷新
Response.setHeader("Refresh","5;URL=http://localhost:8080/servlet/example.htm");

## jsp动态包含和静态包含
- 静态包含： <%@ include file="文件相对 url 地址" %>
- 动态包含： <jsp:include page="相对 URL 地址" flush="true" />

## Cookie和Session的区别
1. 由于HTTP协议是无状态的协议，所以服务端需要记录用户的状态时，就需要用某种机制来识别具体的用户，这个机制就是Session.典型的场景比如购物车，当你点击下单按钮时，由于HTTP协议无状态，所以并不知道是哪个用户操作的，所以服务端要为特定的用户创建了特定的Session，用用于标识这个用户，并且跟踪用户，这样才知道购物车里面有几本书。这个Session是保存在服务端的，有一个唯一标识。在服务端保存Session的方法很多，内存、数据库、文件都有。集群的时候也要考虑Session的转移，在大型的网站，一般会有专门的Session服务器集群，用来保存用户会话，这个时候 Session 信息都是放在内存的，使用一些缓存服务比如Memcached之类的来放 Session。
2. 思考一下服务端如何识别特定的客户？这个时候Cookie就登场了。每次HTTP请求的时候，客户端都会发送相应的Cookie信息到服务端。实际上大多数的应用都是用 Cookie 来实现Session跟踪的，第一次创建Session的时候，服务端会在HTTP协议中告诉客户端，需要在 Cookie 里面记录一个Session ID，以后每次请求把这个会话ID发送到服务器，我就知道你是谁了。有人问，如果客户端的浏览器禁用了 Cookie 怎么办？一般这种情况下，会使用一种叫做URL重写的技术来进行会话跟踪，即每次HTTP交互，URL后面都会被附加上一个诸如 sid=xxxxx 这样的参数，服务端据此来识别用户。
3. Cookie其实还可以用在一些方便用户的场景下，设想你某次登陆过一个网站，下次登录的时候不想再次输入账号了，怎么办？这个信息可以写到Cookie里面，访问网站的时候，网站页面的脚本可以读取这个信息，就自动帮你把用户名给填了，能够方便一下用户。这也是Cookie名称的由来，给用户的一点甜头。所以，总结一下：Session是在服务端保存的一个数据结构，用来跟踪用户的状态，这个数据可以保存在集群、数据库、文件中；Cookie是客户端保存用户信息的一种机制，用来记录用户的一些信息，也是实现Session的一种方式
4.
