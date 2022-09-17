package com.mysql.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @ClassName JdbcUtil
 * @Description JDBC连接工具类
 * @Author CQ
 * @Date 2022/9/15 16:59
 * @Version 1.0
 */
//JdbcUtil.java源码文件----->JdbcUtil.class字节码文件（硬编码方式，不方便修改相关信息）
//优化：当前连接工具中的常量配置：建议调整为动态配置文件（properties格式：key-values）的配置形式。
public class JdbcUtil {
    //定义全局属性：
    static String driver = null;
    static String url = null;
    static String user = null;
    static String password = null;

    //1.静态代码块：安装驱动
    static{
        //加载完成配置文件：
        InputStream is = JdbcUtil.class.getClassLoader().getResourceAsStream("dbinfo.properties");
        Properties pro = new Properties();

        try {
            pro.load(is);//加载输入流数据到当前集合中
            //解析集合中的数据：
            driver = String.valueOf(pro.get("jdbc.driver"));
            url = String.valueOf(pro.get("jdbc.url"));
            user = String.valueOf(pro.get("jdbc.user"));
            password = String.valueOf(pro.get("jdbc.password"));

//            Object jdbc_driver = pro.get("jdbc.driver");
//            Object jdbc_url = pro.get("jdbc.url");
//            Object jdbc_user = pro.get("jdbc.user");
//            Object jdbc_password = pro.get("jdbc.password");
        } catch (IOException e) {
            System.out.println("配置文件初始化失败，请关闭重启程序！！！");
        }
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("驱动程序找不到，加载失败！");
        }
    }

    //2.静态方法：建立连接
    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("连接建立失败，请重试！！！");
        }
        return conn;
    }

    //3.静态方法：释放资源
    public static void closeRelease(ResultSet set, Statement stmt,Connection conn){
        if(set!=null){
            try {
                //set为null可能会导致空指针异常
                set.close();
            } catch (SQLException e) {
                //如果关闭失败就强制关闭清空
                set = null;
            }
        }

        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                stmt = null;
            }
        }

        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                conn = null;
            }
        }

    }
}
