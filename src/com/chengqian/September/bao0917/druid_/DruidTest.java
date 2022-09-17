package com.chengqian.September.bao0917.druid_;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @ClassName DruidTest
 * @Description TODO
 * 连接池创建与演示
 * @Author CQ
 * @Date 2022/9/17 9:42
 * @Version 1.0
 */
public class DruidTest {
    public static void main(String[] args) throws Exception {
        //1.加载druid

        //2.数据源连接池：创建

        //方式一：
        DruidDataSource dataSource1 = new DruidDataSource();
        dataSource1.setUrl("jdbc:mysql://localhost:3306/myschool2");
        dataSource1.setUsername("root");
        dataSource1.setPassword("111111");
        dataSource1.setInitialSize(5);
        dataSource1.setMaxActive(10);
        dataSource1.setMaxWait(6000);

        System.out.println("第一种方式创建的连接池"+dataSource1);
        DruidPooledConnection conn1 = dataSource1.getConnection();
        System.out.println("从连接池中解析的连接对象为："+conn1);



        //方式二：
        InputStream is = DruidTest.class.getClassLoader().getResourceAsStream("druid-1.properties");
        Properties pro = new Properties();
        pro.load(is);
        DataSource dataSource = DruidDataSourceFactory.createDataSource(pro);

        //3.指定或设置连接池的相关参数

        //4.从池中解析获取连接
        System.out.println("连接池为："+dataSource);
        Connection conn = dataSource.getConnection();
        System.out.println("获取的1个连接对象为："+conn);
    }
}
