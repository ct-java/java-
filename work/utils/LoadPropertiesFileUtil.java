package com.lsdm.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * 加载properties文件的方式
 *
 * @author wjzuo
 */
public class LoadPropertiesFileUtil {

    private static String basePath = "src/main/java/com/test/modul/utils/prop.properties";
    private static String path = "";

    /**
     * 一、 使用java.util.Properties类的load(InputStream in)方法加载properties文件
     *
     * @return
     */
    public static String getPath1() {

        try {
            InputStream in = new BufferedInputStream(new FileInputStream(new File(basePath)));
            Properties prop = new Properties();
            prop.load(in);
            path = prop.getProperty("path");
        } catch (FileNotFoundException e) {
            System.out.println("properties文件路径书写有误，请检查！");
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return path;
    }

    /**
     * 二、 使用java.util.ResourceBundle类的getBundle()方法
     * 注意：这个getBundle()方法的参数只能写成包路径+properties文件名，否则将抛异常
     *
     * @return
     */
    public static String getPath2() {
        ResourceBundle rb = ResourceBundle.getBundle("com/test/modul/utils/prop");
        path = rb.getString("path");
        return path;
    }

    /**
     * 三、 使用java.util.PropertyResourceBundle类的构造函数
     *
     * @return
     */
    public static String getPath3() {
        InputStream in;
        try {
            in = new BufferedInputStream(new FileInputStream(basePath));
            ResourceBundle rb = new PropertyResourceBundle(in);
            path = rb.getString("path");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * 四、 使用class变量的getResourceAsStream()方法
     * 注意：getResourceAsStream()方法的参数按格式写到包路径+properties文件名+.后缀
     *
     * @return
     */
    public static String getPath4() {
        InputStream in = LoadPropertiesFileUtil.class.getResourceAsStream("/com/test/modul/utils/prop.properties");
        Properties p = new Properties();
        try {
            p.load(in);
            path = p.getProperty("path");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * 五、
     * 使用class.getClassLoader()所得到的java.lang.ClassLoader的getResourceAsStream()方法
     * getResourceAsStream(name)方法的参数必须是包路径+文件名+.后缀
     * 否则会报空指针异常
     *
     * @return
     */
    public static String getPath5() {
        InputStream in = LoadPropertiesFileUtil.class.getClassLoader().getResourceAsStream("com/test/modul/utils/prop.properties");
        Properties p = new Properties();
        try {
            p.load(in);
            path = p.getProperty("path");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * 六、 使用java.lang.ClassLoader类的getSystemResourceAsStream()静态方法
     * getSystemResourceAsStream()方法的参数格式也是有固定要求的
     *
     * @return
     */
    public static String getPath6() {
        InputStream in = ClassLoader.getSystemResourceAsStream("com/test/modul/utils/prop.properties");
        Properties p = new Properties();
        try {
            p.load(in);
            path = p.getProperty("path");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    public static void main(String[] args) {
        System.out.println(LoadPropertiesFileUtil.getPath1());
        System.out.println(LoadPropertiesFileUtil.getPath2());
        System.out.println(LoadPropertiesFileUtil.getPath3());
        System.out.println(LoadPropertiesFileUtil.getPath4());
        System.out.println(LoadPropertiesFileUtil.getPath5());
        System.out.println(LoadPropertiesFileUtil.getPath6());
    }
}
