package com.chengqian.September.bao0916.test1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Test3 {

    public static void main(String[] args)throws Exception {

        //2.jdbc操作查询: 多个学生信息. 得到: 1个集合.
        Class.forName( "com.mysql.cj.jdbc.Driver" );

        // 创建连接
        String url = "jdbc:mysql://localhost:3306/myschool2";
        String username = "root";
        String password = "111111";
        Connection conn1 = DriverManager.getConnection(url, username, password);

        String sql1 = "select id,stu_name,telphone from tb_student where id between 1 and 10";
        Statement stmt = conn1.createStatement();

        //执行查询时: 应当采用executeQuery().  且返回结果为:ResultSet类型.
        ResultSet resultSet = stmt.executeQuery(sql1);

        //准备空集合:
        List list = new ArrayList();

        //解析结果集中的返回数据:
        while(resultSet.next()){
            //解析每个字段值:
            //long id = resultSet.getLong(1);
            long id = resultSet.getLong("id");
            String stu_name = resultSet.getString("stu_name");
            String telphone = resultSet.getString("telphone");
            System.out.println("一行数据为: " + id + " ---- " + stu_name +" --- " + telphone);

            //产生一个java对象:
            Student s1 = new Student();
            s1.setId( id );
            s1.setStuName( stu_name );
            s1.setTelphone(telphone);

            //将对象存储到集合中:
            list.add( s1 );
        }

        //查看集合:
        System.out.println( "集合长度为 " + list.size() +" --- 内部为: " + list );

        resultSet.close();
        stmt.close();
        conn1.close();








    }

}

