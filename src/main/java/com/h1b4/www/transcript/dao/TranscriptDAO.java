package com.h1b4.www.transcript.dao;

import java.util.ArrayList;

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
}