package com.daelim;

import java.sql.*;

public class CDataBase {
    String serverURL = "61.83.168.88:3306";
    String database = "D201930107";
    String user_name = "U201930107";
    String password = "201930107";
    Statement stmt = null;
    ResultSet rs = null;
    Connection con = null;

    public CDataBase() {
        try {
            // 1. 드라이버 로딩
            try {
                Class.forName("com.mysql.jdbc.Driver");

            } catch (ClassNotFoundException e) {
                System.out.println("!! JDBC ERROR. DRIVER LOAD Error");
                e.printStackTrace();
            }

            // 2. 연결
            String url = "jdbc:mysql://" + serverURL + "/" + database;
            try {
                con = DriverManager.getConnection(url, user_name, password);

                System.out.println("정상 연결 완료");

                // 여기서 서버의 값을 확인 및 입력



                stmt = con.createStatement();



            } catch (SQLException e) {
                System.out.println(" !! con ERROR");
                e.printStackTrace();
            }

            // 3. 해제
//            try {
//                if (con != null) {
//                    con.close();
//                    System.out.println("정상 해제 완료");
//                }
//
//            } catch (SQLException e) {
//                System.out.println(" !! CLOSE ERROR");
//                e.printStackTrace();
//            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
