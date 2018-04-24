package com.h1b4.www.contents.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.h1b4.www.vo.Category;
import com.h1b4.www.vo.Contents;

@Repository
public class ContentsDAO {

	@Autowired
	SqlSession sqlSession;

	private static final Logger logger = LoggerFactory.getLogger(ContentsDAO.class);

	public Contents searchByUrlContents(String contents_url) {
		logger.info("url로 contents 서치 시작");

		ContentsMapper mapper = sqlSession.getMapper(ContentsMapper.class);

		Contents contents = null;

		try {
			contents = mapper.searchByUrlContents(contents_url);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		logger.info("url로 contents 서치 종료");
		return contents;
	}

	public int insertContents(Contents contents) {
		logger.info("contents 넣기 시작");

		ContentsMapper mapper = sqlSession.getMapper(ContentsMapper.class);

		int result = 0;

		try {
			result = mapper.insertContents(contents);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		logger.info("contents 넣기 종료");
		return result;
	}

	// 최신 contents 영상
	public ArrayList<Contents> NewContents() {
		logger.info("DAO전체 리스트  가져오기 시작");

		ContentsMapper mapper = sqlSession.getMapper(ContentsMapper.class);

		ArrayList<Contents> list = null;

		try {
			list = mapper.NewContents();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("DAO전체 리스트 가져오기 종료");
		return list;
	}

	// 랜덤 contents
	public ArrayList<Contents> RndContents() {
		logger.info("DAO랜덤 리스트  가져오기 시작");

		ContentsMapper mapper = sqlSession.getMapper(ContentsMapper.class);

		ArrayList<Contents> list = null;

		try {
			list = mapper.RndContents();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("DAO랜덤 리스트 가져오기 종료");
		return list;
	}

	// 모든 카테고리
	public ArrayList<Category> getCategoryList() {
		logger.info("DAO카테고리 가져오기 시작");
		ContentsMapper mapper = sqlSession.getMapper(ContentsMapper.class);

		ArrayList<Category> category = null;

		try {
			category = mapper.getCategoryList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("DAO카테고리 가져오기 종료");
		return category;
	}
	

	public Contents searchByNumber(String contents_num) {
		logger.info("contents_num으로 컨텐츠 가져오기 시작");
		ContentsMapper mapper = sqlSession.getMapper(ContentsMapper.class);
		
		Contents contents = null;
		
		try {
			contents = mapper.searchByNumber(contents_num);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return contents;

	//카테고리로 검색
	public ArrayList<Contents> searchByCategory(String category_kr){
		logger.info("DAO카테고리별 리스트  가져오기 시작");
		
		ContentsMapper mapper = sqlSession.getMapper(ContentsMapper.class);
		ArrayList<Contents> list = null;
		
		try {
			list = mapper.searchByCategory(category_kr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("DAO카테고리별 리스트  가져오기 종료");
		return list;
	}
	
	//검색한 키워드를 transcript로 가진 contents
	public ArrayList<Contents> searchByText(String searchtext){
		logger.info("DAO 키워드검색  시작");
		ContentsMapper mapper = sqlSession.getMapper(ContentsMapper.class);
		ArrayList<Contents> list = null;
		
		try {
			list = mapper.searchByText(searchtext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("DAO 키워드검색  종료");
		return list;
	}
	
	//즐겨찾기 목록
	public ArrayList<Contents> bookmarklist(String loginId){
		logger.info("DAO 북마크  시작");
		ContentsMapper mapper = sqlSession.getMapper(ContentsMapper.class);
		ArrayList<Contents> list = null;
		
		try {
			list = mapper.bookmarklist(loginId);
			System.out.println(loginId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("DAO 북마크  시작");
		return list;

	}
}
