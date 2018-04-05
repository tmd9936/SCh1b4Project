package com.h1b4.www.vo;


public class Member {
	private String member_id;
	private String member_password;
	private String member_name;
	private String nation;
	private int point;
	
	public Member() {
		// TODO Auto-generated constructor stub
	}

	public Member(String member_id, String member_password, String member_name, String nation, int point) {
		super();
		this.member_id = member_id;
		this.member_password = member_password;
		this.member_name = member_name;
		this.nation = nation;
		this.point = point;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getMember_password() {
		return member_password;
	}

	public void setMember_password(String member_password) {
		this.member_password = member_password;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	@Override
	public String toString() {
		return "Member [member_id=" + member_id + ", member_password=" + member_password + ", member_name="
				+ member_name + ", nation=" + nation + ", point=" + point + "]";
	}
	
	
    	
}
