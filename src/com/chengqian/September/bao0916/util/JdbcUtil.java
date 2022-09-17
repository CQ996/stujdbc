package com.chengqian.September.bao0916.util;

import java.sql.*;

//������: ���jdbc��������е��ظ�����.
public class JdbcUtil {

    //����Ϊ��̬�������:
    public static Connection conn;


    //���ذ�װ����.ʹ��static{}����.
    static{
        try {
            Class.forName( "com.mysql.cj.jdbc.Driver" );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //��������. �ṩ��̬����ʹ��.
    public static Connection createConn() throws SQLException {
        //�������ݿ����������ϢΪ���������ļ���ʽ: ������д����,���ɷ������.
        String url = "jdbc:mysql://localhost:3306/myschool2";
        String username = "root";
        String password = "111111";
        if( conn == null ){
            conn =  DriverManager.getConnection(url , username , password);
        }
        return conn;
    }

    //�ͷ���Դ.�ṩ��̬����ʹ��.
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
