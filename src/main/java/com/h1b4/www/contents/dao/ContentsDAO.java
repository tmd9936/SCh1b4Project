package com.h1b4.www.contents.dao;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
		
		int result =0;
		
		try {
			result = mapper.insertContents(contents);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		logger.info("contents 넣기 종료");
		return result;
	}
}
