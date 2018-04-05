package com.nsu.MyChatRoom.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import com.mysql.fabric.xmlrpc.Client;
import com.nsu.MyChatRoom.Bean.Account;
import com.nsu.MyChatRoom.Bean.UserNoPass;
import com.nsu.MyChatRoom.Service.AccountService;
import com.nsu.MyChatRoom.Service.ChatContent;
import com.nsu.MyChatRoom.Service.ClientService;
import com.nsu.MyChatRoom.Util.FrameUtil;




public class ChatRoom extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 左侧的窗口
	private JPanel leftPanel;

	private JList<String> userList;// 左侧中间的userList
	private Vector<String> userNameVector;// 因为要刷新数据，所以要把用户名添加到vector中
	private JScrollPane userListPane;// userList放在ScrollPane中

	private JPanel leftButtonPanel;// 左侧下方的按钮放在JPanel中
	private JButton connect;// 连接按钮
	private JButton disconnect;// 关闭按钮

	// 右侧聊天窗口
	private JPanel chatPanel;
	private JScrollPane historyPane;// 历史纪录
	private JTextArea historyArea;// 历史纪录

	private JScrollPane contentPane;// 输入框
	private JTextArea contentArea;// 输入框

	private JPanel buttonPane;
	private JButton singleSendButton;// 单聊按钮
	private JButton mutiSendButtonl;// 多聊按钮

	// 指令，根据指令判断点击的是哪一个按钮
	private static final String CONNECT = "connect";
	private static final String DISCONNECT = "disconnect";
	private static final String SINGGLE_SEND = "single_send";
	private static final String MUTI_SEND = "muti_send";

	private ClientService clientServer;

	private ArrayList<String> listNames = new ArrayList<>();
	private ArrayList<Icon> listIcons = new ArrayList<>(); 
	
	private DefaultListModel listModel;
	
	public ChatRoom() {
		initialUI();// 初始化界面
		setListenerAndInstruct();// 给界面中的空间添加监听器和指令
		clientServer = new ClientService();
		clientServer.setChatRoom(this);
	}

	public ChatRoom(Account account) {
		this();
		clientServer.setAccount(account);
		setTitle(account.getUserName());
	}



	private void setListenerAndInstruct() {
		connect.addActionListener(this);
		disconnect.addActionListener(this);
		singleSendButton.addActionListener(this);
		mutiSendButtonl.addActionListener(this);
		connect.setActionCommand(CONNECT);
		disconnect.setActionCommand(DISCONNECT);
		singleSendButton.setActionCommand(SINGGLE_SEND);
		mutiSendButtonl.setActionCommand(MUTI_SEND);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case CONNECT:
			if(clientServer.connectServer()){
//							JOptionPane.showMessageDialog(null, "连接成功");
			}else {
				FrameUtil.showErrorMessage("找不到此主机或但口号不存在，请修改后重试");
			}

//				System.out.println("dili");
			setSetting(false);
			System.out.println("回到主线程");
			break;
		case DISCONNECT:
//			disconnectServer();  //TODO 改为注销
			break;
		case SINGGLE_SEND:
//			singleSendMsg();  //TODO 检测不能含有`
			break;
		case MUTI_SEND:
//			mutiSend();
			break;
		default:
			break;
		}
	}

	private void setSetting(boolean b) {
		connect.setEnabled(b);
		disconnect.setEnabled(!b);
	}
	
	
	private void initialUI() {
		//TODO

		listModel = new DefaultListModel();

		userList = new JList(listModel);
		userListPane = new JScrollPane(userList);
		userListPane.setBorder(BorderFactory.createTitledBorder("在线用户列表"));
		
		
		// ButtonPane
		connect = new JButton("连接");
		disconnect = new JButton("注销");
		disconnect.setEnabled(false);
		leftButtonPanel = new JPanel(new FlowLayout());
		leftButtonPanel.add(connect);
		leftButtonPanel.add(disconnect);

		// 往左侧布局中添加三个以上panel
		leftPanel = new JPanel(new BorderLayout());
		leftPanel.add(userListPane, BorderLayout.CENTER);
		leftPanel.add(leftButtonPanel, BorderLayout.SOUTH);

		// 历史纪录窗口
		historyArea = new JTextArea();
		historyArea.setEditable(false);
		historyArea.setPreferredSize(new Dimension(283, 283));

		historyPane = new JScrollPane(historyArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		historyPane.setBorder(BorderFactory.createTitledBorder("历史纪录"));

		// 输入框
		contentArea = new JTextArea();
		contentPane = new JScrollPane(contentArea);
		contentPane.setBorder(BorderFactory.createTitledBorder("输入消息框"));

		// 发送按钮Pane
		buttonPane = new JPanel(new FlowLayout());
		singleSendButton = new JButton("发送");
		mutiSendButtonl = new JButton("群发");
		buttonPane.add(singleSendButton);
		buttonPane.add(mutiSendButtonl);

		chatPanel = new JPanel(new BorderLayout());
		chatPanel.add(historyPane, BorderLayout.NORTH);
		chatPanel.add(contentPane, BorderLayout.CENTER);
		chatPanel.add(buttonPane, BorderLayout.SOUTH);

		getContentPane().setLayout(new GridLayout(1, 2, 20, 30));
		getContentPane().add(leftPanel);
		getContentPane().add(chatPanel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 点击“x”关闭窗口
		pack(); // 调整窗口大小，以适应子组件的首选大小
		setVisible(true);
		setBounds(0, 0, 600, 500);
		setTitle("ChatClient");
		setResizable(false);
	}

	
	public void historyAppend(String fromUser, ChatContent content) {
		historyArea.append(fromUser+"对你说："+content.getContent()+"\n");	
	}

	public void userUpdate(UserNoPass[] datas) {
		System.out.println("datas大小:"+datas.length);
		listNames.clear();
		listIcons.clear();
		listModel = new DefaultListModel();
		int i = 0;
		for (UserNoPass user : datas) {
			if(user !=null){
				listModel.add(i,user.getUserName());
				listIcons.add(new ImageIcon(user.getAvatar()));
				i++;
			}else{
				break;
			}
		}
		userList.setCellRenderer(new MyCellRenderer(listIcons));
		userList.setModel(listModel);
	}

}
