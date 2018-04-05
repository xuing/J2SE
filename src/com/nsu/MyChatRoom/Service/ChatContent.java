package com.nsu.MyChatRoom.Service;

import java.io.Serializable;

public class ChatContent implements Serializable{
	private String content;
	private String img;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@Override
	public String toString() {
		return new String("content:"+content+"\n img:"+img);
	}
	
}
