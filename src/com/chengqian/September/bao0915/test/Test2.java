package com.chengqian.September.bao0915.test;

import com.chengqian.pojo.Student;
import com.chengqian.utils.JdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @ClassName Test2
 * @Description
 * 1.查询多行数据并解析
 * 2.解析后的查询结果封装到对象中
 * 3.JdbcUtil工具和抽取实现
 * 4.将配置信息抽取到外部动态配置文件（properties格式：key-values）中，通过加载配置文件的形式使用
 * @Author CQ
 * @Date 2022/9/15 15:26
 * @Version 1.0
 */
public class Test2{
    public static void main(String[] args) {
        //编写jdbc实现姓名中包含:井下的学生, 查询学生信息.
        //再实现查询结果的存储和封装（一行学生记录存储为1个学生对象，多个学生对象存储到一个集合中国）
        Connection conn = null ;
        PreparedStatement pstm = null ;
        ResultSet set = null ;
        try{
            //可能产生异常的操作
            //1.加载驱动
            //Class.forName("com.mysql.cj.jdbc.Driver");

            //2.建立连接:通过Jdbc工具类即可
            conn = JdbcUtil.getConnection();
//        String url="jdbc:mysql://localhost:3306/myschool2";
//        String username="root";
//        String password="111111";
//        Connection conn = DriverManager.getConnection(url, username, password);


            //3.编写sql
            String sql = "SELECT * FROM tb_student where stu_name like ?;";

            //4.创建一个预编译执行对象，先绑定参数，再执行sql
            pstm = conn.prepareStatement(sql);
            String name = "井下";
            pstm.setString(1,"%"+name+"%");
            //set查询结果是一张临时数据表
            set = pstm.executeQuery();

            //5.解析返回结果，封装存储到Java对象中

            //模拟集合：
            List list = new ArrayList();

            //循环解析每一行：当所有行解析完毕，返回false循环结束
            while(set.next()){
                //解析一行：id,stu_name,gender
                //long id = set.getLong(1);//根据索引位置解析某个单元格的数据
                long id = set.getLong("id");//根据列名解析某个单元格的数据
                String stu_name = set.getString("stu_name");
                String gender = set.getString("gender");
                System.out.println("解析的学生信息为："+id + stu_name + gender);

                //封装以上数据到学生对象中：
                Student stu1 = new Student(id,stu_name,gender);

                //存储对象到集合中：
                list.add(stu1);
            }

            //模拟使用list集合数据：
            ListIterator it = list.listIterator();
            while (it.hasNext()) {
                Object next =  it.next();
                System.out.println(next);
            }
            System.out.println("集合中数据的个数："+list.size());
        }catch(Exception e1){
            System.out.println("sql报错了！");
        }finally {
            //必须的操作

            //6.释放资源
            JdbcUtil.closeRelease(set,pstm,conn);
        }





    }
}
