package com.daelim;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CClient extends JFrame {
    CDataBase cdb;
    String uriString = "ws://61.83.168.88:4877";
    CSocket c;
    String nick = null;

    String myId = null;
    String myPw = null;

    JFrame th;
    GridBagLayout Gbag = new GridBagLayout();

    HashMap<String, Boolean> hm = new HashMap<>();

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

    JTextArea cp_othersChat = null;
    JTextArea cp_myChat = null;

    JTextArea cp_msg = new JTextArea();
    JButton cp_send = new JButton("전송");

    //채팅 수정 페이지
    JPanel mcp = new JPanel();

    JButton mcp_cancel = new JButton("취소");

    JLabel mcp_idT = new JLabel("id");
    JTextField mcp_idTF = new JTextField();

    JLabel mcp_dateT = new JLabel("날짜");
    JTextField mcp_dateTF = new JTextField();

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

    public void create_form(Component cmpt, int x, int y, float wx, float wy){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(25, 25, 0, 20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = wx;
        gbc.weighty = wy;
        cmpt.setFont(setFont((JComponent)cmpt, 25.0f));
        crp_scp.add(cmpt, gbc);

    }

    void loginCheck() {
        if (lp_idTF.getText().equals("") || lp_pwTF.getText().equals("")) {
            System.out.println("아이디나 패스워드 입력 안됨");
//            jp0.add(idPwW);
//            th.revalidate();
//            th.repaint();
        } else {
            try {
                cdb.rs = cdb.stmt.executeQuery("Select userpw from member where userid='" + lp_idTF.getText() + "'");
                if (!cdb.rs.next()) {
                    System.out.println("id 일치하지 않음");
//                    jp0.add(idNfW);
//                    th.revalidate();
//                    th.repaint();
                } else {
                    if (cdb.rs.getString("userpw").equals(lp_pwTF.getText())) {
                        System.out.println("로그인 됨");
                        myId = lp_idTF.getText();
                        myPw = lp_pwTF.getText();
                        remove(lp);
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
                        System.out.println("비밀번호 일치하지 않음");
//                        jp0.add(pwNfW);
//                        th.revalidate();
//                        th.repaint();
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    void signUp() {
//        removeWM();
        if (rp_idTF.getText().equals("") || rp_pwTF.getText().equals("") || rp_pwrTF.getText().equals("") || rp_nameTF.getText().equals("") || rp_emailTF.getText().equals("")) {
            System.out.println("빈칸 있음");
//            jp0_1.add(nullW);
//            th.revalidate();
//            th.repaint();
        } else if (!rp_pwTF.getText().equals(rp_pwrTF.getText())) {
            System.out.println("비밀번호가 일치하지 않음");
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
        System.out.println("방리스트 생성 메서드 실행");
        cdb.rs = cdb.stmt.executeQuery("select * from chatroom");
        int i = 0;
        crp_scp = new JPanel();
        crp_scp.setLayout(Gbag);
        crp_sp = new JScrollPane(crp_scp, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        crp_sp.setBounds(0, 50, 435, 585);
        crp_sp.getVerticalScrollBar().setUnitIncrement(16); // 마우스 휠 스크롤 속도
        crp_scp.setBackground(new Color(208, 206, 206));
        while(cdb.rs.next()) {
            crp_roomName = new JButton(cdb.rs.getString("roomname"));
            crp_roomName.addActionListener(new ActionListener() {
                ResultSet rs = cdb.rs;
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        System.out.println(rs.getString("roomid"));
                        System.out.println(rs.getString("roompw"));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
            crp_nop = new JLabel(cdb.rs.getString("roomnop"));
            create_form(crp_roomName, 0, 2*i, 10.0f, 1);
            create_form(crp_nop, 1, 2*i, 0.1f, 1);
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

    void createRoom(String roomid, String roompw, String roomname) throws SQLException {
        int roomnop = 0;
        String userid = myId;
        String sql = "INSERT INTO chatroom(roomid, roompw, roomname, roomnop, userid) values('"
                +roomid+"', '"+roompw+"', '"+roomname+"', "+roomnop+", '"+userid+"')";
        cdb.stmt.executeUpdate(sql);
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
        add(lp);
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
                    setRoomLoginPage();
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

    public void setRoomLoginPage() {
        hm.put("rlp_flag", true);
        rlp.setLayout(null);

        //id
        rlp_idT.setBounds(100, 200, 100, 25);
        rlp_idT.setFont(setFont(rlp_idT, 25.0f));
        rlp.add(rlp_idT);

        rlp_idTF.setBounds(100, 225, 250, 35);
        rlp_idTF.setFont(setFont(rlp_idTF, 25.0f));
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
                remove(rlp);
                if (!hm.get("cp_flag")) {
                    System.out.println(hm.get("cp_flag"));
                    setChatPage();
                }
                add(cp);
                th.revalidate();
                th.repaint();
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
                if (ctrp_idTF.getText().equals("") || ctrp_pwTF.getText().equals("") || ctrp_roomNameTF.getText().equals("")) {
                    System.out.println("빈 칸 있음");
                } else {
                    try {
                        createRoom(ctrp_idTF.getText(), ctrp_pwTF.getText(), ctrp_roomNameTF.getText());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    remove(ctrp);
                    if (!hm.get("cp_flag")) {
                        System.out.println(hm.get("cp_flag"));
                        setChatPage();
                    }
                    add(cp);
                    th.revalidate();
                    th.repaint();
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

    public void setChatPage() {
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
                add(mcrp);
                th.revalidate();
                th.repaint();
            }
        });
        cp.add(cp_setRoom);

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
        mcp.add(mcp_idTF);

        //날짜
        mcp_dateT.setBounds(25, 200, 100, 25);
        mcp_dateT.setFont(setFont(mcp_dateT, 25.0f));
        mcp.add(mcp_dateT);

        mcp_dateTF.setBounds(25, 225, 385, 35);
        mcp_dateTF.setFont(setFont(mcp_dateTF, 25.0f));
        mcp.add(mcp_dateTF);

        //내용
        mcp_contT.setBounds(25, 300, 100, 25);
        mcp_contT.setFont(setFont(mcp_contT, 25.0f));
        mcp.add(mcp_contT);

        mcp_contTF.setBounds(25, 325, 385, 250);
        mcp_contTF.setFont(setFont(mcp_contTF, 20.0f));
        mcp.add(mcp_contTF);

        //수정 버튼
        mcp_modify.setBounds(25, 665, 175, 70);
        mcp_modify.setFont(setFont(mcp_modify, 20.0f));
        mcp_modify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(mcp);
                add(cp);
                th.revalidate();
                th.repaint();
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
                remove(mcp);
                add(cp);
                th.revalidate();
                th.repaint();
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
                remove(mcrp);
                add(cp);
                th.revalidate();
                th.repaint();
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
                remove(mcrp);
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
        mcrp.add(mcrp_delete);
    } //완성

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