package com.nsu.MyChatRoom.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import org.jvnet.substance.utils.SubstanceConstants.ScrollPaneButtonPolicyKind;

import com.nsu.MyChatRoom.Bean.Account;
import com.nsu.MyChatRoom.UI.ChatRoom;
import com.nsu.MyChatRoom.UI.Constants;
import com.nsu.MyChatRoom.Util.FrameUtil;



public class ClientService {
	private Account account;
	private ChatRoom chatUI;
	private MyClient myClient;
	private Message message;
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public boolean connectServer() {

		try {
			String ip = AccountService.ServiceIp;
			int port = AccountService.ServicePort;

			// 验证完参数配置后即可连接服务器
			Socket socket = new Socket(ip, port);  //writer和reader的顺序问题很重要!
			myClient = new MyClient(socket);
			myClient.sendMessage(MessageFactory.notifyServerOnline(account));
			// 开启线程接收服务端转发的数据
			new receiveMessage().start();
			
			return true;
		} catch (IOException e) {
			System.out.println("IOException");
			return false;
		}
	}
	
	class receiveMessage extends Thread{
		@Override
		public void run() {
			synchronized(this){
				while ((message = myClient.readMessage()) != null) {
					System.out.println("客户端"+account.getUserName()+"接受到的大小:"+message.getUserList().length);
					switch (message.getFlag()) {
						case Message.SINGLE_CHAT:
							String fromUser = message.getTargetName();
							ChatContent content = message.getContent();
							chatUI.historyAppend(fromUser,content);
							break;
						case Message.UPDATE_USERLIST:
							chatUI.userUpdate(message.getUserList());
						default:
							break;
					}
				}
			}
		}
			
	}

	public void setChatRoom(ChatRoom chatRoom) {
		this.chatUI = chatRoom;
	}
	
}
