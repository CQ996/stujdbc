package September.bao0915.test;


import java.sql.*;

/**
 * @ClassName Test1
 * @Description 绑定参数方式：占位符绑定。
 * @Author CQ
 * @Date 2022/9/15 14:34
 * @Version 1.0
 */
public class Test1 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //模拟删除,学生信息表中编号为10号的同学.
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url="jdbc:mysql://localhost:3306/myschool2";
        String username="root";
        String password="111111";
        Connection conn = DriverManager.getConnection(url,username,password);

        //执行要先获取执行对象：
        Statement stmt =  conn.createStatement();
        //查询 编号为11号的同学信息
        ResultSet sel = stmt.executeQuery("select * from tb_student where id=10");
        System.out.println(sel);

        //删除10号
        //1.固定指定参数
        //String sql = "delete from tb_student where id=10";

        //2.采用字符串拼接参数,不推荐。可能会遇到SQL注入风险
        //int id=10;
        //String sql = "delete from tb_student where id="+id;

        //int r = stmt.executeUpdate(sql);

        //3.推荐绑定参数方式：占位符绑定形式：借助预编译SQL形式
        int id = 15;
        String name = "laowang";
        int telphone = 999999;
        String sql = "delete from tb_student where id=? and telphone = ? and stu_name = ?";
        //传入sql获得一个PreparedStatement的预编译执行对象
        PreparedStatement pstm = conn.prepareStatement(sql);
        //参数绑定
        pstm.setInt(1,id);
        pstm.setInt(2,telphone);
        pstm.setString(3,name);
        //预编译执行对象不用再次传入sql，直接执行
        int r = pstm.executeUpdate();


        if(r>0){
            //成功：
            System.out.println("删除成功！");
        }else{
            System.out.println("删除失败！");
        }

        //删除后查询依然可以获得一个对象
        ResultSet sel2 = stmt.executeQuery("select * from tb_student where id=10");
        System.out.println(sel2);
        stmt.close();
        pstm.close();
        conn.close();
    }
}
