package com.chengqian.September.bao0917;


import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @ClassName DruidTest
 * @Description TODO
 * @Author CQ
 * @Date 2022/9/17 9:42
 * @Version 1.0
 */
public class DruidTest {
    public static void main(String[] args) throws Exception {
        //1.加载druid

        //2.数据源连接池：创建
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
