package com.daelim;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CClient extends JFrame {
    CDataBase cdb;
    String uriString = "ws://61.83.168.88:4877";
    CSocket c;

    String myId = null;
    String myPw = null;
    String roomId = null;
    String roomPw = null;
    String roomName = null;
    int roomnop = 0;

    JFrame th;
    GridBagLayout Gbag = new GridBagLayout();

    HashMap<String, Boolean> hm = new HashMap<>();

    JSONObject jsono = new JSONObject();

    JOptionPane jo = new JOptionPane();

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

    JPanel crp_scp = null;
    JScrollPane crp_sp = null;
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
    JPanel cp = new JPanel();

    JButton cp_escapeRoom = new JButton("방 나가기");
    JButton cp_setRoom = new JButton("채팅방 설정");

    JLabel cp_userName = null;
    JLabel cp_chatDate = null;

    JLabel cp_othersChat = null;
    JButton cp_myChat = null;

    JPanel cp_scp = null;
    JScrollPane cp_sp = null;

    JTextArea cp_msg = new JTextArea();
    JButton cp_send = new JButton("전송");

    //채팅 수정 페이지
    JPanel mcp = new JPanel();

    JButton mcp_cancel = new JButton("취소");

    JLabel mcp_idT = new JLabel("id");
    JLabel mcp_idTF = new JLabel();

    JLabel mcp_dateT = new JLabel("날짜");
    JLabel mcp_dateTF = new JLabel();

    JLabel mcp_contT = new JLabel("내용");
    JTextArea mcp_contTF = new JTextArea();

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

    public Font setFont(JComponent jc, float size) {
        return jc.getFont().deriveFont(size);
    }

    public void create_form(Component cmpt, int x, int y, float wx, float wy, JPanel jp, int it, int il, int ib, int ir, float fs){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(it, il, ib, ir);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = wx;
        gbc.weighty = wy;
        cmpt.setFont(setFont((JComponent)cmpt, fs));
        jp.add(cmpt, gbc);

    }

    void loginCheck() {
        if (lp_idTF.getText().equals("") || lp_pwTF.getText().equals("")) {
            System.out.println("아이디나 패스워드 입력 안됨");
            jo.showMessageDialog(null, "아이디나 패스워드 입력 안됨");
        } else {
            try {
                cdb.rs = cdb.stmt.executeQuery("Select userpw from member where userid='" + lp_idTF.getText() + "'");
                if (!cdb.rs.next()) {
                    System.out.println("id 일치하지 않음");
                    jo.showMessageDialog(null, "id 일치하지 않음");
                } else {
                    if (cdb.rs.getString("userpw").equals(lp_pwTF.getText())) {
                        System.out.println("로그인 됨");
                        myId = lp_idTF.getText();
                        myPw = lp_pwTF.getText();
                        //파일 객체 생성
                        File file = new File("C:\\temp\\CClientL.txt");
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

                        if(file.isFile() && file.canWrite()){
                            //쓰기
                            bufferedWriter.write("id/s/" + myId + " id/e/");
                            //개행문자쓰기
                            bufferedWriter.newLine();
                            bufferedWriter.write("pw/s/" + myPw + " pw/e/");

                            bufferedWriter.close();
                        }

                        remove(lp);
                        if (!hm.get("crp_flag")) {
                            System.out.println(hm.get("crp_flag"));
                            setChatRoomPage();
                        } else {
                            crp.remove(crp_sp);
                            createRoomList();
                        }
                        add(crp);
                        revalidate();
                        repaint();
                    } else {
                        System.out.println("비밀번호 일치하지 않음");
                        jo.showMessageDialog(null, "비밀번호 일치하지 않음");
                    }
                }
            } catch (SQLException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    void signUp() {
//        removeWM();
        if (rp_idTF.getText().equals("") || rp_pwTF.getText().equals("") || rp_pwrTF.getText().equals("") || rp_nameTF.getText().equals("") || rp_emailTF.getText().equals("")) {
            System.out.println("빈칸 있음");
            jo.showMessageDialog(null, "빈칸 있음");
//            jp0_1.add(nullW);
//            th.revalidate();
//            th.repaint();
        } else if (!rp_pwTF.getText().equals(rp_pwrTF.getText())) {
            System.out.println("비밀번호가 일치하지 않음");
            jo.showMessageDialog(null, "비밀번호가 일치하지 않음");
//            System.out.println(rp_pwTF.getText() + " || " + rp_pwrTF.getText());
//            jp0_1.add(pwNmW);
//            th.revalidate();
//            th.repaint();
        } else {
            try {
                cdb.rs = cdb.stmt.executeQuery("select * from member where userid='" + rp_idTF.getText() + "'");
                if (!cdb.rs.next()) {
                    cdb.stmt.executeUpdate("insert into member(userid, userpw, username, useremail) value('"
                            + rp_idTF.getText() + "', '" + rp_pwTF.getText() + "', '"
                            + rp_nameTF.getText() + "', '" + rp_emailTF.getText() + "')");
                    myId = rp_idTF.getText();
                    myPw = rp_pwTF.getText().toString();
                    System.out.println("회원가입 됨");
                    System.out.println("로그인 됨");
                    remove(rp);
                    if (!hm.get("crp_flag")) {
                        System.out.println(hm.get("crp_flag"));
                        setChatRoomPage();
                    } else {
                        crp.remove(crp_sp);
                        createRoomList();
                    }
                    add(crp);
                    th.revalidate();
                    th.repaint();
                } else {
                    System.out.println("아이디 중복됨");
                    jo.showMessageDialog(null, "아이디 중복됨");
//                    jp0_1.add(idOverlap);
//                    th.revalidate();
//                    th.repaint();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    void createRoomList() throws SQLException {
        int i = 0;
        System.out.println("방리스트 생성 메서드 실행");
        cdb.rs = cdb.stmt.executeQuery("select * from chatroom");
        crp_scp = new JPanel();
        crp_scp.setLayout(Gbag);
        crp_sp = new JScrollPane(crp_scp, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        crp_sp.setBounds(0, 50, 435, 585);
        crp_sp.getVerticalScrollBar().setUnitIncrement(16); // 마우스 휠 스크롤 속도
        crp_scp.setBackground(new Color(208, 206, 206));
        while(cdb.rs.next()) {
            JLabel crp_temp = new JLabel(cdb.rs.getString("roomid"));
            crp_roomName = new JButton(cdb.rs.getString("roomname"));
            crp_roomName.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(crp_temp.getText());
                    remove(crp);
                    if (!hm.get("rlp_flag")) {
                        System.out.println(hm.get("rlp_flag"));
                        setRoomLoginPage(crp_temp.getText());
                    } else {
                        rlp_idTF.setText(crp_temp.getText());
                    }
                    add(rlp);
                    th.revalidate();
                    th.repaint();
                }
            });
            crp_nop = new JLabel(cdb.rs.getString("roomnop"));
            create_form(crp_roomName, 0, 2*i, 10.0f, 0.01f, crp_scp, 25, 25, 0, 20, 25.0f);
            create_form(crp_nop, 1, 2*i, 0.1f, 0.01f, crp_scp, 25, 25, 0, 20, 25.0f);
            i++;
        }
        crp_scp.updateUI();
        crp_scp.revalidate();
        crp_scp.repaint();
        crp_sp.updateUI();
        crp_sp.revalidate();
        crp_sp.repaint();
        crp.add(crp_sp);
        crp.updateUI();
        crp.revalidate();
        crp.repaint();
    }

    boolean createRoom(String roomid, String roompw, String roomname) throws SQLException {
        cdb.rs = cdb.stmt.executeQuery("select * from chatroom where roomid='"+roomid+"'");
        if (!cdb.rs.next()) {
            int roomnop = 0;
            String userid = myId;
            String sql = "INSERT INTO chatroom(roomid, roompw, roomname, roomnop, userid) values('"
                    + roomid + "', '" + roompw + "', '" + roomname + "', " + roomnop + ", '" + userid + "')";
            cdb.stmt.executeUpdate(sql);
            this.roomId = roomid;
            this.roomnop = roomnop;
            cdb.stmt.executeUpdate("update chatroom set roomnop='"+ ++this.roomnop +"' where roomid='"+this.roomId+"'");
            return true;
        } else {
            System.out.println("roomid 중복됨");
            jo.showMessageDialog(null, "roomid 중복됨");
            return false;
        }
    }

    boolean roomLoginCheck(String roomid, String roompw) throws SQLException {
        cdb.rs = cdb.stmt.executeQuery("select * from chatroom where roomid='"+roomid+"' and roompw='"+roompw+"'");
        if (cdb.rs.next()) {
            this.roomId = roomid;
            roomPw = roompw;
            roomName = cdb.rs.getString("roomname");
            roomnop = Integer.parseInt(cdb.rs.getString("roomnop"));
            cdb.stmt.executeUpdate("update chatroom set roomnop='"+ ++roomnop +"' where roomid='"+this.roomId+"'");
            return true;
        } else {
            System.out.println("로그인 안됨");
            jo.showMessageDialog(null, "로그인 안됨");
            return false;
        }
    }

    void loadChat(String roomId) throws SQLException {
        System.out.println("loadChat 실행");
        cdb.rs = cdb.stmt.executeQuery("select * from chatmessage where roomid='"+roomId+"'");
        int i = 0;
        cp_scp = new JPanel();
        cp_scp.setLayout(Gbag);
        cp_scp.setSize(435, 532);
        cp_sp = new JScrollPane(cp_scp, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        cp_sp.setSize(435, 532);
        cp_sp.setBounds(0, 50, 435, 532);
        cp_sp.getVerticalScrollBar().setUnitIncrement(16); // 마우스 휠 스크롤 속도
        cp_scp.setBackground(new Color(208, 206, 206));
        while (cdb.rs.next()) {
            String message = cdb.rs.getString("message");
            String userid = cdb.rs.getString("userid");
            String writedate = cdb.rs.getString("writedate");
            if (message.length() > 10) {
                int cnt = message.length() / 10;
                int start = 0;
                int end = 0;
                for (int j=0; j<cnt; j++) {
                    start = j * 10;
                    end = start+10 + (j*4);
                    message = message.substring(0, end) + "<br>" + message.substring(end);
//                    System.out.println(message);
                }
            }
            message = "<HTML>" + message;
            message += "</HTML>";
            cp_userName = new JLabel(userid);
            cp_chatDate = new JLabel(writedate);
            if (cp_userName.getText().equals(myId)) {
                JLabel cp_temp = new JLabel(cdb.rs.getString("idx"));
                cp_myChat = new JButton(message);
                create_form(cp_chatDate, 1, 1*i, 0.1f, 0.1f, cp_scp, 0, 0, 10, 0, 10.0f);
                cp_chatDate.setHorizontalAlignment(JLabel.RIGHT);
                cp_myChat.setHorizontalAlignment(JLabel.RIGHT);
                create_form(cp_myChat, 2, 1*i, 0.1f, 0.1f, cp_scp, 0, 0, 10, 0, 20.0f);
                cp_myChat.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String idx = cp_temp.getText();
                        try {
                            cdb.rs = cdb.stmt.executeQuery("select * from chatmessage where idx='"+idx+"'");
                            if (cdb.rs.next()) {
                                remove(cp);
                                if (!hm.get("mcp_flag")) {
                                    setModifyChatPage();
                                }
                                mcp_idTF.setText(cdb.rs.getString("idx"));
                                mcp_dateTF.setText(cdb.rs.getString("writedate"));
                                mcp_contTF.setText(cdb.rs.getString("message"));
                                add(mcp);
                                th.revalidate();
                                th.repaint();
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                });
            } else {
                cp_othersChat = new JLabel(message);
                cp_othersChat.setOpaque(true);
                cp_othersChat.setBackground(Color.WHITE);
                cp_othersChat.setHorizontalAlignment(JLabel.LEFT);
                create_form(cp_userName, 0, 1*i, 0, 0, cp_scp, 0, 0, 0, 0, 15.0f);
                create_form(cp_othersChat, 0, 1*i+1, 0, 0, cp_scp, 0, 0, 10, 0, 20.0f);
                create_form(cp_chatDate, 1, 1*i+1, 0, 0, cp_scp, 0, 0, 10, 0, 10.0f);
            }

            i+=2;
        }

        cp_sp.getVerticalScrollBar().setValue(cp_sp.getVerticalScrollBar().getMaximum());
        cp_scp.updateUI();
        cp_scp.revalidate();
        cp_scp.repaint();
        cp_sp.updateUI();
        cp_sp.revalidate();
        cp_sp.repaint();
        cp_sp.getVerticalScrollBar().setValue(cp_sp.getVerticalScrollBar().getMaximum());
        cp.add(cp_sp);
        cp.updateUI();
        cp.revalidate();
        cp.repaint();
    }

    public CClient(CDataBase cdb) throws SQLException, FileNotFoundException {
        this.cdb = cdb;
        th = this;
        th.setTitle("Chat Client");
        c = new CSocket(uriString, new MessageHandler() {
            @Override
            public void handleMessage(JSONObject msg) throws SQLException {
                if (msg.get("roomid") == null) {
                    cdb.stmt.executeUpdate("insert into chatmessage (roomid, userid, message) values ('" +
                            "share" + "', '" + msg.get("name") + "', '" + msg.get("data") + "');");
                }
                remove(cp);
                cp.remove(cp_sp);
                loadChat(roomId);
                cp_sp.getVerticalScrollBar().setValue(cp_sp.getVerticalScrollBar().getMaximum());
                add(cp);
                th.revalidate();
                th.repaint();
            }
        });
        c.start();
//        test();
        setUI();
    }

    public void setUI() throws FileNotFoundException, SQLException {
        setSize(450, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        hm.put("lp_flag", false);
        hm.put("rp_flag", false);
        hm.put("crp_flag", false);
        hm.put("rlp_flag", false);
        hm.put("ctrp_flag", false);
        hm.put("cp_flag", false);
        hm.put("mcp_flag", false);
        hm.put("mcrp_flag", false);

        setLoginPage();
//        setChatRoomPage();
        String fileS = null;
        File file = new File("C:\\temp\\CClientL.txt");
        if (file.exists()) {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                fileS += scan.nextLine();
            }
            if (fileS.contains("id/s/") && fileS.contains("pw/s/")) {
                int id_sI = fileS.indexOf("id/s/");
                int id_eI = fileS.indexOf("id/e/");
                int pw_sI = fileS.indexOf("pw/s/");
                int pw_eI = fileS.indexOf("pw/e/");
                String fileId = fileS.substring(id_sI + 5, id_eI-1);
                String filePw = fileS.substring(pw_sI + 5, pw_eI-1);

                lp_idTF.setText(fileId);
                lp_pwTF.setText(filePw);

                cdb.rs = cdb.stmt.executeQuery("Select userpw from member where userid='" + lp_idTF.getText() + "'");
                if (!cdb.rs.next()) {
                    System.out.println("id 일치하지 않음");
                    jo.showMessageDialog(null, "id 일치하지 않음");

                } else {
                    if (cdb.rs.getString("userpw").equals(lp_pwTF.getText())) {
                        System.out.println("로그인 됨");
                        myId = lp_idTF.getText();
                        myPw = lp_pwTF.getText();

                        if (!hm.get("crp_flag")) {
                            System.out.println(hm.get("crp_flag"));
                            setChatRoomPage();
                        } else {
                            crp.remove(crp_sp);
                            createRoomList();
                        }
                        add(crp);
                        revalidate();
                        repaint();
                    } else {
                        System.out.println("비밀번호 일치하지 않음");
                        jo.showMessageDialog(null, "비밀번호 일치하지 않음");
                        add(lp);
                    }
                }

            }
        }
        setVisible(true);
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
                loginCheck();
//                remove(lp);
//                setChatRoomPage();
//                add(crp);
//                th.revalidate();
//                th.repaint();
            }
        });
        lp.add(lp_loginB);

        //회원가입 버튼
        lp_registB.setBounds(100, 610, 250, 70);
        lp_registB.setFont(setFont(lp_registB, 25.0f));
        lp_registB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(lp);
                setRegistPage();
                add(rp);
                th.revalidate();
                th.repaint();
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
                signUp();
//                remove(rp);
//                setChatRoomPage();
//                add(crp);
//                th.revalidate();
//                th.repaint();
            }
        });
        rp.add(rp_regist);

        //취소 버튼
        rp_cancel.setBounds(100, 610, 250, 70);
        rp_cancel.setFont(setFont(rp_cancel, 25.0f));
        rp_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(rp);
                add(lp);
                th.revalidate();
                th.repaint();
            }
        });
        rp.add(rp_cancel);
    } //완성

    public void setChatRoomPage() throws SQLException {
        hm.put("crp_flag", true);
        crp.setLayout(null);

        //로그아웃 버튼
        crp_logout.setBounds(0, 0, 220, 50);
        crp_logout.setFont(setFont(crp_logout, 25.0f));
        crp_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(crp);
                add(lp);
                th.revalidate();
                th.repaint();
            }
        });
        crp.add(crp_logout);

        //방 목록
        createRoomList();

        //방 찾기 버튼
        crp_searchRoom.setBounds(25, 665, 175, 70);
        crp_searchRoom.setFont(setFont(crp_searchRoom, 20.0f));
        crp_searchRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(crp);
                if (!hm.get("rlp_flag")) {
                    System.out.println(hm.get("rlp_flag"));
                    setRoomLoginPage("");
                } else {
                    rlp_idTF.setText("");
                }
                add(rlp);
                th.revalidate();
                th.repaint();
            }
        });
        crp.add(crp_searchRoom);

        //방 생성 버튼
        crp_createRoom.setBounds(225, 665, 175, 70);
        crp_createRoom.setFont(setFont(crp_createRoom, 20.0f));
        crp_createRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(crp);
                if (!hm.get("ctrp_flag")) {
                    System.out.println(hm.get("ctrp_flag"));
                    setCreateRoomPage();
                }
                add(ctrp);
                th.revalidate();
                th.repaint();
            }
        });
        crp.add(crp_createRoom);
    } //완성

    public void setRoomLoginPage(String roomid) {
        hm.put("rlp_flag", true);
        rlp.setLayout(null);

        //id
        rlp_idT.setBounds(100, 200, 100, 25);
        rlp_idT.setFont(setFont(rlp_idT, 25.0f));
        rlp.add(rlp_idT);

        rlp_idTF.setBounds(100, 225, 250, 35);
        rlp_idTF.setFont(setFont(rlp_idTF, 25.0f));
        rlp_idTF.setText(roomid);
        rlp.add(rlp_idTF);

        //pw
        rlp_pwT.setBounds(100, 270, 100, 25);
        rlp_pwT.setFont(setFont(rlp_pwT, 25.0f));
        rlp.add(rlp_pwT);

        rlp_pwTF.setBounds(100, 295, 250, 35);
        rlp_pwTF.setFont(setFont(rlp_pwTF, 25.0f));
        rlp.add(rlp_pwTF);

        //입장 버튼
        rlp_enter.setBounds(100, 530, 250, 70);
        rlp_enter.setFont(setFont(rlp_enter, 25.0f));
        rlp_enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (roomLoginCheck(rlp_idTF.getText(), rlp_pwTF.getText())) {
                        remove(rlp);
                        if (!hm.get("cp_flag")) {
                            System.out.println(hm.get("cp_flag"));
                            roomId = rlp_idTF.getText();
                            setChatPage();
                        } else {
                            cp.remove(cp_sp);
                            loadChat(rlp_idTF.getText());
                        }
                        add(cp);
                        th.revalidate();
                        th.repaint();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        rlp.add(rlp_enter);

        //취소 버튼
        rlp_cancel.setBounds(100, 610, 250, 70);
        rlp_cancel.setFont(setFont(rlp_cancel, 25.0f));
        rlp_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(rlp);
                try {
                    crp.remove(crp_sp);
                    createRoomList();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                add(crp);
                th.revalidate();
                th.repaint();
            }
        });
        rlp.add(rlp_cancel);
    } //완성

    public void setCreateRoomPage() {
        hm.put("ctrp_flag", true);
        ctrp.setLayout(null);

        //방 이름
        ctrp_roomNameT.setBounds(100, 130, 100, 25);
        ctrp_roomNameT.setFont(setFont(ctrp_roomNameT, 25.0f));
        ctrp.add(ctrp_roomNameT);

        ctrp_roomNameTF.setBounds(100, 155, 250, 35);
        ctrp_roomNameTF.setFont(setFont(ctrp_roomNameTF, 25.0f));
        ctrp.add(ctrp_roomNameTF);

        //id
        ctrp_idT.setBounds(100, 200, 100, 25);
        ctrp_idT.setFont(setFont(ctrp_idT, 25.0f));
        ctrp.add(ctrp_idT);

        ctrp_idTF.setBounds(100, 225, 250, 35);
        ctrp_idTF.setFont(setFont(ctrp_idTF, 25.0f));
        ctrp.add(ctrp_idTF);

        //pw
        ctrp_pwT.setBounds(100, 270, 100, 25);
        ctrp_pwT.setFont(setFont(ctrp_pwT, 25.0f));
        ctrp.add(ctrp_pwT);

        ctrp_pwTF.setBounds(100, 295, 250, 35);
        ctrp_pwTF.setFont(setFont(ctrp_pwTF, 25.0f));
        ctrp.add(ctrp_pwTF);

        //방 생성 버튼
        ctrp_createRoom.setBounds(100, 530, 250, 70);
        ctrp_createRoom.setFont(setFont(ctrp_createRoom, 25.0f));
        ctrp_createRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ctrp_idTF.getText().equals("") || ctrp_roomNameTF.getText().equals("")) {
                    System.out.println("빈 칸 있음");
                    jo.showMessageDialog(null, "빈 칸 있음");
                } else {
                    try {
                        if (createRoom(ctrp_idTF.getText(), ctrp_pwTF.getText(), ctrp_roomNameTF.getText())) {
                            remove(ctrp);
                            if (!hm.get("cp_flag")) {
                                System.out.println(hm.get("cp_flag"));
                                roomId = ctrp_idTF.getText();
                                setChatPage();
                            } else {
                                cp.remove(cp_sp);
                                loadChat(ctrp_idTF.getText());
                            }
                            add(cp);
                            th.revalidate();
                            th.repaint();
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
        ctrp.add(ctrp_createRoom);

        //취소 버튼
        ctrp_cancel.setBounds(100, 610, 250, 70);
        ctrp_cancel.setFont(setFont(ctrp_cancel, 25.0f));
        ctrp_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(ctrp);
                try {
                    crp.remove(crp_sp);
                    createRoomList();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                add(crp);
                th.revalidate();
                th.repaint();
            }
        });
        ctrp.add(ctrp_cancel);

    } //완성

    public void setChatPage() throws SQLException {
        hm.put("cp_flag", true);
        cp.setLayout(null);

        //방 나가기 버튼
        cp_escapeRoom.setBounds(0, 0, 217, 50);
        cp_escapeRoom.setFont(setFont(cp_escapeRoom, 25.0f));
        cp_escapeRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(cp);
                try {
                    cdb.stmt.executeUpdate("update chatroom set roomnop='"+ --roomnop +"' where roomid='"+roomId+"'");
                    crp.remove(crp_sp);
                    createRoomList();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                add(crp);
                th.revalidate();
                th.repaint();
            }
        });
        cp.add(cp_escapeRoom);

        //방 설정 버튼
        cp_setRoom.setBounds(217, 0, 217, 50);
        cp_setRoom.setFont(setFont(cp_setRoom, 25.0f));
        cp_setRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(cp);
                if (!hm.get("mcrp_flag")) {
                    System.out.println(hm.get("mcrp_flag"));
                    setModifyChatRoomPage();
                }
                mcrp_idTF.setText(roomId);
                mcrp_roomNameTF.setText(roomName);
//                mcrp_pwTF.setText(roomPw);
                add(mcrp);
                th.revalidate();
                th.repaint();
            }
        });
        cp.add(cp_setRoom);

        loadChat(roomId);
        cp_sp.getVerticalScrollBar().setValue(cp_sp.getVerticalScrollBar().getMaximum());
        //이름

        //날짜

        //타인 메시지

        //내 메시지

        //메시지 작성
        cp_msg.setBounds(0, 581, 330, 180);
        cp_msg.setFont(setFont(cp_msg, 20.0f));
        cp_msg.setLineWrap(true);
        cp.add(cp_msg);

        //전송 버튼
        cp_send.setBounds(330, 581, 104, 180);
        cp_send.setFont(setFont(cp_send, 25.0f));
        cp_send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cdb.stmt.executeUpdate("insert into chatmessage (roomid, userid, message) values ('" +
                            roomId + "', '" + myId + "', '" + cp_msg.getText() + "');");
                    jsono.put("name", myId);
                    jsono.put("data", cp_msg.getText());
                    jsono.put("roomid", roomId);
                    c.sendMsg(jsono.toString());
                    cp_msg.setText("");
                    remove(cp);
                    cp.remove(cp_sp);
                    loadChat(roomId);
                    cp_sp.getVerticalScrollBar().setValue(cp_sp.getVerticalScrollBar().getMaximum());
                    add(cp);
                    th.revalidate();
                    th.repaint();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        cp.add(cp_send);

    } //완성

    public void setModifyChatPage() {
        hm.put("mcp_flag", true);
        mcp.setLayout(null);

        // 취소 버튼
        mcp_cancel.setBounds(0, 0, 217, 50);
        mcp_cancel.setFont(setFont(mcp_cancel, 25.0f));
        mcp_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(mcp);
                cp.remove(cp_sp);
                try {
                    loadChat(roomId);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                add(cp);
                th.revalidate();
                th.repaint();
            }
        });
        mcp.add(mcp_cancel);

        //id
        mcp_idT.setBounds(25, 100, 100, 25);
        mcp_idT.setFont(setFont(mcp_idT, 25.0f));
        mcp.add(mcp_idT);

        mcp_idTF.setBounds(25, 125, 385, 35);
        mcp_idTF.setFont(setFont(mcp_idTF, 25.0f));
        mcp_idTF.setOpaque(true);
        mcp_idTF.setBackground(Color.WHITE);
        mcp.add(mcp_idTF);

        //날짜
        mcp_dateT.setBounds(25, 200, 100, 25);
        mcp_dateT.setFont(setFont(mcp_dateT, 25.0f));
        mcp.add(mcp_dateT);

        mcp_dateTF.setBounds(25, 225, 385, 35);
        mcp_dateTF.setFont(setFont(mcp_dateTF, 25.0f));
        mcp_dateTF.setOpaque(true);
        mcp_dateTF.setBackground(Color.WHITE);
        mcp.add(mcp_dateTF);

        //내용
        mcp_contT.setBounds(25, 300, 100, 25);
        mcp_contT.setFont(setFont(mcp_contT, 25.0f));
        mcp.add(mcp_contT);

        mcp_contTF.setBounds(25, 325, 385, 250);
        mcp_contTF.setFont(setFont(mcp_contTF, 20.0f));
        mcp_contTF.setLineWrap(true);
        mcp.add(mcp_contTF);

        //수정 버튼
        mcp_modify.setBounds(25, 665, 175, 70);
        mcp_modify.setFont(setFont(mcp_modify, 20.0f));
        mcp_modify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = jo.showConfirmDialog(null, "수정 하시겠습니까?");
                if (result == jo.YES_OPTION) {
                    try {
                        cdb.stmt.executeUpdate("update chatmessage set message='" + mcp_contTF.getText()
                                + "' where idx='" + mcp_idTF.getText() + "';");
                        remove(mcp);
                        cp.remove(cp_sp);
                        loadChat(roomId);
                        add(cp);
                        th.revalidate();
                        th.repaint();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
        mcp.add(mcp_modify);

        //삭제 버튼
        mcp_delete.setBounds(225, 665, 175, 70);
        mcp_delete.setBackground(new Color(244, 177, 131));
        mcp_delete.setFont(setFont(mcp_delete, 20.0f));
        mcp_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = jo.showConfirmDialog(null, "삭제 하시겠습니까?");
                if (result == jo.YES_OPTION) {
                    try {
                        cdb.stmt.executeUpdate("delete from chatmessage where idx='"+mcp_idTF.getText()+"';");
                        remove(mcp);
                        cp.remove(cp_sp);
                        loadChat(roomId);
                        add(cp);
                        th.revalidate();
                        th.repaint();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    remove(mcp);
                    add(cp);
                    th.revalidate();
                    th.repaint();
                }
            }
        });
        mcp.add(mcp_delete);
    } //완성

    public void setModifyChatRoomPage() {
        hm.put("mcrp_flag", true);
        mcrp.setLayout(null);

        // 취소 버튼
        mcrp_cancel.setBounds(0, 0, 217, 50);
        mcrp_cancel.setFont(setFont(mcrp_cancel, 25.0f));
        mcrp_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(mcrp);
                add(cp);
                th.revalidate();
                th.repaint();
            }
        });
        mcrp.add(mcrp_cancel);

        //방 이름
        mcrp_roomNameT.setBounds(100, 200, 100, 25);
        mcrp_roomNameT.setFont(setFont(mcrp_roomNameT, 25.0f));
        mcrp.add(mcrp_roomNameT);

        mcrp_roomNameTF.setBounds(100, 225, 250, 35);
        mcrp_roomNameTF.setFont(setFont(mcrp_roomNameTF, 25.0f));
        mcrp.add(mcrp_roomNameTF);

        //id
        mcrp_idT.setBounds(100, 300, 100, 25);
        mcrp_idT.setFont(setFont(mcrp_idT, 25.0f));
        mcrp.add(mcrp_idT);

        mcrp_idTF.setBounds(100, 325, 250, 35);
        mcrp_idTF.setFont(setFont(mcrp_idTF, 25.0f));
        mcrp.add(mcrp_idTF);

        //pw
        mcrp_pwT.setBounds(100, 400, 100, 25);
        mcrp_pwT.setFont(setFont(mcrp_pwT, 25.0f));
        mcrp.add(mcrp_pwT);

        mcrp_pwTF.setBounds(100, 425, 250, 35);
        mcrp_pwTF.setFont(setFont(mcrp_pwTF, 25.0f));
        mcrp.add(mcrp_pwTF);

        //수정 버튼
        mcrp_modify.setBounds(25, 665, 175, 70);
        mcrp_modify.setFont(setFont(mcrp_modify, 20.0f));
        mcrp_modify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = jo.showConfirmDialog(null, "수정 하시겠습니까?");
                if (result == jo.YES_OPTION) {
                    try {
                        if (mcrp_pwTF.getText().equals(roomPw)) {
                            cdb.stmt.executeUpdate("update chatroom set roomid = '" + mcrp_idTF.getText() + "', roomname='" +
                                    mcrp_roomNameTF.getText() + "' where roomid='" + roomId + "';");

                            cdb.stmt.executeUpdate("update chatmessage set roomid = '" + mcrp_idTF.getText() + "' where roomid='" + roomId + "';");
                            roomId = mcrp_idTF.getText();
                            roomName = mcrp_roomNameTF.getText();
                            crp.remove(crp_sp);
                            createRoomList();
                            remove(mcrp);
                            add(cp);
                            th.revalidate();
                            th.repaint();
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
        mcrp.add(mcrp_modify);

        //삭제 버튼
        mcrp_delete.setBounds(225, 665, 175, 70);
        mcrp_delete.setBackground(new Color(244, 177, 131));
        mcrp_delete.setFont(setFont(mcrp_delete, 20.0f));
        mcrp_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = jo.showConfirmDialog(null, "삭제 하시겠습니까?");
                if (result == jo.YES_OPTION) {
                    try {
                        if (mcrp_pwTF.getText().equals(roomPw)) {
                            cdb.stmt.executeUpdate("delete from chatroom where roomid='" + roomId + "';");
                            cdb.stmt.executeUpdate("delete from chatmessage where roomid='" + roomId + "';");
                            crp.remove(crp_sp);
                            createRoomList();
                            remove(mcrp);
                            add(crp);
                            th.revalidate();
                            th.repaint();
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
        mcrp.add(mcrp_delete);
    } //완성

}