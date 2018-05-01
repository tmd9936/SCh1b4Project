package com.h1b4.www.vo;

public class Voca {
	private int voca_num;
	private int contents_num;
	private String member_id;
	private String kanji;
	private String part;
	private String mean;
	private String memo;
	private String vocadate;
	private String example;
	
	
	public Voca(int voca_num, int contents_num, String member_id, String kanji, String part, String mean, String memo,
			String vocadate, String example) {
		super();
		this.voca_num = voca_num;
		this.contents_num = contents_num;
		this.member_id = member_id;
		this.kanji = kanji;
		this.part = part;
		this.mean = mean;
		this.memo = memo;
		this.vocadate = vocadate;
		this.example = example;
	}
	public Voca() {
		super();
	}
	public int getVoca_num() {
		return voca_num;
	}
	public void setVoca_num(int voca_num) {
		this.voca_num = voca_num;
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
	public String getKanji() {
		return kanji;
	}
	public void setKanji(String kanji) {
		this.kanji = kanji;
	}
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	public String getMean() {
		return mean;
	}
	public void setMean(String mean) {
		this.mean = mean;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getVocadate() {
		return vocadate;
	}
	public void setVocadate(String vocadate) {
		this.vocadate = vocadate;
	}
	public String getExample() {
		return example;
	}
	public void setExample(String example) {
		this.example = example;
	}
	@Override
	public String toString() {
		return "Voca [voca_num=" + voca_num + ", contents_num=" + contents_num + ", member_id=" + member_id + ", kanji="
				+ kanji + ", part=" + part + ", mean=" + mean + ", memo=" + memo + ", vocadate=" + vocadate
				+ ", example=" + example + "]";
	}
	
	
}
