package com.nsu.MyChatRoom.Bean;

import java.io.Serializable;

public class UserNoPass implements Serializable{
	private String userName;
	private String avatar;

	public UserNoPass() {
	}
	
	public UserNoPass(String userName, String avatar) {
		this.userName = userName;
		this.avatar = avatar;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return "userName:"+userName+" avatar:"+avatar;
	}

}
