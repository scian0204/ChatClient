package com.daelim;

import java.sql.SQLException;

public class CClient {
    CDataBase cdb;
    String uriString = "61.83.168.88:4877";
    CSocket c;
    String nick;

    public CClient(CDataBase cdb) throws SQLException {
        this.cdb = cdb;
        test();
    }

    public void test() throws SQLException {
        cdb.rs = cdb.stmt.executeQuery("select * from chatmessage");
        if (!cdb.rs.next()) {
            System.out.println("데이터 없음");
        }
        while(cdb.rs.next()) {
            System.out.println(cdb.rs.getInt("idx"));
        }
    }


}
