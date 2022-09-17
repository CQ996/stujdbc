package com.chengqian.pojo;

/**
 * @ClassName Student
 * @Description TODO
 * @Author CQ
 * @Date 2022/9/15 16:14
 * @Version 1.0
 */
public class Student {
    public Student() {
    }

    public Student(long id, String stuName, String gender) {
        this.id = id;
        this.stuName = stuName;
        this.gender = gender;
    }

    private long id;
    private String stuName;
    private String gender;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
