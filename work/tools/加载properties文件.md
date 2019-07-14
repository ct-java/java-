[toc]
# 加载Properties工具类
- [LoadPropertiesFileUtil.java](work\utils\LoadPropertiesFileUtil.java)
# 加载Properties的六种方式
## 1.使用java.util.Properties类的load(InputStream in)
```Java
// 获取properties配置文件的输入流
InputStream in = new BufferedInputStream(new FileInputStream(new File(basePath)));
// 创建Properties对象
Properties prop = new Properties();
// 将输入流加载到Properties对象中
prop.load(in);
// 通过key获取值
path = prop.getProperty("path");
```
## 2.使用java.util.ResourceBundle类的getBundle()方法
```Java
ResourceBundle rb = ResourceBundle.getBundle("com/test/modul/utils/prop");
 path = rb.getString("path");
```
## 3.使用java.util.PropertyResourceBundle类的构造函数
```Java
InputStream in = new BufferedInputStream(new FileInputStream(basePath));
ResourceBundle rb = new PropertyResourceBundle(in);
path = rb.getString("path");
```
## 4.使用class变量的getResourceAsStream()方法
```Java
InputStream in = LoadPropertiesFileUtil.class.getResourceAsStream("db.properties");
Properties p = new Properties();
p.load(in);
path = p.getProperty("path");
```
## 5.使用class.getClassLoader()所得到的java.lang.ClassLoader的getResourceAsStream()方法
```Java
InputStream in = LoadPropertiesFileUtil.class.getClassLoader().getResourceAsStream("db.properties");
Properties p = new Properties();
p.load(in);
path = p.getProperty("path");
```
## 6.使用java.lang.ClassLoader类的getSystemResourceAsStream()静态方法
```Java
InputStream in = ClassLoader.getSystemResourceAsStream("db.properties");
Properties p = new Properties();
p.load(in);
path = p.getProperty("path");
```
# 解决中文乱码
```Java
InputStream inputStream = CniprCollect.class.getClassLoader().getResourceAsStream("db.properties");
// 将字节流转成字符流
BufferedReader bf = new BufferedReader(new  InputStreamReader(inputStream));
Properties p = new Properties();
p.load(bf);
```
