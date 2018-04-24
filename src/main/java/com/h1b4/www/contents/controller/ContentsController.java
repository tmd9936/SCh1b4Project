package com.h1b4.www.contents.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.h1b4.www.contents.service.ContentService;
import com.h1b4.www.member.controller.MemberController;
import com.h1b4.www.transcript.service.TranscriptService;
import com.h1b4.www.vo.Category;
import com.h1b4.www.vo.Contents;
import com.h1b4.www.vo.Transcript;

@Controller
@RequestMapping(value="contents")
@SessionAttributes("contents")
public class ContentsController {
	
	@Autowired
	ContentService service;
	
	@Autowired
	TranscriptService tsService;
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	
	//컨텐츠 전체 출력
	@RequestMapping(value="toUserHome",method=RequestMethod.GET)
	public String AllContents(Contents contents , Model model ){
		
		logger.info("최신 컨텐츠 읽기");
		
		ArrayList<Contents> result = service.NewContents();
		System.out.println("result:"+result);
		model.addAttribute("result", result);
		
		logger.info("랜덤 카테고리 읽기");

		ArrayList<Contents> rnd = service.Rnd();
		System.out.println("rnd:"+rnd);
		model.addAttribute("rnd", rnd);
		
		
		logger.info("카테고리 읽기");
		
		ArrayList<Category> result2 = service.CategoryList();
		System.out.println("categoryList:"+result2);
		model.addAttribute("categoryList", result2);
		
		logger.info("컨텐츠 읽기 완료");
		return "user_home";
	}
	
	//카테고리 데이터 가지고 페이지 이동
	@RequestMapping(value="categoryList",method=RequestMethod.GET)
	public String selectAllPlayer(Model model,String category){
		logger.info("카테고리별 리스트 읽기");
		
		ArrayList<Contents> result3 = service.searchByCategory(category);
		System.out.println("category"+result3);
		model.addAttribute("category",result3);
		
		logger.info("카테고리별 리스트 종료");
		return "/contents/contentsList";
	}
	
	//교육용 페이지 이동
	@RequestMapping(value="studySpace", method=RequestMethod.GET)
	public String StudySpace(String contents_num,Model model) {
		logger.info("교육화면 이동 시작");
		
		Contents contents = service.searchByNumber(contents_num);
		ArrayList<Transcript> tsList = tsService.getTsList(contents.getContents_num());
		
		String filename = "";
		String ytName = "";
		
		model.addAttribute("contents",contents);
		model.addAttribute("tsList",tsList);
		
		ytName = contents.getContents_url().replace("https://www.youtube.com/embed/", "");
		filename = "m"+ytName;
		
		String tStr[] = contents.getEndtime().split(":");
		int allTime = Integer.parseInt(tStr[0])*3600 + Integer.parseInt(tStr[1])*60 + Integer.parseInt(tStr[2]);
		
		model.addAttribute("allTime",allTime);
		model.addAttribute("ytName",ytName);
		model.addAttribute("filename",filename);
		
		logger.info("교육화면 이동 종료");
		return "/education/studySpace";
	}
}
