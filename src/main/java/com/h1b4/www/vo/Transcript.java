package com.h1b4.www.vo;

public class Transcript {
	
	private int contents_num;
	private int ts_num;
	private String ts_start;
	private String ts_dur;
	private String ts_text;
	
	public Transcript() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Transcript(int contents_num, int ts_num, String ts_start, String ts_dur, String ts_text) {
		super();
		this.contents_num = contents_num;
		this.ts_num = ts_num;
		this.ts_start = ts_start;
		this.ts_dur = ts_dur;
		this.ts_text = ts_text;
	}

	public int getContents_num() {
		return contents_num;
	}

	public void setContents_num(int contents_num) {
		this.contents_num = contents_num;
	}

	public int getTs_num() {
		return ts_num;
	}

	public void setTs_num(int ts_num) {
		this.ts_num = ts_num;
	}

	public String getTs_start() {
		return ts_start;
	}

	public void setTs_start(String ts_start) {
		this.ts_start = ts_start;
	}

	public String getTs_dur() {
		return ts_dur;
	}

	public void setTs_dur(String ts_dur) {
		this.ts_dur = ts_dur;
	}

	public String getTs_text() {
		return ts_text;
	}

	public void setTs_text(String ts_text) {
		this.ts_text = ts_text;
	}

	@Override
	public String toString() {
		return "Transcript [contents_num=" + contents_num + ", ts_num=" + ts_num + ", ts_start=" + ts_start
				+ ", ts_dur=" + ts_dur + ", ts_text=" + ts_text + "]";
	}

	
	
	
	

}
