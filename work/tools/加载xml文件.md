# 六种加载xml文件的方式
## 1. XmlBeanFactory【spring持有，过时，不建议用】
```java
Resource resource = new ClassPathResource("appcontext.xml");
BeanFactory factory = new XmlBeanFactory(resource);
```
## 2. ClassPathXmlApplicationContext【spring持有】
```java
ApplicationContext factory=new ClassPathXmlApplicationContext("classpath:appcontext.xml");
// src目录下的
ApplicationContext factory=new ClassPathXmlApplicationContext("appcontext.xml");
ApplicationContext factory=new ClassPathXmlApplicationContext(new String[] {"bean1.xml","bean2.xml"});
// src/conf 目录下的
ApplicationContext factory=new ClassPathXmlApplicationContext("conf/appcontext.xml");
ApplicationContext factory=new ClassPathXmlApplicationContext("file:C:/Test/src/appcontext.xml");
```
## 3. 用文件系统的路径【spring持有】
```java
ApplicationContext factory=new FileSystemXmlApplicationContext("src/appcontext.xml");
//使用了 classpath: 前缀,作为标志, 这样,FileSystemXmlApplicationContext 也能够读入classpath下的相对路径
ApplicationContext factory=new FileSystemXmlApplicationContext("classpath:appcontext.xml");
ApplicationContext factory=new FileSystemXmlApplicationContext("file:C:/Test/src/appcontext.xml");
ApplicationContext factory=new FileSystemXmlApplicationContext("C:/Test/src/appcontext.xml");
// 获取对象
// 方式一：
JedisClientPool bean = context.getBean(JedisClientPool.class);
// 方式二：其中参数“jedisClientPool”必须和加载的xml配置文件中的id一致
JedisClientPool bean = (JedisClientPool)context.getBean("jedisClientPool");
```
## 4. XmlWebApplicationContext【Web持有】
```java【web持有】
ServletContext servletContext = request.getSession().getServletContext();
ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext );
```
## 5.使用BeanFactory【spring持有】
```java
BeanDefinitionRegistry reg = new DefaultListableBeanFactory();
XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(reg);
reader.loadBeanDefinitions(new ClassPathResource("bean1.xml"));
reader.loadBeanDefinitions(new ClassPathResource("bean2.xml"));
```
## 6. Web应用启动时加载多个配置文件
通过ContextLoaderListener 也可加载多个配置文件，在web.xml文件中利用
元素来指定多个配置文件位置，其配置如下:
```xml
<!-- 加载spring容器 -->
<context-param>
  <param-name>contextConfigLocation</param-name>
  <param-value>classpath:spring/applicationContext-*.xml</param-value>
</context-param>
<!-- 配置spring的监听器 -->
<listener>
  <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
```
