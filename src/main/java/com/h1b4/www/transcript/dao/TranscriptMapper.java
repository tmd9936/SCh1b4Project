package com.h1b4.www.transcript.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.h1b4.www.vo.Transcript;

public interface TranscriptMapper {
	
	public void insertTsList(ArrayList<Transcript> ts);
	
	public void insertTsList(List<Transcript> ts);
	
	public void insertEditList(List<Transcript> ts);
	
	public ArrayList<Transcript> selectT(int contents_num);

	public ArrayList<Transcript> selectE(int contents_num);
	
	public int insertTsOne(Transcript transcript);
	
	public String selectContentsUrl(int contents_num);
	
	public int deleteTranscript (int contents_num);
	//전체 리스트 가져옵니다
	public ArrayList<Transcript> takeAllList(int contents_num);
	//자막 전체 불러오기(중 10개만)
	public ArrayList<Transcript> selectList(int contents_num);
	//문제풀기 용 자막
	public Transcript tsnum(@Param("contents_num")int contents_num,@Param("ts_num")int ts_num);

}
