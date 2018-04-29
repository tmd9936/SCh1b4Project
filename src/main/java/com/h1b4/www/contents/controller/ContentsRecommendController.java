package com.h1b4.www.contents.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.h1b4.www.contents.service.ContentsRecommendService;
import com.h1b4.www.vo.Emotion;

@RestController
@RequestMapping(value="contents")
public class ContentsRecommendController {
	
	@Autowired
	ContentsRecommendService service;
	
	private static final Logger logger = LoggerFactory.getLogger(ContentsRecommendController.class);
	
	@RequestMapping(value="insertRecommend", method = RequestMethod.POST)
	public void insertRecommend(int contents_num, HttpSession session)
	{
		logger.info("추천 입력 시작");
		String member_id = (String)session.getAttribute("member_id");
		Emotion emotion = new Emotion();
		emotion.setMember_id(member_id);
		emotion.setContents_num(contents_num);
		emotion.setState("likes");
		service.insertRecommendContents(emotion);
		
		logger.info("추천 입력 종료");
	}
	
	
	
	@RequestMapping(value="selectRecommendCount", method = RequestMethod.GET)
	public int selectRecommendCount(int contents_num)
	{	logger.info("추천수 불러오기");
		
		int result = service.selectCountRecommend(contents_num);
		
		return result;
	}
	
	@RequestMapping(value="selectRecommendOrNot", method = RequestMethod.GET)
	public Boolean selectRecommendOrNot(int contents_num, HttpSession session){
		logger.info("추천 여부 확인");
		
		String member_id = (String)session.getAttribute("loginId");
		
		if(member_id == ""){
			return false;
		}
		
		Emotion emotion = new Emotion();
		emotion.setMember_id(member_id);
		emotion.setContents_num(contents_num);
		
		Emotion result = service.selectRecommendOrNot(emotion);
	
		if(result != null){
			return false;
		}
		
		
		return true;
		
	}
	@RequestMapping(value="deleteRecommend", method = RequestMethod.POST)
	public void bookmarkDelete(int contents_num, HttpSession session){
		logger.info("추천 삭제 시작");
		
		Emotion emotion = new Emotion();
		String member_id = (String)session.getAttribute("loginId");
		emotion.setMember_id(member_id);
		emotion.setContents_num(contents_num);
		
		service.deleteRecommend(emotion);
		
		
		logger.info("추천 삭제 종료");
	}
	
}
