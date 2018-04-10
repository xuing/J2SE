package com.nsu.MyChatRoom.Service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

public class MyClient {
	private Socket client;
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	
	public MyClient(Socket client) {
		this.client = client;
		if (client != null) {
				try {
					writer = new ObjectOutputStream(client.getOutputStream());
					reader = new ObjectInputStream(client.getInputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public Message readMessage() {
		Message message = null;
		try {
			message = (Message) reader.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;
	}

	public String getIp() {
		return client.getInetAddress().getHostAddress();
	}

	public int getPort() {
		return client.getPort();
	}

	public void sendMessage(Message message){
		try {
            writer.reset(); //日了狗!
//			if(client == null)System.out.println("client null");
//			else if(message == null)System.out.println("message null");
//			if(message.getUserList() != null)
//			System.out.println("MyClient内1输出:"+client.getPort()+" "+message.getUserList().size());
			writer.writeUnshared(message);
//			if(message.getUserList() != null)
//			System.out.println("MyClient内2输出:"+client.getPort()+" "+message.getUserList().size());
			writer.flush();
			
//			if(message.getUserList() != null)
//			System.out.println("MyClient内3输出:"+client.getPort()+" "+message.getUserList().size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
