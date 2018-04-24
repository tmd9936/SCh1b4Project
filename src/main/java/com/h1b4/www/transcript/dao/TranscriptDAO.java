package com.h1b4.www.transcript.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.h1b4.www.vo.Transcript;

@Repository
public class TranscriptDAO {

	
	@Autowired
	SqlSession sqlSession;
	
	public void insertTsList(ArrayList<Transcript> tsList){
		
		TranscriptMapper mapper = sqlSession.getMapper(TranscriptMapper.class);
		try{
			
			mapper.insertTsList(tsList);
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public ArrayList<Transcript> selectT(Transcript ts){
		TranscriptMapper mapper = sqlSession.getMapper(TranscriptMapper.class);
		ArrayList<Transcript> transcriptsList = null;
		try {
			transcriptsList = mapper.selectT(ts);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return transcriptsList;
		
	
	}
	
	//자막 전체 불러오기
		public ArrayList<Transcript> selectList(int contents_num){
			TranscriptMapper mapper = sqlSession.getMapper(TranscriptMapper.class);
			ArrayList<Transcript> transcriptList = null;
			try {
				transcriptList = mapper.selectList(contents_num);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return transcriptList;
		}
		//문제풀이용 자막 불러오기
		public Transcript ts_num(@Param("contents_num")int contents_num,@Param("ts_num")int ts_num){
			TranscriptMapper mapper = sqlSession.getMapper(TranscriptMapper.class);
			Transcript transcript = null;
			try {
				transcript = mapper.tsnum(contents_num,ts_num);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return transcript;
		}
}
