package com.nsu.MyChatRoom.Service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public	class TransMsg extends Thread {
private Socket client;
private ArrayList<Socket> sockets;

public TransMsg(ArrayList<Socket> sockets, Socket client) {
	this.sockets = sockets;
	this.client = client;
}

//@SuppressWarnings("deprecation")
@Override
public void run() {
	try {
		 ObjectInputStream reader = new ObjectInputStream(client.getInputStream());
		 Message message;
		while ((message = (Message) reader.readObject())!=null) {
			switch (message.getFlag()) {
			case Message.SINGLE_CHAT:
				break;
			case Message.NOTIFY_SERVER:
				break;
			default:
				break;
			}
		}
	} catch (IOException | ClassNotFoundException e) {
		e.printStackTrace();
	}
}
}

