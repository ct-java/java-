[toc]
# Spring学习
## SpringMVC常用注解
### 开启注解扫描
启动包扫描功能，以便注册带有@Controller、@Service、@repository、@Component等注解的类成为Spring的Bean。
```xml
<!-- spring可以自动去扫描base-pack下面的包或者子包下面的java文件，如果扫描到有Spring的相关注解的类，则把这些类注册为Spring的bean -->
    <context:component-scan base-package="com.qianchunhua.controller"/>
```
### @Controller
- org.springframework.stereotype.Controller注解类型用于指示Spring类的实例是一个控制器
- 使用@Controller注解的类不需要继承特定的父类或者实现特定的接口
- @Controller注解注解的控制器可以同时支持处理多个请求动作，使程序开发变的更加灵活
- @Controller用户标记一个类，使用它标记的类就是一个Spring MVC Controller对象
### @Autowired
### @RequestMapping
==属性列表：==
1. value属性
- @RequestMapping(value="/hello")
- 但如果有超过一个属性，就必须写上value属性名称。value属性的值也可以是一个空字符串，此时方法被映射到如下url请求：http://localhost:8088/context
2. method属性
- @RequestMapping(value="/hello",method=RequestMethod.POST)
- 以上代码method=RequestMethod.POST表示该方法只支持POST请求，也可以同时支持多个HTTP请求
- @RequestMapping(value="/hello",method={RequestMethod.POST,method=RequestMethod.GET})
- 若没有指定method属性值，则请求处理方法可以任意的请求HTTP方式。
3. consumes属性
- 该属性指定处理请求的提交内容类型（Content-Type）
- @RequestMapping(value="/hello",method=RequestMethod.POST,consumes="application/json")
- 表示方法仅处理request Content-Type为"application/json"类型的请求
4. produces属性
该属性指定返回的内容类型，返回的内容类型必须是request请求头（Accept）中所包含的类型，@RequestMapping(value="/hello",method=RequestMethod.POST,produces="application/json")，方法仅处理request请求中Accept头中包含了”application/json”的请求，同时知名了返回的内容类为application/json
5. params属性
- 该属性指定request中必须包含某些参数值时，才让该方法处理
- @RequestMapping(value="/hello",method=RequestMethod.POST,params="myParam=myValue")
- 方法仅处理其中名为“myPara”，值为“myValue”的请求
6. headers属性
- 该属性指定request中必须包含某些特性的header值，才能让该方法处理请求
- @RequestMapping(value="/hello",method=RequestMethod.POST,headers="Referer=http://www.qianchunhua.com/")
- 方法仅处理request的header中包含了指定的“Referer”请求头和对应值为“http://www.qianchunhua.com”的请求
### @RequestParam
- org.springframework.web.bind.annotation包下的第三个注解
- 该注解类型用于将指定的请求参数赋值给方法中的形参
- 请求处理方法参数的可选类型位Java的8种基本数据类型和String
1. name属性
- 该属性的类型是String类型，它可以指定请求头绑定的名称
2. value属性
- 该属性的类型是String类型，它可以设置是name属性的别名
3. required属性
- 该属性的类型是boolean类型，它可以设置指定参数是否必须绑定
4. defalutValue属性
- 该属性的类型是String类型，它可以设置如果没有传递参数可以使用默认值
### @PathVaribale
- org.springframework.web.bind.annotation包下的第四个注解
- 获得请求url中的动态参数
- @PathVaribale注解只支持一个属性value，类型String
- 如果省略则默认绑定同名参数
```java
@RequestMapping(value="/pathVariableTest/{userId}")
public void pathVariableTest(@PathVaribale Integer userId){}
```
### @RequestHeader
- org.springframework.web.bind.annotation包下的第五个注解
- 该注解类型用于将请求的头的信息区域数据映射到功能处理方法的参数上
1. name属性
- 该属性的类型是String类型，它可以指定请求头绑定的名称；
2. value属性
- 该属性的类型是String类型，它可以设置是name属性的别名；
3. required属性
该属性的类型是boolean类型，它可以设置指定参数是否必须绑定；
4. defalutValue属性
- 该属性的类型是String类型，它可以设置如果没有传递参数可以使用默认值。
```java
@RequestMapping(value="/requestHeaderTest")
public void requestHeaderTest(
    @RequestHeader("User-Agent") String userAgent,
    @RequestHeader(value="Accept") String[] accepts) {
}
```
### @CookieValue
- org.springframework.web.bind.annotation包下的第六个注解
- 该注解类型用于将请求的Cookie数据映射到功能处理方法的参数上
1. name属性
该属性的类型是String类型，它可以指定请求头绑定的名称；
2. value属性
该属性的类型是String类型，它可以设置是name属性的别名；
3. required属性
该属性的类型是boolean类型，它可以设置指定参数是否必须绑定；
4. defalutValue属性
该属性的类型是String类型，它可以设置如果没有传递参数可以使用默认值。
```java
@RequestMapping(value="/cookieValueTest")
public void cookieValueTest(
    @CookieValue(value="JSESSIONID",defaultValue="") String sessionId) {
}！
```
### @SessionAttributes
- org.springframework.web.bind.annotation包下的第七个注解
- 该注解类型允许我们有选择地指定Model中的哪些属性需要转存到HttpSession对象当中
- @SessionAttributes只能声明在类似，而不能声明在方法上
1. names属性
- 该属性的类型是String[]，它可以指定Model中属性的名称，即存储在HttpSession当中的属性名称；
2. value属性
- 该属性的类型是String[]，它可以设置names属性的别名；
3. types属性
- 该属性的类型是Class<?>[] ，它可以指定参数是否必须绑定

### @ModelAttribut
- org.springframework.web.bind.annotation包下的第八个注解
- 该注解类型将请求参数绑定到Model对象
- @ModelAttribute注解只支持一个属性value，类型为String
```java
// model属性名称和model属性对象由model.addAttribute()实现，前提是要在方法中加入一个Model类型的参数。
// 注意：当URL或者post中不包含对应的参数时，程序会抛出异常。
 @ModelAttribute
 public void userModel2(@RequestParam("loginname") String loginname,@RequestParam("password") String password,Model model){
     model.addAttribute("loginname", loginname);
     model.addAttribute("password", password);
 }
```
## web.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" version="2.5">
<display-name>springmvc-comment2</display-name>
<servlet>
  <servlet-name>springmvc</servlet-name>
  <servlet-class>
      org.springframework.web.servlet.DispatcherServlet
  </servlet-class>
  <init-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>/WEB-INF/springmvc-config.xml</param-value>
  </init-param>
  <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
  <servlet-name>springmvc</servlet-name>
  <url-pattern>/</url-pattern>
</servlet-mapping>
<filter>
  <filter-name>characterEncodingFilter</filter-name>
  <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
  <init-param>
    <param-name>encoding</param-name>
    <param-value>UTF-8</param-value>
  </init-param>
</filter>
<filter-mapping>
  <filter-name>characterEncodingFilter</filter-name>
  <url-pattern>/*</url-pattern>
</filter-mapping>
</web-app>
```
## 视图解析器
```xml
<!-- 视图解析器  -->
<bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
  <!-- 前缀 -->
  <property name="prefix">
      <value>/WEB-INF/content/</value>
  </property>
  <!-- 后缀 -->
  <property name="suffix">
      <value>.jsp</value>
  </property>
</bean>
```
## 拦截器
### XML配置
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd
        http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">

    <!-- 扫描controller（controller层注入） -->
<context:component-scan base-package="com.bybo.aca.web.controller"/>
<mvc:interceptors>  
   <!--  使用bean定义一个Interceptor，直接定义在mvc:interceptors根下面的Interceptor将拦截所有的请求   -->
    <!-- <bean class="com.bybo.aca.web.interceptor.Login"/> -->   
    <mvc:interceptor>  
        <!-- 进行拦截：/**表示拦截所有controller -->
        <mvc:mapping path="/**" />
    　　 <!-- 不进行拦截 -->
        <mvc:exclude-mapping path="/index.html"/>
        <bean class="com.bybo.aca.web.interceptor.Login"/>  
    </mvc:interceptor>  
</mvc:interceptors>     

<bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver"
          p:prefix="/WEB-INF/views/" p:suffix=".jsp"/>
</beans>
```
### Controller代码
```java
package com.bybo.aca.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class Login implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest httpRequest,
            HttpServletResponse httpResponse, Object arg2, Exception arg3)
            throws Exception {         
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2, ModelAndView arg3) throws Exception {      
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object object) throws Exception {
        /*HttpServletRequest httpRequest = (HttpServletRequest) request;  
         HttpServletResponse httpResponse = (HttpServletResponse) response;*/
        String urlString = request.getRequestURI();

        ///olForum/forumList.html模拟登录页
        if(urlString.endsWith("forumList.html")){
            return true;
        }
        //请求的路径
        String contextPath=request.getContextPath();
        response.sendRedirect(contextPath + "/olForum/forumList.html?login=aaa");  
        return false;
    }
}
```
## 文件上传
### XML配置
```xml
<!-- 配置多媒体文件解析器 -->
<!-- 文件上传 -->
<bean id="multipartResolver"
    class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
    <!-- 设置上传文件的最大尺寸为5MB -->
    <property name="maxUploadSize">
        <value>5242880</value>
    </property>
</bean>
```
### Controller代码
```java
@RequestMapping(value="/updateitem",method={RequestMethod.POST,RequestMethod.GET})
public String updateItems(Items items, MultipartFile picture) throws Exception {
    // 处理上传的单个图片    
    String originalFileName = picture.getOriginalFilename();// 原始名称
    // 上传图片
    if (picture != null && originalFileName != null && originalFileName.length() > 0) {
        // 存储图片的物理路径，实际中是要写到配置文件中的，不能在这写死
        String pic_path = "F:\\temp\\images\\";
        // 新的图片名称
        String newFileName = UUID.randomUUID()
                + originalFileName.substring(originalFileName
                        .lastIndexOf("."));     
        File newFile = new File(pic_path + newFileName);//新图片   
        picture.transferTo(newFile);// 将内存中的数据写入磁盘
        items.setPic(newFileName);// 将新图片名称写到itemsCustom中
    } else {
        //如果用户没有选择图片就上传了，还用原来的图片
        Items temp = itemsService.findItemsById(items.getId());
        items.setPic(temp.getPic());
    }
    // 调用service更新商品信息，页面需要将商品信息传到此方法
    itemService.updateItem(items);
    return "forward:/item/itemEdit.action";
}
```
