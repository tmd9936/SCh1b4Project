package com.h1b4.www.contents.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h1b4.www.contents.dao.ContentsRecommendDAO;
import com.h1b4.www.vo.Emotion;

@Service
public class ContentsRecommendService {

	@Autowired
	ContentsRecommendDAO contentsRecommendDao;
	
	private static final Logger logger = LoggerFactory.getLogger(ContentsRecommendService.class);


	//추천 입력 
	public void insertRecommendContents(Emotion emotion){
		logger.info("추천 입력 시작");
		
		
		contentsRecommendDao.insertRecommend(emotion);
		
		
		logger.info("추천 입력 종료");
	}
	
	
	
	//추천 수 조회
	public int selectCountRecommend(int contents_num){
		logger.info("추천 수 조회 시작");
		int result = 0;
		
		result = contentsRecommendDao.selectCountRecommend(contents_num);
		
		logger.info("추천 수 조회 종료");
		return result;
	}
	
	//추천 비추천 조회
	public Emotion selectRecommendOrNot(Emotion emotion){
		logger.info("추천 비추천  조회 시작");
		Emotion emotion2 = null;
		
		emotion2 = contentsRecommendDao.selectRecommendOrNot(emotion);
		
		logger.info("추천 비추천 조회 종료");
		return emotion2;
	}
	
	//추천 삭제 
	public void deleteRecommend(Emotion emotion){
		logger.info("추천 삭제 시작");
		
		contentsRecommendDao.deleteRecommend(emotion);
		
		
		logger.info("추천 삭제 종료");
	}
	


}
