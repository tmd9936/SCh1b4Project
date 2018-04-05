package com.h1b4.www.vo;

public class Contents {
	private int contents_num;
	private String contents_title;
	private String endtime;
	private String thumbnail;
	private String deletestate;
	private String deletedate;
	private String deleteid;
	private String category;
	private String contents_url;
	private int contents_hits;
	
	public Contents() {
		// TODO Auto-generated constructor stub
	}

	public Contents(int contents_num, String contents_title, String endtime, String thumbnail, String deletestate,
			String deletedate, String deleteid, String category, String contents_url, int contents_hits) {
		super();
		this.contents_num = contents_num;
		this.contents_title = contents_title;
		this.endtime = endtime;
		this.thumbnail = thumbnail;
		this.deletestate = deletestate;
		this.deletedate = deletedate;
		this.deleteid = deleteid;
		this.category = category;
		this.contents_url = contents_url;
		this.contents_hits = contents_hits;
	}

	public int getContents_num() {
		return contents_num;
	}

	public void setContents_num(int contents_num) {
		this.contents_num = contents_num;
	}

	public String getContents_title() {
		return contents_title;
	}

	public void setContents_title(String contents_title) {
		this.contents_title = contents_title;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getDeletestate() {
		return deletestate;
	}

	public void setDeletestate(String deletestate) {
		this.deletestate = deletestate;
	}

	public String getDeletedate() {
		return deletedate;
	}

	public void setDeletedate(String deletedate) {
		this.deletedate = deletedate;
	}

	public String getDeleteid() {
		return deleteid;
	}

	public void setDeleteid(String deleteid) {
		this.deleteid = deleteid;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getContents_url() {
		return contents_url;
	}

	public void setContents_url(String contents_url) {
		this.contents_url = contents_url;
	}

	public int getContents_hits() {
		return contents_hits;
	}

	public void setContents_hits(int contents_hits) {
		this.contents_hits = contents_hits;
	}

	@Override
	public String toString() {
		return "Contents [contents_num=" + contents_num + ", contents_title=" + contents_title + ", endtime=" + endtime
				+ ", thumbnail=" + thumbnail + ", deletestate=" + deletestate + ", deletedate=" + deletedate
				+ ", deleteid=" + deleteid + ", category=" + category + ", contents_url=" + contents_url
				+ ", contents_hits=" + contents_hits + "]";
	}
	
	
	
}
