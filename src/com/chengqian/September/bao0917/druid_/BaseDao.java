package com.chengqian.September.bao0917.druid_;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @ClassName BasDao
 * @Description TODO
 * BaseDao的声明和演示，充当查询父类
 * @Author CQ
 * @Date 2022/9/15 18:58
 * @Version 1.0
 */
//从所有jdbc操作类中抽取而来：充当父类使用
public class BaseDao {
    //定义全局属性：
    static String driver = null;
    static String url = null;
    static String user = null;
    static String password = null;

    //JDBC API全局属性：
    Connection conn = null ;
    PreparedStatement pstm = null ;
    ResultSet set = null ;

    //连接池全局属性
    static DataSource dataSource = null;

    //1.静态代码块：安装驱动
    static{
        //加载完成配置文件：
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("druid-1.properties");
        Properties pro = new Properties();

        try {
            pro.load(is);//加载输入流数据到当前集合中
            //开始构建连接池：
            dataSource = DruidDataSourceFactory.createDataSource(pro);

        } catch (Exception e) {
            System.out.println("连接池失败，请关闭重启程序！！！");
        }

    }

    //2.：建立连接，不需要是静态方法
    public Connection getConnection(){
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println("连接建立失败，请重试！！！");
        }
        return conn;
    }

    //3.静态方法：释放资源
    public static void closeRelease(ResultSet set, Statement stmt, Connection conn){
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

    //编写查询功能：
    public ResultSet query(String sql , Object[] params){

        try{
            //可能产生异常的操作
            //2.建立连接:通过Jdbc工具类即可
            conn = getConnection();

            //3.编写sql

            //4.创建一个预编译执行对象，先绑定参数，再执行sql
            pstm = conn.prepareStatement(sql);

            //绑定参数数组到预编译对象中：
            for (int i = 0; i < params.length; i++) {
                pstm.setObject(i+1,params[i]);
            }

            //set查询结果是一张临时数据表
            set = pstm.executeQuery();

            //5.解析返回结果，封装存储到Java对象中
            return set;
        }catch(Exception e1){
            System.out.println("sql报错了！");
            return null;
        }

    }

    //编写增删改功能
    public int executeUpdate(String sql , Object[] params){
        try{
            //可能产生异常的操作
            //2.建立连接:通过Jdbc工具类即可
            conn = getConnection();

            //4.创建一个预编译执行对象，先绑定参数，再执行sql
            pstm = conn.prepareStatement(sql);

            //绑定参数数组到预编译对象中：
            for (int i = 0; i < params.length; i++) {
                pstm.setObject(i+1,params[i]);
            }

            //set查询结果是一张临时数据表
            int r = pstm.executeUpdate();

            //5.解析返回结果，封装存储到Java对象中
            return r;
        }catch(Exception e1){
            System.out.println("sql报错了！");
            return -1;
        }finally {
            closeRelease(set,pstm,conn);
        }
    }

}
