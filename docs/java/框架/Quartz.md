# 定时任务
1. Spring-task
2. Quartz
> Quartz功能更加强大，建议使用Quartz

# Quartz基本介绍
- Quartz可以与javaee与javase应用程序结合使用，也可以单独使用。
- Quartz可以用来创建简单或为运行十个，百个，甚至是好几万个Jobs这样复杂的程序。Jobs可以做成标准的Java组件或EJBs
> Quartz是一个纯java开发的开源框架，对于java出身的程序员来讲，不管是api还是文档相对还是很友好的，而且使用起来也很方便。其实quartz最主要的几个接口就是 scheduler·、job、jobdetai、Trigger、jobBuilder。其中最主要的还是Trigger，再深一点，最主要的还是cron表达式。下一篇博文的话，给大家介绍如何在spring中使用Quartz。

## Quartz特点
1. 强大的调度功能，例如支持丰富多样的调度方法，可以满足各种常规及特殊需求
2. 灵活的应用方式，例如支持任务和调度的多种组合方式，支持调度数据的多种存储方式
3. 分布式和集群能力，Terracotta 收购后在原来功能基础上作了进一步提升

## Quartz核心元素
1. `Scheduler`:任务调度器，是实际执行任务调度的控制器。在spring中通过SchedulerFactoryBean封装起来
2. `Trigger`：触发器，用于定义任务调度的时间规则，有SimpleTrigger,CronTrigger,DateIntervalTrigger和NthIncludedDayTrigger，其中CronTrigger用的比较多，本文主要介绍这种方式。CronTrigger在spring中封装在CronTriggerFactoryBean中
3. `Calendar`:它是一些日历特定时间点的集合。一个trigger可以包含多个Calendar，以便排除或包含某些时间点
4. `JobDetail`:用来描述Job实现类及其它相关的静态信息，如Job名字、关联监听器等信息。在spring中有JobDetailFactoryBean和 MethodInvokingJobDetailFactoryBean两种实现，如果任务调度只需要执行某个类的某个方法，就可以通过MethodInvokingJobDetailFactoryBean来调用
5. `Job`：是一个接口，只有一个方法void execute(JobExecutionContext context),开发者实现该接口定义运行任务，JobExecutionContext类提供了调度上下文的各种信息。Job运行时的信息保存在JobDataMap实例中。实现Job接口的任务，默认是无状态的，若要将Job设置成有状态的，在quartz中是给实现的Job添加@DisallowConcurrentExecution注解（以前是实现StatefulJob接口，现在已被Deprecated）,在与spring结合中可以在spring配置文件的job detail中配置concurrent参数

# Quartz使用
## Quartz简单使用
### 引入依赖
```xml
<properties>
    <quartz.version>2.3.1</quartz.version>
</properties>
<!-- quartz任务调度框架 -->
<dependency>
    <groupId>org.quartz-scheduler</groupId>
    <artifactId>quartz</artifactId>
    <version>${quartz.version}</version>
</dependency>
<dependency>
    <groupId>org.quartz-scheduler</groupId>
    <artifactId>quartz-jobs</artifactId>
    <version>${quartz.version}</version>
</dependency>
```
### 2.使用
```java
package com.lsdm.service.impl;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component //注册一个Bean
@EnableScheduling // 开启定时器
public class SelectPaperImpl  {

    @Scheduled(cron = "*/5 * * * * ?") // 每 2s 执行1次。
    public void send() {
        System.out.println("hello world");
    }
}
```
## Quartz集群配置

# Cron表达式
- Cron表达式是一个字符串，分为6或7个域，每一个域代表一个含义

## Cron有如下两个语法格式：
1. Seconds Minutes Hours Day Month Week Year
2. Seconds Minutes Hours Day Month Week

### Cron从左到右：
|  ---   |  ---   |   --   |      ---       |  ---   |      ---       |    ---    |
|:------:|:------:|:------:|:--------------:|:------:|:--------------:|:---------:|
|   秒   |   分   |  小时  |       日       |   月   |      星期      |    年     |
|  0-59  |  0-59  |  0-23  |      1-31      |  1-12  |      1-7       | 1970-2099 |
| , -  / | , -  / | , -  / | , -  ? / L W C | , -  / | , -  ? / L C # |  , -  /   |

## Cron中的特殊符号
- \* 代表整个时间段
- / 表示每多长时间执行一次
0/15 表示每隔15分钟执行一次,“0”表示为从“0”分开始；
3/20 表示每隔20分钟执行一次，“3”表示从第3分钟开始执行
- ? 表示每月的某一天，或第几周的某一天
- L “6L”表示“每月的最后一个星期五”
- W：表示为最近工作日
如“15W”放在每月（day-of-month）字段上表示为“到本月15日最近的工作日”
- \# 是用来指定每月第n个工作日
"6#3"或者"FRI#3":在每周（day-of-week）中表示“每月第三个星期五”

问号(?)就是用来对日期和星期字段做互斥的，问号(?)的作用是指明该字段‘没有特定的值’，星号(\*)和其它值，比如数字，都是给该字段指明特定的值，而星号(\*)代表所有，在天时表示每一天。
- “?”字符：表示不确定的值
- “,”字符：指定数个值
- “-”字符：指定一个值的范围
- “/”字符：指定一个值的增加幅度。n/m表示从n开始，每次增加m
- “L”字符：用在日表示一个月中的最后一天，用在周表示该月最后一个星期X
- “W”字符：指定离给定日期最近的工作日(周一到周五)
- “#”字符：表示该月第几个周X。6#3表示该月第3个周五

## Cron大全
"0 0 0/2 \* \* ?" 每隔2个小时触发
"0 0 12 \* \* ?" 每天中午12点触发
"0 15 10 ? \* \*" 每天上午10:15触发
"0 15 10 \* \* ?" 每天上午10:15触发
"0 15 10 \* \* ? \*" 每天上午10:15触发
"0 15 10 \* \* ? 2005" 2005年的每天上午10:15触发
"0 \* 14 \* \* ?" 在每天下午2点到下午2:59期间的每1分钟触发
"0 0/5 14 \* \* ?" 在每天下午2点到下午2:55期间的每5分钟触发
"0 0/5 14,18 \* \* ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
"0 0-5 14 \* \* ?" 在每天下午2点到下午2:05期间的每1分钟触发
"0 10,44 14 ? 3 WED" 每年三月的星期三的下午2:10和2:44触发
"0 15 10 ? \* MON-FRI" 周一至周五的上午10:15触发
"0 15 10 15 \* ?" 每月15日上午10:15触发
"0 15 10 L \* ?" 每月最后一日的上午10:15触发
"0 15 10 ? \* 6L" 每月的最后一个星期五上午10:15触发
"0 15 10 ? \* 6L 2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发
"0 15 10 ? \* 6#3" 每月的第三个星期五上午10:15触发
"0 \* \* 1 \* ？"   note：每月1号凌晨都会被执行
"0 \* \* ? \* \* "  note：每分钟的00秒被执行
"0 10 18 ? 3 WEB" 每年3月的每个星期三，下午6点10分都会被触发
"0 10 18 15 3 ?" 每年三月的第15天，下午6点10分都会被触发
"0 10 18 1-5 \* ？" 每月的1号到5号（包含每月1号和5号，每月共计5天都会被触发），下午6点10分都会被触发
"0 10-15 * ? \* \*" 每小时的第10分钟到第15分钟（包含每小时的第10分钟和第15分钟，每小时共计5分钟都会被触发），都会被触发
"10,20 \* \* ? \* \*" 每分钟的第10秒与第20秒都会被触发
"0 10,20 \* 1,2 \* ?" 每月的第1天与第2天的，每小时的第10分钟与第20分钟被触发
"5/20 \* \* ? \* \*" 每分钟的第5秒，第25秒，第45秒 都会被执行
"0 \* 2/2 ? \* \*" 每天的第2小时，第4小时，第6小时，第8小时 ... 第22小时的00分00秒都会被触发
"\* \* \* ? \* 3#4" 每月的第4个星期的周2，凌晨触发
"\* \* \* ? \* 6#2" 每月的第2个星期的周5，凌晨触发
