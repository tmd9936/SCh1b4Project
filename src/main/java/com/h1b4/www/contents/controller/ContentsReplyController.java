package com.h1b4.www.contents.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.h1b4.www.contents.service.ContentReplyService;
import com.h1b4.www.utils.PageNavigator;
import com.h1b4.www.vo.ContentsReply;

@Controller
@RequestMapping(value="contents")
public class ContentsReplyController {

	private static final Logger logger = LoggerFactory.getLogger(ContentsReplyController.class);
	final int countPerPage = 5; //페이지당 글목록 수 
	final int pagePerGroup = 5; //그룹당 페이지 수 
	@Autowired
	ContentReplyService contentReplyService;
	
	/*@Autowired
	ContentsReplyDAO contentsreplydao;*/
	
	

	@ResponseBody
	@RequestMapping(value="contentsReplyInsert", method = RequestMethod.POST)
	public void contentsReplyInsert(@RequestBody ContentsReply contentsreply, HttpSession session){
		logger.info("댓글 입력 시작");	
		String loginId = (String)session.getAttribute("loginId");
		contentsreply.setMember_id(loginId);
		
		contentReplyService.insertContentsReply(contentsreply);
		System.out.println(contentsreply);
		logger.info("댓글 입력 종료");
	}
	
	//ArrayList<ContentsReply>
	@ResponseBody
	@RequestMapping(value="selectAllContentsReply", method = RequestMethod.POST)
	public HashMap<String, Object> selectAllContentsReply(
			//@RequestBody ContentsReply contentsreply
			int contents_num
			,int page
			//,@RequestParam(value="searchText", defaultValue = "")String searchText
			//,@RequestParam(value="page", defaultValue="1") int page
			//,@RequestParam(value="search", defaultValue = "")String search
			,HashMap<String, Object> map
			,HttpSession session
			){
		logger.info("댓글 검색 시작");
		
		
		System.out.println("페이지  값 : "+page);	
	
		//map.put("search", search);
		//map.put("searchText",searchText);
		map.put("contents_num", contents_num);
		
		//전체 글 갯수 조회
		int total = contentReplyService.getTotal(map);
		
		//페이징 처리를 위한 네비게이션
		PageNavigator navi = new PageNavigator(countPerPage, pagePerGroup, page, total);
		
		//리턴 
		HashMap<String, Object> hashMap = new HashMap<>();
		
		//목록을 불러오는 함수
		ArrayList<ContentsReply> contentsreply2 = null; 
		
		contentsreply2 = contentReplyService.selectAllContentsReply(map, navi.getStartRecord(),navi.getCountPerPage());
		
		System.out.println( "컨트롤러 어레이리스트 값 : "+contentsreply2);
		hashMap.put("contentsReply", contentsreply2);
		hashMap.put("navi", navi);
		
		//System.out.println("해쉬맵"+hashMap);
		//System.out.println("어레이리스트"+  hashMap.get("contentsReply"));
		//System.out.println("네비 객체 " + hashMap.get("navi"));		
		
		logger.info("댓글 검색 종료");
		
		//contentsreply2
		return hashMap;
	
	}
		
	
	
	
	@ResponseBody
	@RequestMapping(value="contentsReplyDelete", method = RequestMethod.POST)
	public void contentsReplyDelete(@RequestBody ContentsReply contentsreply, HttpSession session){
		logger.info("댓글 삭제 시작");
		String loginId = (String)session.getAttribute("loginId");
		contentsreply.setMember_id(loginId);
		System.out.println(contentsreply);
		contentReplyService.deleteContentsReply(contentsreply);
		
		logger.info("댓글 삭제 종료");
	}
	
	
	@ResponseBody
	@RequestMapping(value="contentsReplyUpdate", method = RequestMethod.POST)
	public void updateReply(@RequestBody ContentsReply contentsreply, HttpSession session){
		logger.info("댓글 수정 시작");
		String loginId = (String)session.getAttribute("loginId");
		contentsreply.setMember_id(loginId);
		System.out.println(contentsreply);
		contentReplyService.updateContentsReply(contentsreply);
		logger.info("댓글 수정 종료");
	}
	
	
	
	
	

}