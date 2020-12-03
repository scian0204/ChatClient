package com.daelim;

import org.json.simple.JSONObject;

import java.sql.SQLException;

public interface MessageHandler {
    public void handleMessage(JSONObject msg) throws SQLException;
}
