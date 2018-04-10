package com.nsu.MyChatRoom.UI;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
import javax.xml.transform.Templates;

import com.mysql.jdbc.StatementInterceptor;
import com.nsu.MyChatRoom.Service.AccountService;
import com.nsu.MyChatRoom.Util.FrameUtil;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class RegisterFrame extends JFrame {
	private JTextField userNameText;
	private String userName,Password,imgPath = "img/temp_1.jpg";
	private JPasswordField PasswordText;
	private static int imgNumber = 0;
	private String avaPath;
	
	public RegisterFrame() {
		getContentPane().setLayout(null);
		
		userNameText = new JTextField();
		userNameText.setBounds(102, 71, 86, 24);
		getContentPane().add(userNameText);
		
		JLabel lblUserName = new JLabel("\u7528\u6237\u540D");
		lblUserName.setBounds(44, 73, 72, 18);
		getContentPane().add(lblUserName);
		
		JLabel label = new JLabel("\u5BC6\u7801");
		label.setBounds(44, 132, 72, 18);
		getContentPane().add(label);
		
		JLabel label_2 = new JLabel("\u6CE8\u518C");
		label_2.setFont(new Font("SimSun", Font.PLAIN, 33));
		label_2.setBounds(65, 10, 123, 51);
		getContentPane().add(label_2);
		
		JButton button = new JButton("\u6CE8\u518C");
		button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkInput()){
                    AccountService.addAccount(userName,Password,avaPath);
                    FrameUtil.showInfoMessage("注册成功~~");
                    LoginFrame loginFrame = new LoginFrame(userName,Password);
                    loginFrame.setVisible(true);
                    dispose();
                }
            }
		});
		button.setBounds(65, 186, 113, 27);
		getContentPane().add(button);
		
		try {
			ImageIcon img = new ImageIcon("img/reg.jpg");
			JLabel avatar = new JLabel(img);
			avatar.setBounds(219, 56, 180, 180);
			
			avatar.addMouseListener(new MouseAdapter() {

				

				@Override
				public void mouseClicked(MouseEvent e) {
				    JFileChooser chooser = new JFileChooser();
				    FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "JPG & GIF Images", "jpg", "gif");
				    chooser.setFileFilter(filter);
				    int returnVal = chooser.showOpenDialog(null);
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				        try {
				        imgPath = chooser.getSelectedFile().getAbsolutePath();
				    	avaPath = FrameUtil.listImgToAvatar(imgPath);
//						System.out.println("You chose to open this file: " + imaPath);
				    	
				    	String temp = FrameUtil.regImaToAvatar(imgPath, imgNumber++);
				    	System.out.println(temp);
						avatar.setIcon(new ImageIcon(temp));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
				    }

				}
			});
			
			getContentPane().add(avatar);

			JLabel label_1 = new JLabel("\u5934\u50CF(\u70B9\u51FB\u4E0A\u4F20)");
			label_1.setBounds(282, 35, 97, 15);
			getContentPane().add(label_1);
			
			PasswordText = new JPasswordField();
			PasswordText.setBounds(102, 131, 86, 21);
			getContentPane().add(PasswordText);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
//		 
//		
//		 getContentPane().add(background);
		
	}
	
    public static boolean rexCheckNickName(String nickName) {
        // 昵称格式：限16个字符，支持中英文、数字、减号或下划线
        String regStr = "^[\\u4e00-\\u9fa5_a-zA-Z0-9-]{1,16}$";
        return nickName.matches(regStr);
    }

    public static boolean rexCheckPassword(String input) {
        // 6-20 位，字母、数字、字符
        //String reg = "^([A-Z]|[a-z]|[0-9]|[`-=[];,./~!@#$%^*()_+}{:?]){6,20}$";
        String regStr = "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]){6,20}$";
        return input.matches(regStr);
    }

    protected boolean checkInput() {
        userName = userNameText.getText();
        Password = String.valueOf(PasswordText.getPassword());
        if (rexCheckNickName(userName) && rexCheckPassword(Password)) {
            return true;
        } else {
            FrameUtil.showErrorMessage("输入有误");
            return false;
        }

    }


}
