package com.h1b4.www.vo;


import com.google.gson.Gson;

public class Message {
	private String message;
	private String type;
	private String to;
	
	public static Message convertMessage(String source){
		Message message = new Message();
		Gson gson = new Gson();
		message = gson.fromJson(source, Message.class);
		
		return message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}
