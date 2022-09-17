package com.chengqian.September.bao0916.test1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Test1 {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        //安装驱动到内存中:
//        方式一: 不通用.
//        Driver d1 = new Driver();
//        DriverManager.registerDriver( d1 );

//        方式二: 只需传入任意驱动名称即可构建安装.
        Class.forName( "com.mysql.cj.jdbc.Driver" );


        // 创建连接
        String url = "jdbc:mysql://localhost:3306/myschool2";

        String username = "root";
        String password = "111111";

       Connection conn =  DriverManager.getConnection(url , username , password);

        //编写SQL脚本
        String sql1 = "delete from tb_student where id in (101,111,112)";

        //创建执行对象stmt, 负责执行SQL
        Statement stmt = conn.createStatement();
        int i = stmt.executeUpdate(sql1);

        //得到SQL执行结果并使用
        System.out.println("删除结果为； "  + i);

        //断开链接,并释放操作资源
        stmt.close();
        conn.close();




    }


}
