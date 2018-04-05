package com.nsu.MyChatRoom.UI;

import java.awt.Font;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.skin.RavenSkin;
import org.jvnet.substance.skin.SaharaSkin;

import com.nsu.MyChatRoom.Bean.Account;
import com.nsu.MyChatRoom.Bean.UserNoPass;
import com.nsu.MyChatRoom.Exce.MyChatException;
import com.nsu.MyChatRoom.Service.*;
import com.nsu.MyChatRoom.Util.DBTool;
import com.nsu.MyChatRoom.Util.FrameUtil;

public class LoginFrame extends JFrame {
	private JButton btn_cancel;
	private JButton btn_login;
	private JPasswordField passwordField;
	private JTextField userNameField;
	private JButton button;
	private String defaultUserName;
	private String defaultPassword;
	
	
	public LoginFrame() {
		
		setBounds(FrameUtil.FrameBound+600, FrameUtil.FrameBound+300, 400, 300);
		FrameUtil.FrameBound += 200;
		setTitle("登录程序");
		
		initPane();
	
		addMyListener();
		
	}
	
	

	public LoginFrame(String defaultUserName, String defaultPassword) {
		this();
  		userNameField.setText(defaultUserName);
		passwordField.setText(defaultPassword);
	}



	private void addMyListener() {
  		btn_cancel.addActionListener(new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					userNameField.setText("");
					passwordField.setText("");
					userNameField.requestFocus();
				}
			});
  		
  		btn_login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String userName,password;
				userName = userNameField.getText().trim();
				password = String.valueOf(passwordField.getPassword());
				Account account = AccountService.loginVerify(userName,password);
				System.out.println("点击登录后信息:"+account.getUserName());
				if(null != account){
					ChatRoom chatRoom = new ChatRoom(account);
					chatRoom.setBounds(FrameUtil.FrameBound, FrameUtil.FrameBound, 600, 600);
					FrameUtil.FrameBound -= 200;
					chatRoom.setVisible(true);
					dispose();
				}
				
			}
		});
	}

	private void initPane() {
		getContentPane().setLayout(null);
  		
  		JLabel lblUserName = new JLabel("\u7528\u6237\u540D:");
  		lblUserName.setBounds(118, 45, 54, 15);
  		getContentPane().add(lblUserName);
  		
  		JLabel lblPassword = new JLabel("\u5BC6\u7801:");
  		lblPassword.setBounds(118, 131, 54, 15);
  		getContentPane().add(lblPassword);
  		
  		userNameField = new JTextField();
  		userNameField.setBounds(182, 42, 119, 21);
  		getContentPane().add(userNameField);
  		userNameField.setColumns(10);
  		
  		btn_login = new JButton("\u767B\u5F55");
  
  		btn_login.setBounds(30, 203, 93, 23);
  		getContentPane().add(btn_login);
  		  		
  		btn_cancel = new JButton("\u53D6\u6D88");
  		btn_cancel.setBounds(259, 203, 93, 23);
  		getContentPane().add(btn_cancel);
  		
  		passwordField = new JPasswordField();
  		passwordField.setBounds(182, 126, 119, 20);
  		getContentPane().add(passwordField);
  		
  		userNameField.requestFocus();
  		

  		
  	
  		getRootPane().setDefaultButton(btn_login);
  		
  		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try {
			//UIManager.setLookAndFeel(new SubstanceLookAndFeel());
			// UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel());
			// SubstanceLookAndFeel.setSkin(new CremeSkin());//白色
			// SubstanceLookAndFeel.setSkin(new BusinessSkin());//灰色
			// SubstanceLookAndFeel.setSkin(new AutumnSkin());//粉红色
			// SubstanceLookAndFeel.setSkin(new BusinessBlueSteelSkin());//青灰色
			// SubstanceLookAndFeel.setSkin(new BusinessBlackSteelSkin());//黑色
			// SubstanceLookAndFeel.setSkin(new ChallengerDeepSkin());//深紫色
			// SubstanceLookAndFeel.setSkin(new CremeCoffeeSkin());//白色
			 SubstanceLookAndFeel.setSkin(new SaharaSkin());//浅绿色
			// SubstanceLookAndFeel.setSkin(new RavenSkin());//黑色
			// SubstanceLookAndFeel.setSkin(new OfficeSilver2007Skin());//浅灰色
			// SubstanceLookAndFeel.setSkin(new NebulaSkin());//灰白色
			// SubstanceLookAndFeel.setSkin(new ModerateSkin());//灰青色
			// SubstanceLookAndFeel.setSkin(new ModerateSkin());//灰青色
			// SubstanceLookAndFeel.setCurrentWatermark(new
			// SubstanceBubblesWatermark());
			// SubstanceLookAndFeel.setCurrentGradientPainter(new
			// StandardGradientPainter());
			// SubstanceLookAndFeel.setCurrentBorderPainter(new
			// StandardBorderPainter());//水蓝色
			// SubstanceLookAndFeel.setCurrentTheme(new
			// SubstanceTerracottaTheme());//黄色
			// SubstanceLookAndFeel;
		} catch (Exception e) {
			e.printStackTrace();
		}
  		setMybackground();		
	}

	private void setMybackground() {
		 ImageIcon img = new ImageIcon("img/1.jpg");
		 
		 button = new JButton("\u6CE8\u518C");
		 button.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		//					register();
				//					FrameUtil.showInfoMessage("注册成功");
	 			RegisterFrame registerFrame = new RegisterFrame();
	 			registerFrame.setBounds(600, 300, 448, 300);
	 			registerFrame.setVisible(true);
	 			dispose();
		 	}

//			private void register() throws MyATMException {
//				String name = FrameUtil.InputString("请输入用户名");
//				String password = FrameUtil.InputString("请输入密码");
//				int money = FrameUtil.InputInt("请输入金额");
//				//AccountService.register(name, password, money);	
//			}
		 });
		 button.setBounds(152, 203, 93, 23);
		 getContentPane().add(button);
//		 JLabel background = new JLabel(img);
//		 background.setFont(new Font("宋体", Font.PLAIN, 18));
//		 
//		 background.setBounds(0, 0, 700, 500);
//		 getContentPane().add(background);
	}
	
	public static void main(String[] args) throws Exception {
		ChatSever chatSever = new ChatSever(true);
		chatSever.setVisible(true);
		Thread.sleep(200);
		LoginFrame loginFrame = new LoginFrame("xuing","123456");
		loginFrame.setVisible(true);
		Thread.sleep(200);
		LoginFrame loginFrame2 = new LoginFrame("cc","123456");
		loginFrame2.setVisible(true);
		Thread.sleep(200);
		LoginFrame loginFrame3 = new LoginFrame("wangyong","wangyong");
		loginFrame3.setVisible(true);
//		 
////		RegisterFrame registerFrame = new RegisterFrame();
////			registerFrame.setBounds(600, 300, 448, 300);
////			registerFrame.setVisible(true);


//		TableFrame tableFrame = new TableFrame(AccountDao.getAccounts());
//		tableFrame.setVisible(true);
		

//		Vector<UserNoPass> userlist = new Vector<>();
//		userlist.addElement(new UserNoPass("xuing",FrameUtil.listImgToAvatar("img\\temp_0.jpg")));
//		userlist.addElement(new UserNoPass("cc",FrameUtil.listImgToAvatar("img\\temp_3.jpg")));
//		userlist.addElement(new UserNoPass("bilibili", FrameUtil.listImgToAvatar("img\\temp_2.jpg")));
//		
//		ChatRoom chatRoom = new ChatRoom();
//		chatRoom.setBounds(0, 0, 500, 500);
//		chatRoom.userUpdate(userlist);
//		chatRoom.setVisible(true);
		
	}
}
