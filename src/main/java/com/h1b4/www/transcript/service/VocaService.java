package com.h1b4.www.transcript.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h1b4.www.transcript.dao.VocaDAO;
import com.h1b4.www.vo.ContentsReply;
import com.h1b4.www.vo.Voca;

@Service
public class VocaService {

	@Autowired
	VocaDAO vacaDAO;
	private static final Logger logger = LoggerFactory.getLogger(VocaService.class);	
	
	

	//단어장 입력 
	public void insertVoca(Voca voca){
		
		logger.info("서비스 단어장 입력");
		
		vacaDAO.insertVoca(voca);
		
		logger.info("서비스 단어장 종료");
		
	}
	
	
	//단어장 목록 출력 
		public ArrayList<Voca> selectAllVoca(Voca voca)
		{
			logger.info("서비스 단어장 목록 가져오기 시작");
			
			ArrayList<Voca> vocaList = null;
			
			
			vocaList = vacaDAO.selectAllVoca(voca);
			logger.info("서비스 단어장 목록 가져오기 종료");
			
			return vocaList;
		}
		
		
		//단어장 삭제
		public void deleteVoca(Voca voca){
			logger.info("서비스 단어장 삭제 시작");
			
			
			vacaDAO.deleteVoca(voca);
			
			logger.info("서비스 단어장 삭제 종료");
		}
		
}
