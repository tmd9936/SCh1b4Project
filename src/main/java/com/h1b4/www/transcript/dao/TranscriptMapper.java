package com.h1b4.www.transcript.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.h1b4.www.vo.Transcript;

public interface TranscriptMapper {
	
	public void insertTsList(ArrayList<Transcript> ts);
	
	public ArrayList<Transcript> selectT(Transcript ts);
	
	//자막 전체 불러오기
	public ArrayList<Transcript> selectList(int contents_num);
	//문제풀기 용 자막
	public Transcript tsnum(@Param("contents_num")int contents_num,@Param("ts_num")int ts_num);
}
