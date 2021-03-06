package com.h1b4.www.vo;

public class ContentsReply {
	private int reply_num;
	private int contents_num;
	private String member_id;
	private String reply_text;
	private String inputdate;
	
	public ContentsReply() {
		// TODO Auto-generated constructor stub
	}

	public ContentsReply(int reply_num, int contents_num, String member_id, String reply_text, String inputdate) {
		super();
		this.reply_num = reply_num;
		this.contents_num = contents_num;
		this.member_id = member_id;
		this.reply_text = reply_text;
		this.inputdate = inputdate;
	}

	public int getReply_num() {
		return reply_num;
	}

	public void setReply_num(int reply_num) {
		this.reply_num = reply_num;
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

	public String getReply_text() {
		return reply_text;
	}

	public void setReply_text(String reply_text) {
		this.reply_text = reply_text;
	}

	public String getInputdate() {
		return inputdate;
	}

	public void setInputdate(String inputdate) {
		this.inputdate = inputdate;
	}

	@Override
	public String toString() {
		return "ContentsReply [reply_num=" + reply_num + ", contents_num=" + contents_num + ", member_id=" + member_id
				+ ", reply_text=" + reply_text + ", inputdate=" + inputdate + "]";
	}
	
	
}
