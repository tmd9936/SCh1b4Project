package com.h1b4.www.contents.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h1b4.www.contents.dao.ContentsDAO;
import com.h1b4.www.vo.Category;
import com.h1b4.www.vo.Contents;

@Service
public class ContentService {
	
	@Autowired
	ContentsDAO dao;
	
	private static final Logger logger = LoggerFactory.getLogger(ContentService.class);
	
	//커테고리별 리스트
	public ArrayList<Contents> searchByCategory(String category_kr){
		logger.info("서비스 컨텐츠 전체 읽기");
		
		ArrayList<Contents> list = null;
		list = dao.searchByCategory(category_kr);
		
		
		logger.info("서비스 컨텐츠 전체 보내주기");
		
		return list;
	}
	
	//리스트 최신 읽기
	public ArrayList<Contents> NewContents(){
		logger.info("서비스 컨텐츠 전체 읽기");
		
		ArrayList<Contents> list = null;
		list = dao.NewContents();
		
		
		logger.info("서비스 컨텐츠 전체 보내주기");
		
		return list;
	}
	
	//랜덤 리스트  읽기
		public ArrayList<Contents> Rnd(){
			logger.info("서비스 컨텐츠 전체 읽기");
			
			ArrayList<Contents> list = null;
			list = dao.RndContents();
			
			
			logger.info("서비스 컨텐츠 전체 보내주기");
			
			return list;
		}
	
	//카테고리 전체
	public ArrayList<Category>  CategoryList(){
		logger.info("카테고리 가져오기 시작");
		
		ArrayList<Category> list = null;
		list = dao.getCategoryList();
		
		logger.info("카테고리 가져오기 끝");
		return list;
	}
	
	public Contents searchByNumber(String contents_num) {
		logger.info("컨텐츠 가져오기 시작");
		
		Contents con = dao.searchByNumber(contents_num);
		
		logger.info("컨텐츠 가져오기 종료");
		
		return con;
	}
}