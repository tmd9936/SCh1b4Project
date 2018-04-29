package com.h1b4.www.contents.dao;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.h1b4.www.vo.Emotion;

@Repository
public class ContentsRecommendDAO {
	
	
	private static final Logger logger = LoggerFactory.getLogger(ContentsRecommendDAO.class);
	
	@Autowired
	SqlSession sqlSession;
	
	
	public void insertRecommend(Emotion emotion){
		logger.info("추천 입력 시작");
		ContentsRecommendMapper mapper = sqlSession.getMapper(ContentsRecommendMapper.class);
		
		try{
			
		mapper.insertRecommend(emotion);	
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
		
		logger.info("추천 입력 종료");
	}
	
	public int selectCountRecommend(int contents_num){
		logger.info("추천 수 읽기 시작");
		int result = 0;
		ContentsRecommendMapper mapper = sqlSession.getMapper(ContentsRecommendMapper.class);
		
		try{
			
			result = mapper.selectCountRecommend(contents_num);
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		
		logger.info("추천 수 읽기 종료");
		
		return result;
	}
	
	public Emotion selectRecommendOrNot(Emotion emotion){
		logger.info("추천 여부 읽기 시작");
		ContentsRecommendMapper mapper = sqlSession.getMapper(ContentsRecommendMapper.class);
		Emotion emotion2 = null;
		
		
		try{
			emotion2 = mapper.selectRecommendOrNot(emotion);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		logger.info("추천 여부 읽기 종료");
		return emotion2;
	}
	
	public void deleteRecommend(Emotion emotion){
		
		logger.info("추천 삭제 시작");
		ContentsRecommendMapper mapper = sqlSession.getMapper(ContentsRecommendMapper.class);
		
		
		try{
			
			mapper.deleteRecommend(emotion);
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		
		logger.info("추천 삭제 종료");
	}
	
}
