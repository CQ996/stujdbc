package September.bao0916.test2;

import java.sql.*;

public class Test1 {

    public static void main(String[] args) throws Exception{
        addGrade( "') -- j111" );
    }

    //定义在一个独立的函数中:
    public static void addGrade( String gradeName )throws Exception{
        Class.forName( "com.mysql.cj.jdbc.Driver" );

        // 创建连接
        String url = "jdbc:mysql://localhost:3306/myschool2";

        String username = "root";
        String password = "111111";

        Connection conn =  DriverManager.getConnection(url , username , password);

        //编写SQL脚本
        //String sql1 = "insert into tb_grade (grade_name) values ( '"+ gradeName +"' )";
        //创建执行对象stmt, 负责执行SQL
        //Statement stmt = conn.createStatement();
        //int i = stmt.executeUpdate(sql1);

        //一种防止sql注入的参数绑定形式:
        String sql1 = "insert into tb_grade (grade_name) values ( ? )";
        //绑定sql: 预编译对象.
        PreparedStatement pstm = conn.prepareStatement( sql1 );
        //绑定参数值:
        pstm.setString(1 ,gradeName );

        int i = pstm.executeUpdate();

        //得到SQL执行结果并使用
        System.out.println("结果为； "  + i);

        //断开链接,并释放操作资源
        pstm.close();
        conn.close();



    }


}
