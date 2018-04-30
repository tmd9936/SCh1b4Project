package com.h1b4.www.contents.service;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.h1b4.www.contents.dao.ContentsDAO;
import com.h1b4.www.vo.Bookmark;
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


	//키워드로 검색
	public ArrayList<Contents> searchByText(String searchtext){
		logger.info("텍스트로 검사하기 서비스");
		
		ArrayList<Contents> list = null;
		list = dao.searchByText(searchtext);
		
		logger.info("텍스트로 검사하기 서비스 끝");
		return list;
	}
	
	//북마크 입력
	public void bookmarkInsert(Bookmark bookmark){
		logger.info("북마크 입력 서비스 시작");
		
		dao.bookmarkInsert(bookmark);
		
		
		logger.info("북마크 입력 서비스 종료");
	}
	
	//북마크 검색
	public ArrayList<Contents> bookmarklist(String loginId){
		logger.info("북마크 서비스");
		
		ArrayList<Contents> list = null;
		list = dao.bookmarklist(loginId);
		
		
		logger.info("북마크 서비스 끝");
		return list;
	}
	
	//북마크 삭제
	public void bookmarkDelete(Bookmark bookmark){
		logger.info("북마크 삭제 서비스 시작");
		
		dao.bookmarkDelete(bookmark);
		
		logger.info("북마크 삭제 서비스 종료");
		
	}
	
	//해당 컨테츠 북마크 여부 확인
	public Bookmark selectBookmarkOrNot(Bookmark bookmark){
		logger.info("DAO 해당 컨텐츠 북마크 여부 확인 시작");
		
		Bookmark boomark2 = null;
		boomark2 = dao.selectBookmarkOrNot(bookmark);
		
		
		logger.info("DAO 해당 컨텐츠 북마크 여부 확인 종료");
		return boomark2;
	}
	
	//멤버별 아이디로 Publish 리스트 가져오기
	public ArrayList<Contents> selectPublishList(String member_id){
		logger.info("Select Publish List");
		ArrayList<Contents> list = dao.selectPublishList(member_id);
		
		return list;
	}
	
	
}	

