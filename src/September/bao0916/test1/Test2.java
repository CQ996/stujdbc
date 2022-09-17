package September.bao0916.test1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Test2 {

    public static void main(String[] args)throws Exception {

        //jdbc操作查询: 1个学生信息.  得到: 1个对象.
        Class.forName( "com.mysql.cj.jdbc.Driver" );


        // 创建连接
        String url = "jdbc:mysql://localhost:3306/myschool2";
        String username = "root";
        String password = "111111";
        Connection conn1 = DriverManager.getConnection(url, username, password);

        String sql1 = "select id,stu_name,telphone from tb_student where id = 1";
        Statement stmt = conn1.createStatement();

        //执行查询时: 应当采用executeQuery().  且返回结果为:ResultSet类型.
        ResultSet resultSet = stmt.executeQuery(sql1);

        //解析结果集中的返回数据:
        if(resultSet.next()){
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

            System.out.println("产生了一个java对象为: " + s1);
        }

        resultSet.close();
        stmt.close();
        conn1.close();

        //jdbc操作查询: 多个学生信息. 得到: 1个集合.






    }

}

//模拟学生类:
class Student{
    private long id;
    private String stuName;
    private String telphone;

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

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
}
