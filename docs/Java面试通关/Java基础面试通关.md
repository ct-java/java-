# java基础面试通关
## 1. 相加short类型数据
```java
short a = 10;
short b = 10;
a += b; // 20
a = a + b; // 编译时错误
```
## 2. 变量类型加宽
```java
int a = 10;
float f = a;
System.out.println(a); // 10
System.out.println(f); // 10.0
```
## 3.变量溢出
```java
int a = 150;
byte b = (byte)a;
System.out.println(a); // 150
System.out.println(b); // -126
```

## 4.浮点运算进度丢失
```java
float x = 2.0f;
float y = 1.8f;
float t = x - y;
System.out.println(t); // 0.20000005
```
> 浮点数运算存在精度丢失问题，可以采用BigDecimal解决

##
```java
int i = 1;
i = i++;  // i=1
int j = i++;  // j=1
int k = i + ++i * i++;
System.out.println("i=" + i); // i=4;
System.out.println("j=" + j); // j=1
System.out.println("k=" + k); // k=11
```

## 四舍五入
```java
System.out.println(Math.round(12.5)); // 13
System.out.println(Math.round(-12.5)); // -12
```
## 数据类型
- 基本数据类型 四类八种
- 引用数据类型 数组[]/类class/抽象类abstract class/接口 interface/枚举enum/注解@interface
## 枚举
```java
public enum Day {
  // 描述了七个当前类的对象
  monday,tuesday,wednesday,thursday,friday,saturday,sunday
}
```
上述枚举等价于：
```java
public class Day {
  // 私有化构造方法，不允许创建对象
  private Day() {}
  public static final Day monday = new Day();
  public static final Day tuesday = new Day();
  public static final Day wednesday = new Day();
  public static final Day thursday = new Day();
  public static final Day friday = new Day();
  public static final Day saturday = new Day();
  public static final Day sunday = new Day();
  // 一般方法
}
```
## 自定义完整枚举enum
```java
public enum Day {
    // 描述七个当前类的对象
    monday("星期一", 1), tuesday("星期二", 2), wednesday("星期三", 3), thursday("星期四", 4),
    firday("星期五", 5), saturday("星期六", 6), sunday("星期天", 7);
    // 私有属性
    private String name;
    private int index;

    // 私有构造方法
    private Day() {
    }

    private Day(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
```
## 局部内部类
- 局部内部类是在方法中的类
- 局部内部类只能用abstract或final修饰
- 局部内部类的变量需要加final修饰

## java中初始化对象的方式
1. 通过引用变量初始化对象
2. 通过方法初始化对象
3. 通过构造函数初始化对象

#### Java中复制对象的值
1. 通过构造函数
2. 通过将一个对象的值分配给另一个对象
3. 通过Object类的 clone() 方法

## 构造函数有返回值类型吗？
没有

## 构造函数有返回值吗？
是的，构造函数返回当前类的实例（不能指定返回值类型，但它会返回一个值）

## 可以覆盖静态方法吗？
静态方法不能被覆盖，可以通过运行时多态来验证不能覆盖

## 为什么不能覆盖静态方法？
因为静态方法时绑定类的，而实例方法绑定对象。静态属于类区域，实例属于堆区域

## 可以重写 Java main 方法吗？
不可以，因为main方法是一个静态方法

## 可以重载 Java main 方法吗？
可以，重载后的方法只是一个普通的静态方法

## final方法可以继承吗？
可以，final方法可以继承，但是不能覆盖

#### 可以初始化空白的final变量吗？
是的，但只能在构造函数中

## Java8 接口中的默认方法，default修饰
```java
public interface Demo {
  default void msg(){
    System.out.println("default method");
  }
}
```
## java8 接口中的静态方法，static修饰
```java
public interface Demo() {
  static int cube(int x) {
    return x * x;
  }
}
```
## 什么是标记或标记的接口？
- 没有成员的接口（仅定义一个空的接口）称为标记或标记接口
- 可序列化，可克隆，远程等等
- 用于向JVM提供一些基本信息，以便JVM可以执行一些有用阿操作
```java
// 标记的接口/标记
public interface Serializable{

}
```
## 嵌套接口
```java
public interface Demo(){
  // 嵌套接口
  interface Demo2(){
  }
}
```
## 数据类
|   ---   |   ---    |  ---   |
|:-------:|:--------:|:------:|
| boolean |  false   | 1 bit  |
|  char   | '\u0000' | 2 byte |
|  byte   |    0     | 1 byte |
|  short  |    0     | 2 byte |
|   int   |    0     | 4 byte |
|  long   |    0L    | 8 byte |
|  float  |   0.0f   | 4 byte |
| double  |   0.0d   | 8 byte |

## Java访问修饰符
|    ---     |  ---   | ---  |     ---      |  ---   |
|:----------:|:------:|:----:|:------------:|:------:|
| 访问修饰符 | 本类中 | 同包 | 外部包的子类 | 外部包 |
|  private   |   Y    |  N   |      N       |   N    |
|  default   |   Y    |  Y   |      N       |   N    |
| protected  |   Y    |  Y   |      Y       |   N    |
|   public   |   Y    |  Y   |      Y       |   Y    |

## 对象克隆的优点
创建一个对象需要浪费较多的资源，而clone()对象需要资源很少

## Java类和类的关系
- is-a 继承，实现
- has-a 组合 聚合 关联
- use-a 依赖

## 程序设计问题
1. 可读性  名字 缩进 注释
2. 健壮性  判断严谨
3. 优化  结构 性能 内存
4. 复用性  方法 类
可扩展性  抽象 接口 面向配置文件

## 栈溢出例子
```java
public class SingleTon {

    private SingleTon(){}

    public SingleTon single = new SingleTon();

    public static void main(String[] args) {
        //Exception in thread "main" java.lang.StackOverflowError
        SingleTon single2 = new SingleTon();
        System.out.println(single2);
    }
}
```