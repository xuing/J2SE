package com.nsu.MyChatRoom.Service;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.nsu.MyChatRoom.Bean.UserNoPass;
import com.nsu.MyChatRoom.UI.ChatSever;
import com.nsu.MyChatRoom.UI.ServiceConstans;
public class Service {
	private static Vector<UserNoPass> userNameList;
	private static ServerSocket server;
	private static ChatSever chatSever;
	private static HashMap<UserNoPass,MyClient> nameMap;
	public Service(ChatSever chatSever) {
		this.chatSever = chatSever;
	}

	public void startServer() {
		userNameList = new Vector<>();
		nameMap = new HashMap<>();
		/**
		 * ����������̣߳����޽��ܿͻ�������
		 */
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					server = new ServerSocket(AccountService.ServicePort);
					System.out.println("�������ڶ˿ں�Ϊ" + ServiceConstans.SERVER_PORT + "�Ķ˿ڿ���");
					chatSever.showMessage("�������ڶ˿ں�Ϊ" + ServiceConstans.SERVER_PORT + "�Ķ˿ڿ���");
					while (true) {
						Socket client = server.accept();
						System.out.println("IP��ַΪ" + client.getInetAddress().getHostAddress() + "�Ŀͻ���������"); //TODO ����ɾ
						MyClient myClient = new MyClient(client);
//						reader = new ObjectInputStream(client.getInputStream());
//						writer = new ObjectOutputStream(client.getOutputStream());
//						writers.add(writer);
//						readers.add(reader);
//						sockets.add(client);// ���ͻ��˵�socket��ӵ�������
						// �����߳���clientͨ��
						new TransMsg(nameMap,myClient).start();
						//nameMap����UserNoPass��client����,���߳�ҪMap�͵�ǰsocket
					}
				} catch (Exception e) {

				}
			}
		}).start();
		

	}

	public static void stopServer() {
		// TODO Auto-generated method stub
		
	}

	static class TransMsg extends Thread {
		private MyClient myClient;
		private HashMap<UserNoPass,MyClient> userMap;
		
		public TransMsg(HashMap<UserNoPass,MyClient> nameMap,MyClient myClient) {
			this.userMap = nameMap;
			this.myClient = myClient;
		}

//		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			Message message;
			while ((message = myClient.readMessage())!=null) {
				System.out.println("���յ���Ϣ");
				switch (message.getFlag()) {
				case Message.SINGLE_CHAT:
					break;
				case Message.NOTIFY_SERVER:
					System.out.println(message);
//						System.out.println(client.getPort()+" "+client.getLocalAddress());
//						System.out.println("Writes��С:"+writers.size());
					NotifyServer(message);
					break;
				default:
					break;
				}
			}
		}

		private synchronized void NotifyServer(Message message) {
			UserNoPass user = message.getUserInfo();
			userMap.put(user, myClient);
			System.out.println("UserNoPass:"+user);
			UserNoPass[] userInfos = chatSever.addUser(user,myClient.getIp(),myClient.getPort());
			try {
				sendMessage(MessageFactory.updateUserList(userInfos));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private synchronized void sendMessage(Message message) throws IOException {
//			for (ObjectOutputStream writer : writers) {
//				writer.writeObject(message);
//				System.out.println("����ʱdatas��С:"+message.getUserList().size());
//				writer.flush();
//			}
//			Map<String, String> map = new HashMap<String, String>();
			
			UserNoPass userNoPass;
			MyClient myClient;
			for (Entry<UserNoPass,MyClient> entry : nameMap.entrySet()) {
				userNoPass = entry.getKey();
				myClient = entry.getValue();
				myClient.sendMessage(message);
				System.out.println("sendMessage�����:"+myClient.getPort()+" "+message.getUserList().length);
			}
		}
	}
	

}
