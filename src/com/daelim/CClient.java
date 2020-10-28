package com.daelim;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Scanner;

public class CClient extends JFrame {
    CDataBase cdb;
    String uriString = "ws://61.83.168.88:4877";
    CSocket c;
    String nick = null;

    JFrame th;

    //로그인 페이지
    JPanel lp = new JPanel();

    JLabel lp_idT = new JLabel("id");
    JTextField lp_idTF = new JTextField();

    JLabel lp_pwT = new JLabel("pw");
    JPasswordField lp_pwTF = new JPasswordField();

    JButton lp_loginB = new JButton("로그인");
    JButton lp_registB = new JButton("회원가입");

    //회원가입 페이지
    JPanel rp = new JPanel();

    JLabel rp_idT = new JLabel("id");
    JTextField rp_idTF = new JTextField();

    JLabel rp_pwT = new JLabel("pw");
    JPasswordField rp_pwTF = new JPasswordField();

    JLabel rp_pwrT = new JLabel("pw 다시");
    JPasswordField rp_pwrTF = new JPasswordField();

    JLabel rp_nameT = new JLabel("이름");
    JTextField rp_nameTF = new JTextField();

    JLabel rp_emailT = new JLabel("이메일");
    JTextField rp_emailTF = new JTextField();

    JButton rp_regist = new JButton("회원가입");
    JButton rp_cancel = new JButton("취소");

    //채팅방 페이지
    JPanel crp = new JPanel();

    JButton crp_logout = new JButton("로그아웃");

    JButton crp_searchRoom = new JButton("ID로 방 찾기");
    JButton crp_createRoom = new JButton("방 생성");

    JLabel crp_nop = null;
    JButton crp_roomName = null;

    //방 로그인 페이지
    JPanel rlp = new JPanel();

    JLabel rlp_idT = new JLabel("id");
    JTextField rlp_idTF = new JTextField();

    JLabel rlp_pwT = new JLabel("pw");
    JPasswordField rlp_pwTF = new JPasswordField();

    JButton rlp_enter = new JButton("입장");
    JButton rlp_cancel = new JButton("취소");

    //방 생성 페이지
    JPanel ctrp = new JPanel();

    JLabel ctrp_roomNameT = new JLabel("방 이름");
    JTextField ctrp_roomNameTF = new JTextField();

    JLabel ctrp_idT = new JLabel("id");
    JTextField ctrp_idTF = new JTextField();

    JLabel ctrp_pwT = new JLabel("pw");
    JPasswordField ctrp_pwTF = new JPasswordField();

    JButton ctrp_createRoom = new JButton("방 생성");
    JButton ctrp_cancel = new JButton("취소");

    //채팅 페이지
    JPanel chatPage = new JPanel();

    JButton cp_escapeRoom = new JButton("방 나가기");
    JButton cp_setRoom = new JButton("채팅방 설정");

    JLabel cp_userName = null;
    JLabel cp_chatDate = null;

    JTextField cp_othersChat = null;
    JTextField cp_myChat = null;

    JTextField cp_msg = new JTextField();
    JButton cp_send = new JButton("전송");

    //채팅 수정 페이지
    JPanel mcp = new JPanel();

    JButton mcp_cancel = new JButton("취소");

    JTextField mcp_id = new JTextField();
    JTextField mcp_date = new JTextField();
    JTextField mcp_cont = new JTextField();

    JButton mcp_modify = new JButton("수정");
    JButton mcp_delete = new JButton("삭제");

    //채팅 방 수정 페이지
    JPanel mcrp = new JPanel();

    JButton mcrp_cancel = new JButton("취소");

    JLabel mcrp_roomNameT = new JLabel("방 이름");
    JTextField mcrp_roomNameTF = new JTextField();

    JLabel mcrp_idT = new JLabel("id");
    JTextField mcrp_idTF = new JTextField();

    JLabel mcrp_pwT = new JLabel("pw");
    JPasswordField mcrp_pwTF = new JPasswordField();

    JButton mcrp_modify = new JButton("수정");
    JButton mcrp_delete = new JButton("삭제");

    public Font setFont(JLabel jl, float size) {
        return jl.getFont().deriveFont(size);
    }
    public Font setFont(JTextField jtf, float size) {
        return jtf.getFont().deriveFont(size);
    }
    public Font setFont(JPasswordField jtf, float size) {
        return jtf.getFont().deriveFont(size);
    }
    public Font setFont(JButton jb, float size) {
        return jb.getFont().deriveFont(size);
    }

    public CClient(CDataBase cdb) throws SQLException {
        this.cdb = cdb;
        th = this;
//        test();
        setUI();
    }

    public void setUI() {
        setSize(450, 800);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLoginPage();
        setRegistPage();
        setChatRoomPage();
        add(crp);
    }

    public void setLoginPage() {
        lp.setLayout(null);

        //id
        lp_idT.setBounds(100, 200, 100, 25);
        lp_idT.setFont(setFont(lp_idT, 25.0f));
        lp.add(lp_idT);

        lp_idTF.setBounds(100, 225, 250, 35);
        lp_idTF.setFont(setFont(lp_idTF, 25.0f));
        lp.add(lp_idTF);

        //pw
        lp_pwT.setBounds(100, 270, 100, 25);
        lp_pwT.setFont(setFont(lp_pwT, 25.0f));
        lp.add(lp_pwT);

        lp_pwTF.setBounds(100, 295, 250, 35);
        lp_pwTF.setFont(setFont(lp_pwTF, 25.0f));
        lp.add(lp_pwTF);

        //로그인 버튼
        lp_loginB.setBounds(100, 530, 250, 70);
        lp_loginB.setFont(setFont(lp_loginB, 25.0f));
        lp_loginB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        lp.add(lp_loginB);

        //회원가입 버튼
        lp_registB.setBounds(100, 610, 250, 70);
        lp_registB.setFont(setFont(lp_registB, 25.0f));
        lp_registB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        lp.add(lp_registB);

    } //완성

    public void setRegistPage() {
        rp.setLayout(null);

        //id
        rp_idT.setBounds(100, 100, 250, 25);
        rp_idT.setFont(setFont(rp_idT, 25.0f));
        rp.add(rp_idT);

        rp_idTF.setBounds(100, 125, 250, 35);
        rp_idTF.setFont(setFont(rp_idTF, 25.0f));
        rp.add(rp_idTF);

        //pw
        rp_pwT.setBounds(100, 170, 250, 25);
        rp_pwT.setFont(setFont(rp_pwT, 25.0f));
        rp.add(rp_pwT);

        rp_pwTF.setBounds(100, 195, 250, 35);
        rp_pwTF.setFont(setFont(rp_pwTF, 25.0f));
        rp.add(rp_pwTF);

        //pw 다시
        rp_pwrT.setBounds(100, 240, 250, 25);
        rp_pwrT.setFont(setFont(rp_pwrT, 25.0f));
        rp.add(rp_pwrT);

        rp_pwrTF.setBounds(100, 265, 250, 35);
        rp_pwrTF.setFont(setFont(rp_pwrTF, 25.0f));
        rp.add(rp_pwrTF);

        //이름
        rp_nameT.setBounds(100, 310, 250, 25);
        rp_nameT.setFont(setFont(rp_nameT, 25.0f));
        rp.add(rp_nameT);

        rp_nameTF.setBounds(100, 335, 250, 35);
        rp_nameTF.setFont(setFont(rp_nameTF, 25.0f));
        rp.add(rp_nameTF);

        //email
        rp_emailT.setBounds(100, 380, 250, 25);
        rp_emailT.setFont(setFont(rp_emailT, 25.0f));
        rp.add(rp_emailT);

        rp_emailTF.setBounds(100, 405, 250, 35);
        rp_emailTF.setFont(setFont(rp_emailTF, 25.0f));
        rp.add(rp_emailTF);

        //회원가입 버튼
        rp_regist.setBounds(100, 530, 250, 70);
        rp_regist.setFont(setFont(rp_regist, 25.0f));
        rp_regist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        rp.add(rp_regist);

        //취소 버튼
        rp_cancel.setBounds(100, 610, 250, 70);
        rp_cancel.setFont(setFont(rp_cancel, 25.0f));
        rp_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        rp.add(rp_cancel);
    } //완성

    public void setChatRoomPage() {
        crp.setLayout(null);

        //로그아웃 버튼
        crp_logout.setBounds(0, 0, 220, 50);
        crp_logout.setFont(setFont(crp_logout, 25.0f));
        crp.add(crp_logout);

        //방 목록


        //방 찾기 버튼
        crp_searchRoom.setBounds(25, 665, 175, 70);
        crp_searchRoom.setFont(setFont(crp_searchRoom, 20.0f));
        crp.add(crp_searchRoom);

        //방 생성 버튼
        crp_createRoom.setBounds(225, 665, 175, 70);
        crp_createRoom.setFont(setFont(crp_createRoom, 20.0f));
        crp.add(crp_createRoom);
    } //완성

    public void setRoomLoginPage() {

    }

    public void setCreateRoomPage() {

    }

    public void setChatPage() {

    }

    public void setModifyChatPage() {

    }

    public void setModifyChatRoomPage() {

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
