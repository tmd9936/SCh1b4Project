package com.h1b4.www.contents.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h1b4.www.contents.dao.ContentsReplyDAO;
import com.h1b4.www.vo.ContentsReply;

@Service
public class ContentReplyService {

	@Autowired
	ContentsReplyDAO contentsReplydao;
	
	private static final Logger logger = LoggerFactory.getLogger(ContentReplyService.class);

	
	
	//댓글 입력 
	public void insertContentsReply(ContentsReply contentsReply){
		
		logger.info("서비스 댓글 입력");
		
		contentsReplydao.insertContentsReply(contentsReply);
		
		
		logger.info("서비스 댓글 종료");
		
	}
	
	//댓글 목록 출력 
	public ArrayList<ContentsReply> selectAllContentsReply(HashMap<String, Object> map, int getStartRecord, int getCountPerPage)
	{
		logger.info("서비스 댓글 목록 가져오기 시작");
		
		ArrayList<ContentsReply> ReplyList = null;
		
		
		ReplyList = contentsReplydao.selectAllContentsReply(map, getStartRecord, getCountPerPage);
		logger.info("서비스 댓글 목록 가져오기 종료");
		
		return ReplyList;
	}
	
	//댓글 전체 갯수 
	public int getTotal(HashMap<String, Object> map){
		
		logger.info("서비스 전체 댓글 수 시작");
		int total = contentsReplydao.getTotal(map);
		
		
		logger.info("서비스 전체 댓글 수 종료");
		return total;
	}
	
	//댓글 삭제
	public void deleteContentsReply(ContentsReply contentsreply){
		logger.info("서비스 댓글 삭제 시작");
		
		
		contentsReplydao.deleteContentsReply(contentsreply);
		
		logger.info("서비스 댓글 삭제 종료");
	}
	
	
	//댓글 수정 
	public void updateContentsReply(ContentsReply contentsreply){
		logger.info("서비스 댓글 수정 시작");
		contentsReplydao.updateContentsReply(contentsreply);
		
		logger.info("서비스 댓글 수정 종료");	
	}
	
	
	//선택된 영상의 모든 댓글 
	public ContentsReply selectOneContentsReply(int reply_num){
		logger.info("서비스 선택한 영상의 댓글 가져오기 시작");
		ContentsReply contentsReply = null;
		
		contentsReply = contentsReplydao.selectOneContentsReply(reply_num);
		logger.info("서비스 선택한 영상의 댓글 가져오기 종료");
		
		return contentsReply;
		
	}
	
}
