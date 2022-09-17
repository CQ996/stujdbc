package com.chengqian.September.bao0915;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @ClassName JDBCDemo
 * @Description 连接JDBC。
 * @Author CQ
 * @Date 2022/9/15 13:36
 * @Version 1.0
 */
public class JDBCDemo {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1.前提:  准备mysql的驱动依赖包，增加到项目构建路径中.

        //安装加载驱动包.


        //注册驱动方式一:数据库类型不通用
        //Driver d1 = new Driver();
        //DriverManager.registerDriver( d1 );

        //2.注册驱动方式二：通用数据库类型，且可传参
        Class.forName( "com.mysql.cj.jdbc.Driver" );
        //3.获取连接对象,建立mysql数据库连接.
        String url = "jdbc:mysql://localhost:3306/myschool2";
        String username = "root";
        String password = "111111";
        Connection conn = DriverManager.getConnection(url, username, password);
        System.out.println("建立成功: " + conn);
        //4.编写sql,执行sql。
        //演示: 增删改数据.  三种操作方式基本一致.
        //常量参数:
        //String sql = "insert into tb_student (stu_name,gender,telphone) values ('方凯凯','男生','199');";

        //变量参数: 字符串拼接方式
        String sname = "老胡";
        String gender = "男生";
        String telphone = "1888";
        String sql = "insert into tb_student (stu_name , gender , telphone) values ('"+ sname +"' , '"+gender+"' , '"+telphone+"');";

        //执行要先获取执行对象：
        Statement stmt =  conn.createStatement();

        //开始执行：
        //查询    stmt.executeQuery(sql);
        //增删改   stmt.executeUpdate(sql);
        int r = stmt.executeUpdate(sql);

        //解析判断执行结果
        if(r>0){
            //成功：
            System.out.println("恭喜，执行成功！");
        }else{
            System.out.println("抱歉，执行失败！");
        }
        System.out.println(r);
        //5.释放资源
        stmt.close();
        conn.close();

    }
}
