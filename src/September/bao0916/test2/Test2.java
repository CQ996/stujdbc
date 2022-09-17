package September.bao0916.test2;

import September.bao0916.util.JdbcUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;

//借助JdbcUtil的工具类形式操作数据库:
public class Test2 {

    public static void main(String[] args) throws Exception{
        addGrade( "') -- j111" );
    }

    //定义在一个独立的函数中:
    public static void addGrade( String gradeName )throws Exception{


        // 创建连接 ： JdbcUtil工具.
        Connection conn = JdbcUtil.createConn();

        //编写SQL脚本
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
        JdbcUtil.releaseConn( null,pstm , conn );



    }


}
