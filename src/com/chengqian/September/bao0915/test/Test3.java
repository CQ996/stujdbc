package com.chengqian.September.bao0915.test;


import com.chengqian.dao.StudentDao;
import com.chengqian.pojo.Student;

import java.util.List;

/**
 * @ClassName Test3
 * @Description BasDao封装的增删改查
 * @Author CQ
 * @Date 2022/9/15 15:26
 * @Version 1.0
 */
public class Test3 {
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
