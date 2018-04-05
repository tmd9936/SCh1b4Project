package com.h1b4.www.vo;


public class Bookmark {
	private int bookmark_num;
	private String member_id;
	private int contents_num;
	private String inputdate;
	
	public Bookmark() {
		// TODO Auto-generated constructor stub
	}

	public Bookmark(int bookmark_num, String member_id, int contents_num, String inputdate) {
		super();
		this.bookmark_num = bookmark_num;
		this.member_id = member_id;
		this.contents_num = contents_num;
		this.inputdate = inputdate;
	}

	public int getBookmark_num() {
		return bookmark_num;
	}

	public void setBookmark_num(int bookmark_num) {
		this.bookmark_num = bookmark_num;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public int getContents_num() {
		return contents_num;
	}

	public void setContents_num(int contents_num) {
		this.contents_num = contents_num;
	}

	public String getInputdate() {
		return inputdate;
	}

	public void setInputdate(String inputdate) {
		this.inputdate = inputdate;
	}

	@Override
	public String toString() {
		return "Bookmark [bookmark_num=" + bookmark_num + ", member_id=" + member_id + ", contents_num=" + contents_num
				+ ", inputdate=" + inputdate + "]";
	}

	
	
	
}
