package com.nsu.MyChatRoom.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.jar.Attributes.Name;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.nsu.MyChatRoom.Util.FrameUtil;
import com.nsu.MyChatRoom.Bean.UserNoPass;
import com.nsu.MyChatRoom.Service.Service;
import com.nsu.MyChatRoom.UI.ServiceConstans;;

public class ChatSever extends JFrame implements ActionListener {

	private ArrayList<Socket> sockets;

	private Vector<String> userNameList;
	private UserNoPass[] userInfoList;
	
	private ObjectInputStream reader;
	private ObjectOutputStream writer;

	private static Integer userSize = 0;
	
	// 声明控件
	private JList<String> userList; // 在线用户列表
	private JScrollPane userListPane; // 将userList放到ScrollPane中
	public JTextArea messageTextArea; // 聊天消息框
	private JScrollPane messageTextPane;// 将聊天消息框放到ScrollPane中
	private JPanel topPanel; // 包含userList和message
	private JButton startServer; // 开启服务器的按钮
	private JButton stopServer; // 关闭服务器的按钮
	private JPanel buttonPanel; // 将按钮放到Panel中
	private JPanel mySelf;

	// 声明指令
	private static final String START = "start";
	private static final String STOP = "stop";
	private static final String NOTIFY = "notify";
	private static final String GETOUT = "getOut";
	private static final String SAVA = "save";
	

	private ServerSocket server;
	private JButton sendNofity;
	private JButton getOutUser;
	private JButton saveLog;
	private Service service;

	
	public ChatSever() {
		initUI();
		setListenerAndInstruct();
		service = new Service(this);
	}
	
	public ChatSever(boolean start){
		this();
		if(start){
			startServer();
		}
	}


	private void startServer() {
		userInfoList = new UserNoPass[100];
		userNameList = new Vector<>();
		service.startServer();
		startServer.setEnabled(false);
		stopServer.setEnabled(true);
	}

	/**
	 * 为控件添加监听器
	 */
	private void setListenerAndInstruct() {
		startServer.addActionListener(this);
		stopServer.addActionListener(this);
		sendNofity.addActionListener(this);
		getOutUser.addActionListener(this);
		saveLog.addActionListener(this);
		sendNofity.setActionCommand(NOTIFY);
		getOutUser.setActionCommand(GETOUT);
		saveLog.setActionCommand(SAVA);
		startServer.setActionCommand(START);
		stopServer.setActionCommand(STOP);
	}

	/**
	 * 初始化服务端UI
	 */
	private void initUI() {

		// 左边的userList
		userList = new JList<>();// 实例化userList
		userListPane = new JScrollPane();// 将userList放到ScrollPane中
		userListPane.getViewport().setView(userList);
		userListPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		userListPane.setOpaque(false); // 不绘制内部元素
		userListPane.setPreferredSize(new Dimension(200, 130));
		userListPane.setBorder(BorderFactory.createTitledBorder("online user list:")); // 给userListPane添加边界title

		// 右边的message窗口
		messageTextArea = new JTextArea();// 实例化消息框
		messageTextArea.setPreferredSize(new Dimension(400, 200));
		messageTextArea.setEditable(false);
		messageTextPane = new JScrollPane(messageTextArea);// 将消息框放到ScollPane中
		messageTextPane.setOpaque(false);// 不绘制内部元素
		messageTextPane.setBorder(BorderFactory.createTitledBorder("Message:"));// 给messageTextPane添加边界title

		// TopPanel
		topPanel = new JPanel(new GridLayout(1, 2, 10, 2));// 一行两列
		topPanel.add(userListPane);
		topPanel.add(messageTextPane);

		// 下方的ButtonPanel
		startServer = new JButton("start");// 实例化开启按钮
		startServer.setEnabled(true);
		stopServer = new JButton("stop");// 实例化关闭按钮
		stopServer.setEnabled(false);
		buttonPanel = new JPanel(new FlowLayout());// 设置流式布局，默认居中对齐
		buttonPanel.add(startServer);
		buttonPanel.add(stopServer);
		mySelf = new JPanel();

		// 添加几个主要的布局部分
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(topPanel, BorderLayout.NORTH);
		getContentPane().add(mySelf, BorderLayout.WEST);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		sendNofity = new JButton("发送通知");

		buttonPanel.add(sendNofity);
		
		getOutUser = new JButton("踢出该用户");

		buttonPanel.add(getOutUser);
		
		saveLog = new JButton("保存日志与消息");

		buttonPanel.add(saveLog);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 点击“x”关闭窗口
		pack(); // 调整窗口大小，以适应子组件的首选大小
		setVisible(true);
		setTitle("ChatServer");
		// setBounds(0, 0, 600, 700);
		setResizable(false);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case START:
			startServer();
			break;
		case STOP:
			// JOptionPane.showMessageDialog(null, "点击了stop");
			service.stopServer();// 关闭服务器
			break;
		case NOTIFY:
			break;
		case GETOUT:
			break;
		case SAVA:
			break;
		default:
			break;
		}

	}

	private boolean LegalityCheck(String content){
		for (String legalStr : ServiceConstans.legals) {
			if(content.contains(legalStr))return false;
		}
		return true;
	}

	public void showMessage(String string) {
//		FrameUtil.showInfoMessage(string);
		messageTextArea.append(string+"\n");
	}
	
	
//	public void updateUserList(String line) {
//	} else if (line.contains(ServiceConstans.GET_NAME_IP_FROM_CLIENT)) {
//		if (!userNameList.contains(line.split(ServiceConstans.GET_NAME_IP_FROM_CLIENT)[0]
//				+ line.split(ServiceConstans.GET_NAME_IP_FROM_CLIENT)[1])) {
//			userNameList.addElement(line.split(ServiceConstans.GET_NAME_IP_FROM_CLIENT)[0]
//					+ line.split(ServiceConstans.GET_NAME_IP_FROM_CLIENT)[1]);
//		}
//		userList.setModel(new DefaultComboBoxModel<>(userNameList));
//		messageTextArea.append(line.split(ServiceConstans.GET_NAME_IP_FROM_CLIENT)[0]+"上线啦\n");
//		updateUserList();
//	}
	public UserNoPass[] addUser(UserNoPass user, String hostAddress, int localPort) {
		String name = user.getUserName();
		userInfoList[userSize]=user;
		userSize++;
		userNameList.addElement(name+"-"+hostAddress+"-"+localPort);
		userList.setModel(new DefaultComboBoxModel<>(userNameList));
		messageTextArea.append(name+"上线啦\n");
		return userInfoList;
	}
	
	
	
}
