package com.daelim;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        CDataBase cdb = new CDataBase();
        CClient cc = new CClient(cdb);
    }
}
