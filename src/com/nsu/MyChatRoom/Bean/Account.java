package com.nsu.MyChatRoom.Bean;


public class Account extends UserNoPass{
	
	private String password;
	
	public Account() {
	}
	
	public Account(String userName, String password, String avatar) {
		super(userName,avatar);
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Account a = (Account)obj;
		return a.getUserName().equals(getUserName()) && a.getPassword().equals(password);
	}
	
	
}
