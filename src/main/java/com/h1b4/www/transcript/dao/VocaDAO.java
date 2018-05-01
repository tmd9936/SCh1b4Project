package com.h1b4.www.transcript.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.h1b4.www.contents.dao.ContentsReplyMapper;
import com.h1b4.www.vo.Voca;


@Repository
public class VocaDAO {

	
	private static final Logger logger = LoggerFactory.getLogger(VocaDAO.class);

	@Autowired
	SqlSession sqlSession;
	
	
	public void insertVoca(Voca voca){
		logger.info("단어장 입력 시작");
		System.out.println(voca);
		VocaMapper mapper = sqlSession.getMapper(VocaMapper.class);
		
		try{
			
			mapper.insertVoca(voca);
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
	}
		
	public ArrayList<Voca> selectAllVoca(Voca voca){
		logger.info("단어장 전체 목록 출력 시작");
		
		VocaMapper mapper = sqlSession.getMapper(VocaMapper.class);
		
		
		ArrayList<Voca> vocaList = null;
		
		
		try{
			vocaList = mapper.selectAllVoca(voca);
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		
		return vocaList;
	}
	
	
	public void deleteVoca(Voca voca){
		logger.info("단어장 삭제 시작");
		
		VocaMapper mapper = sqlSession.getMapper(VocaMapper.class);
		
	try{
		mapper.deleteVoca(voca);
		
	}catch(Exception e){
		
		e.printStackTrace();
	}
		logger.info("단어장 삭제 종료");
		
	}
		

}