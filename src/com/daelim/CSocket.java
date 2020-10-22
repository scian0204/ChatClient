package com.daelim;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CSocket {
    WebSocketClient ws;
    URI uri;
    TextArea t;
    MessageHandler handler;

    public CSocket(String s, TextArea ta) {
        try {
            uri = new URI(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        t = ta;
    }
    public CSocket(String s, MessageHandler h) {
        try {
            uri = new URI(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler = h;
    }

    public void start() {
        ws = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                System.out.println("onOpen");

            }

            @Override
            public void onMessage(String s) {
                try {
                    System.out.println("onMessage :: " + s);

                    JSONObject msg = (JSONObject) (new JSONParser()).parse(s);
                    String str = getTime() + " || [ " + msg.get("name") + " ] : " + msg.get("data") + "\n";

                    if (t == null) {
                        handler.handleMessage(str);
                    } else {
                        t.append(str);
                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                System.out.println("onClose :: " + i + ", " + s);
            }

            @Override
            public void onError(Exception e) {
                System.out.println("onError");
            }
        };

        ws.connect();

    }

    public void sendMsg(String str) {
        ws.send(str);

    }
    public void sendMsg(JSONObject jsono) {
        ws.send(jsono.toString());

    }

    public void end() {
        ws.close();
    }

    private String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        return format.format(new Date());
    }
}
