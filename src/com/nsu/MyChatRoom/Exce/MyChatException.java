package com.nsu.MyChatRoom.Exce;

public class MyChatException extends Exception {
	private String content;
	
	public MyChatException(String content) {
		this.content = content;
	}
	
	public MyChatException() {
	
	}


	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
