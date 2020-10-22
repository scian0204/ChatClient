package com.daelim;

import org.json.simple.JSONObject;

import java.sql.SQLException;
import java.util.Scanner;

public class CClient {
    CDataBase cdb;
    String uriString = "ws://61.83.168.88:4877";
    CSocket c;
    String nick=null;

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
        c = new CSocket(uriString, new MessageHandler() {
            @Override
            public void handleMessage(String message) {
                System.out.println(message);
            }
        });
        c.start();
        while(true) {
            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();
            if (str.equals("exit0")) break;
            JSONObject jsono = new JSONObject();
            jsono.put("name", nick);
            jsono.put("data", str);
            c.sendMsg(jsono.toString());
        }
        c.end();
        System.out.println("끝");
    }


}
