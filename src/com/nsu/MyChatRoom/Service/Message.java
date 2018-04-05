package com.nsu.MyChatRoom.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import com.nsu.MyChatRoom.Bean.Account;
import com.nsu.MyChatRoom.Bean.UserNoPass;

//当前用户名、聊天对象、聊天内容、聊天语气和是否私聊进行
public  class Message implements Serializable {
	private UserNoPass userInfo;
	private String targetName;
	private ChatContent content;
	private String other;
	private int flag;
	private UserNoPass[] userList;
	

	public static final int NOTIFY_SERVER = 0; //TODO 改成枚举可能更好.
	public static final int SINGLE_CHAT = 1;
	public static final int UPDATE_USERLIST = 2;
	
//	public Message(String myName,String myIp,int flag){
//		this.myIp = myIp;
//		this.myName = myName;
//		this.flag = flag;
//	}
	
	public Message(UserNoPass userInfo, String targetName, ChatContent content, String other,UserNoPass[] userlist, int flag) {
		super();
		this.userInfo = userInfo;
		this.targetName = targetName;
		this.content = content;
		this.other = other;
		this.userList = userlist;
		this.flag = flag;
	}

	@Override
	public String toString() {
		return new String("myName:"+userInfo.getUserName()+
				"\n myAvator:"+userInfo.getAvatar()+
				"\n targetName:"+targetName+
				"\n content:"+content+
				"\n other:"+other+
				"\n flag:"+flag);
	}

	
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public UserNoPass getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserNoPass userInfo) {
		this.userInfo = userInfo;
	}

	public UserNoPass[] getUserList() {
		return userList;
	}

	public void setUserList(UserNoPass[] userList) {
		this.userList = userList;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public ChatContent getContent() {
		return content;
	}
	public void setContent(ChatContent content) {
		this.content = content;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
}

