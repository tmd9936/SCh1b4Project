package com.h1b4.www.contents.controller;

import java.awt.Dialog.ModalExclusionType;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.socket.WebSocketSession;

import com.h1b4.www.contents.service.ContentService;
import com.h1b4.www.member.controller.MemberController;
import com.h1b4.www.teacher.service.TeacherService;
import com.h1b4.www.transcript.service.TranscriptService;
import com.h1b4.www.vo.Category;
import com.h1b4.www.vo.Contents;
import com.h1b4.www.vo.Teacher;
import com.h1b4.www.vo.Transcript;
import com.h1b4.www.vo.Bookmark;

@Controller
@RequestMapping(value="contents")
@SessionAttributes("contents")
public class ContentsController {
	
	@Autowired
	ContentService service;
	
	@Autowired
	TranscriptService tsService;
	
	@Autowired
	TeacherService teacherService;
	
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
		model.addAttribute("list",result3);
		
		logger.info("카테고리별 리스트 종료");
		return "/contents/contentsList";
	}
	

	//교육용 페이지 이동
	@RequestMapping(value="studySpace", method=RequestMethod.GET)
	public String StudySpace(String contents_num,Model model) {
		logger.info("교육화면 이동 시작");
		
		/*선생님 리스트*/
		ArrayList<Teacher> teacherList = teacherService.selectAllTeacherList();
		model.addAttribute("teacherList", teacherList);
		
		Contents contents = service.searchByNumber(contents_num);
		ArrayList<Transcript> tsList = tsService.getTsList(contents.getContents_num());
		//이건 문제용으로 10개만 가져오게 따로 만든 커스텀 리스트
		ArrayList<Transcript> tslist = tsService.selectList(Integer.parseInt(contents_num));
		String filename = "";
		String ytName = "";
		
		
		model.addAttribute("contents",contents);
		model.addAttribute("tsList",tsList);
		//이건 문제용으로 10개만 가져오게 따로 만든 커스텀 리스트
		model.addAttribute("tslist", tslist);
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

	//검색한 키워드를 transcript로 가진 contents
	@RequestMapping(value="searchtText" ,method=RequestMethod.GET)
	public String searchByText(Model model,String searchtext){
		logger.info("검색한 텍스트 읽기");
		
		ArrayList<Contents> list = service.searchByText(searchtext);
		System.out.println("searchtext"+searchtext);
		model.addAttribute("list",list);
		
		return "/contents/contentsList";
	}
	
	//즐겨찾기 목록
	@RequestMapping(value="BookMark" ,method=RequestMethod.GET)
	public String Bookmark(Model model,HttpSession session){
		logger.info(" 즐겨찾기 읽기");
		
		String loginId = (String)session.getAttribute("loginId");
		
		ArrayList<Contents> bookmarklist = service.bookmarklist(loginId);
		
		System.out.println("loginId"+loginId);
		model.addAttribute("bookmarklist",bookmarklist);
		
		
		return "/contents/BookMark";
		
	}
	
	//영상추가 페이지 이동
	@RequestMapping(value="ytDownPage" , method=RequestMethod.GET)
	public String addVideo(){
		logger.info("영상추가 페이지 이동");
		
		
		return "/contents/ytDown";
	}
	
	
	//북마크 등록
	@ResponseBody
	@RequestMapping(value="bookmarkInsert", method = RequestMethod.POST)
	public void bookmarkInsert(int contents_num, HttpSession session){
		
		logger.info("북마크 등록 시작");
		
		Bookmark bookmark = new Bookmark();
		String loginId = (String)session.getAttribute("loginId");
		bookmark.setMember_id(loginId);
		bookmark.setContents_num(contents_num);
		
		service.bookmarkInsert(bookmark);
		
		
		logger.info("북마크 등록 종료");
	}
	
	//해당 컨테츠 북마크 여부 확인
	@ResponseBody
	@RequestMapping(value="selectBookmarkOrNot", method = RequestMethod.GET)
	public Boolean selectBookmarkOrNot(int contents_num, HttpSession session){
		logger.info("해당 컨테츠 북마크 여부 확인 시작");
		
		String member_id = (String)session.getAttribute("loginId");
		
		if(member_id == ""){
			
			return false;
		}
		
		Bookmark bookmark = new Bookmark();
		bookmark.setMember_id(member_id);
		bookmark.setContents_num(contents_num);
		
		Bookmark result = service.selectBookmarkOrNot(bookmark);
		
		if(result != null){
			return false;
		}
		logger.info("해당 컨테츠 북마크 여부 확인 종료");
		
		return true;
		
	}
	
	//북마크 삭제
	@ResponseBody
	@RequestMapping(value="bookmarkDelete", method = RequestMethod.POST)
	public void bookmarkDelete(int contents_num, HttpSession session){
		
		logger.info("북마크 삭제 시작");
		String loginId = (String)session.getAttribute("loginId");
		Bookmark bookmark = new Bookmark();
		
		bookmark.setMember_id(loginId);
		bookmark.setContents_num(contents_num);
		
		service.bookmarkDelete(bookmark);
		
		
		logger.info("북마크 삭제 종료");
	}
	
	//Publish 목록
	@RequestMapping(value="publishList", method = RequestMethod.GET)
	public String goPublishList(HttpSession session, Model model){
		ArrayList<Contents> list = null;
		String loginId = (String)session.getAttribute("loginId");
		
		list = service.selectPublishList(loginId);
		
		if(list != null){
			model.addAttribute("pubList", list);
		}
		
		return "/publish/publishList";
		
	}
	
	

}
