package com.chengqian.September.bao0917;

import com.mysql.pojo.Student;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



/**
 * @ClassName JdbcTest
 * @Description JDBC和BasDao使用梳理
 * @Author CQ
 * @Date 2022/9/16 16:02
 * @Version 1.0
 */
public class JdbcTest {
    public static void main(String[] args) {
        StudentDao dao = new StudentDao();
        //模拟删除操作
        dao.delStudent();
        //模拟新增操作
        dao.addStudent();
        //模拟更改操作
        dao.upStudent();
        //模拟查询操作
        List list = dao.queryStudent();
        for (Object o : list) {
            Student s1 = (Student)o;
            System.out.println(s1.getId()+"--" + s1.getStuName() +"--"+s1.getGender());
        }
    }

}

//----------------------------------------------BasDao：加载驱动，建立连接，释放资源，查询功能，增删改功能-------------------
class BasDao{

    //将配置文件信息定义为全局变量，以方便使用
    static String driver = null;
    static String url = null;
    static String user = null;
    static String password = null;

    //JDBC API全局属性：
    Connection conn = null ;
    PreparedStatement pstm = null ;
    ResultSet set = null ;


    //-----------静态代码块加载配置文件中的驱动
    static{
        //获取类加载器，将资源作为流获取
        InputStream is = BasDao.class.getClassLoader().getResourceAsStream("dbinfo.properties");
        //new一个Properties对象，以方便我们加载 "dbinfo.properties" 配置文件中的信息
        Properties pro = new Properties();

        try {
        //Properties是Map接口的实现类，所以可以看作一个集合
        pro.load(is);//调用加载方法,将输入流数据加载到当前集合中
        //解析集合中的数据：
        driver = String.valueOf(pro.get("jdbc.driver"));
        url = String.valueOf(pro.get("jdbc.url"));
        user = String.valueOf(pro.get("jdbc.user"));
        password = String.valueOf(pro.get("jdbc.password"));

        } catch (IOException e) {
        System.out.println("配置文件初始化失败，请关闭重启程序！！！");
        }

        try {
        //根据名称获取类对象，同时将该类加载到内存中
        Class.forName(driver);
        } catch (ClassNotFoundException e) {
        System.out.println("驱动程序找不到，加载失败！");
        }
    }


    //----------------------------------静态方法：建立连接
    public static Connection getConnection(){
        // 驱动管理器解析 获取连接
        Connection conn = null;
        try {
        conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
        System.out.println("连接建立失败，请重试！！！");
        }
        return conn;
    }

    //----------------------------------静态方法：释放资源
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





//-----------------------------------------------借助父类：BasDao优化-------------------------------------------------
class StudentDao extends BasDao{
    //模拟查询学生：
    public List queryStudent() {
        //1.提供sql
        String sql = "select * from tb_student";
        //String sql = "select * from tb_student where id = ? or stu_name = ?";
        //2.提供查询参数数组
        Object[] params = {};
        //Object[] params = {1,"井下犬子"};

        //3.调用父类查询方法：query();
        set = super.query(sql, params);

        //4.得到结果自定义解析封装
        //模拟集合：
        List list = new ArrayList();

        try {
            //循环解析每一行：当所有行解析完毕，返回false循环结束
            while (set.next()) {
                //解析一行：id,stu_name,gender
                //long id = set.getLong(1);//根据索引位置解析某个单元格的数据
                long id = set.getLong("id");//根据列名解析某个单元格的数据
                String stu_name = set.getString("stu_name");
                String gender = set.getString("gender");
                System.out.println("解析的学生信息为：" + id + stu_name + gender);

                //封装以上数据到学生对象中：
                Student stu1 = new Student(id, stu_name, gender);

                //存储对象到集合中：
                list.add(stu1);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("解析异常！");
            return null;
        } finally {
            //必须的操作
            //6.释放资源
            closeRelease(set, pstm, conn);
        }

    }

    //模拟删除学生：
    public void delStudent() {
        //1.提供sql
        String sql = "delete from tb_student where id = ?";

        //2.参数列表
        Object[] params = {99};

        //3.调用父类实现操作
        int r = super.executeUpdate(sql, params);

        //4.根据执行结果返回
        if (r > 0) {
            System.out.println("删除成功！");
        } else {
            System.out.println("删除失败！");
        }
    }

    //模拟新增学生：
    public void addStudent() {
        //1.提供sql
        String sql = "INSERT INTO tb_student (stu_name,gender,telphone) " + " VALUE (?,?,?)";

        //2.参数列表
        Object[] params = {"胡永见", "男生", "100000"};

        //3.调用父类实现操作
        int r = super.executeUpdate(sql, params);

        //4.根据执行结果返回
        if (r > 0) {
            System.out.println("新增成功！");
        } else {
            System.out.println("新增失败！");
        }
    }

    //模拟更改学生信息：
    public void upStudent() {
        //1.提供sql
        String sql = "update tb_student set stu_name = ? where id = ? ";

        //2.参数列表
        Object[] params = {"张三",217};

        //3.调用父类实现操作
        int r = super.executeUpdate(sql, params);

        //4.根据执行结果返回
        if (r > 0) {
            System.out.println("更改成功！");
        } else {
            System.out.println("更改失败！");
        }
    }

}