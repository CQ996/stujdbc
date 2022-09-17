package com.mysql.dao;

import com.mysql.pojo.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName StudentDao
 * @Description Dao查询子类
 * @Author CQ
 * @Date 2022/9/15 19:25
 * @Version 1.0
 */
//借助父类：BasDao优化
public class StudentDao extends BasDao {
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
