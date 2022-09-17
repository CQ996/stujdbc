package September.bao0916.util;

import java.sql.*;

//工具类: 解决jdbc编码过程中的重复代码.
public class JdbcUtil {

    //声明为静态共享对象:
    public static Connection conn;


    //加载安装驱动.使用static{}处理.
    static{
        try {
            Class.forName( "com.mysql.cj.jdbc.Driver" );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //创建连接. 提供静态方法使用.
    public static Connection createConn() throws SQLException {
        //改造数据库相关连接信息为灵活的配置文件形式: 无需重写代码,即可方便调整.
        String url = "jdbc:mysql://localhost:3306/myschool2";
        String username = "root";
        String password = "111111";
        if( conn == null ){
            conn =  DriverManager.getConnection(url , username , password);
        }
        return conn;
    }

    //释放资源.提供静态方法使用.
    public static void releaseConn(ResultSet rs , PreparedStatement pstm , Connection conn){

        try {
            if( rs != null ){
                rs.close();
            }
        } catch (SQLException throwables) {
            rs = null;
        }
        try {
            if( pstm != null ){
                pstm.close();
            }
        } catch (SQLException throwables) {
            pstm = null;
        }
        try {
            if( conn != null ){
                conn.close();
            }
        } catch (SQLException throwables) {
            conn = null;
        }

    }

}
