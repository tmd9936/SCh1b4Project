package com.h1b4.www.contents.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.h1b4.www.vo.ContentsReply;

@Repository
public class ContentsReplyDAO {

	private static final Logger logger = LoggerFactory.getLogger(ContentsReplyDAO.class);
	
	@Autowired
	SqlSession sqlSession;
	
	
	public void insertContentsReply(ContentsReply contentsReply){
		logger.info("댓글 입력 시작");
		
		ContentsReplyMapper mapper = sqlSession.getMapper(ContentsReplyMapper.class);
		
		try{
			
			mapper.insertContentsReply(contentsReply);
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
		
		
		logger.info("댓글 입력 종료");
	}
	
	//ArrayList<ContentsReply>
	
	public ArrayList<ContentsReply> selectAllContentsReply(HashMap<String, Object> map, int StartRecord, int CountPerPage){
		logger.info("댓글 목록 출력 시작");
		ContentsReplyMapper mapper = sqlSession.getMapper(ContentsReplyMapper.class);
		
		
		ArrayList<ContentsReply> contentsreplyList = null;
		//HashMap<String, Object> HashMapList;
		
		RowBounds rb = new RowBounds(StartRecord, CountPerPage);
		
		try{
			//, rb
			contentsreplyList = mapper.selectAllContentsReply(map, rb);
			System.out.println(contentsreplyList);
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		

		
		logger.info("댓글 목록 출력 종료");
		return contentsreplyList;
	}
	
	
	public int getTotal(HashMap<String, Object> map){
		logger.info("댓글 목록 갯수 세기 시작");
		int result = 0;
		ContentsReplyMapper mapper = sqlSession.getMapper(ContentsReplyMapper.class);
			
		try{
			
			result = mapper.getTotal(map);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		logger.info("댓글 목록 갯수 세기 종료");
		return result;
	}
	
	public void deleteContentsReply(ContentsReply contentsreply){
		logger.info("댓글 삭제 시작");
		
		ContentsReplyMapper mapper = sqlSession.getMapper(ContentsReplyMapper.class);
		
	try{
		
		
		mapper.deleteContentsReply(contentsreply);
		
	}catch(Exception e){
		
		e.printStackTrace();
	}
		
		
		
		logger.info("댓글 삭제 종료");
	}
	
	public void updateContentsReply(ContentsReply contentsreply){
		
		
		logger.info("댓글 수정 시작");
		ContentsReplyMapper mapper = sqlSession.getMapper(ContentsReplyMapper.class);
		try{
			
		mapper.updateContentsReply(contentsreply);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		logger.info("댓글 수정 종료");
		
		
		
		
		
	}
	
	public ContentsReply selectOneContentsReply(int reply_num){
	logger.info("특정 댓글 검색 시작");
	ContentsReplyMapper mapper = sqlSession.getMapper(ContentsReplyMapper.class);
	ContentsReply contentsreply = null;
	
	try{
		
		contentsreply = mapper.selectOneContentsReply(reply_num);
	
}catch(Exception e){
	e.printStackTrace();
}
	
	
	logger.info("특정 댓글 검색 시작");
	return contentsreply;
	
	}
}