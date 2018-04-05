package com.h1b4.www.vo;

public class Emotion {
	private int emotion_num;
	private int contents_num;
	private String member_id;
	private String state;
	
	public Emotion() {
		// TODO Auto-generated constructor stub
	}

	public int getEmotion_num() {
		return emotion_num;
	}

	public void setEmotion_num(int emotion_num) {
		this.emotion_num = emotion_num;
	}

	public int getContents_num() {
		return contents_num;
	}

	public void setContents_num(int contents_num) {
		this.contents_num = contents_num;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Emotion [emotion_num=" + emotion_num + ", contents_num=" + contents_num + ", member_id=" + member_id
				+ ", state=" + state + "]";
	}
	
	
}
