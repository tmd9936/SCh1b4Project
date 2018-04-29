package com.h1b4.www.vo;

public class Teacher {
	private String teacher_id;
	private String teacher_password;
	private String teacher_name;
	private String teacher_onstate;
	private String teacher_ip;
	private int teacher_hit;
	
	public Teacher() {
		// TODO Auto-generated constructor stub
	}

	public Teacher(String teacher_id, String teacher_password, String teacher_name, String teacher_onstate,
			String teacher_ip, int teacher_hit) {
		super();
		this.teacher_id = teacher_id;
		this.teacher_password = teacher_password;
		this.teacher_name = teacher_name;
		this.teacher_onstate = teacher_onstate;
		this.teacher_ip = teacher_ip;
		this.teacher_hit = teacher_hit;
	}

	public String getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}

	public String getTeacher_password() {
		return teacher_password;
	}

	public void setTeacher_password(String teacher_password) {
		this.teacher_password = teacher_password;
	}

	public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}

	public String getTeacher_onstate() {
		return teacher_onstate;
	}

	public void setTeacher_onstate(String teacher_onstate) {
		this.teacher_onstate = teacher_onstate;
	}

	public String getTeacher_ip() {
		return teacher_ip;
	}

	public void setTeacher_ip(String teacher_ip) {
		this.teacher_ip = teacher_ip;
	}

	public int getTeacher_hit() {
		return teacher_hit;
	}

	public void setTeacher_hit(int teacher_hit) {
		this.teacher_hit = teacher_hit;
	}

	@Override
	public String toString() {
		return "Teacher [teacher_id=" + teacher_id + ", teacher_password=" + teacher_password + ", teacher_name="
				+ teacher_name + ", teacher_onstate=" + teacher_onstate + ", teacher_ip=" + teacher_ip
				+ ", teacher_hit=" + teacher_hit + "]";
	}

	
	
}
