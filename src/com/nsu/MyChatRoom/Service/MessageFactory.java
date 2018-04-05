package com.nsu.MyChatRoom.Service;

import java.util.Vector;

import com.nsu.MyChatRoom.Bean.UserNoPass;

public class MessageFactory {
	public static Message notifyServerOnline(UserNoPass user){
		return new Message(user,null, null, null, null, Message.NOTIFY_SERVER);
	}
	
	public static Message updateUserList(UserNoPass[] userList){
		return new Message(null,null, null, null, userList, Message.UPDATE_USERLIST);
	}
	
}
