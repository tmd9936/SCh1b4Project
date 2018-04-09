package com.h1b4.www.member.controller;


import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.instrumentation.trace.Status;
import com.h1b4.www.member.service.MemberService;
import com.h1b4.www.vo.Member;


@Controller
@RequestMapping(value = "member")
@SessionAttributes("member")
public class MemberController {
	
	@Autowired
	MemberService service;
	
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	
	//메인페이지 이동
	@RequestMapping(value="user_home_test" , method = RequestMethod.GET)
	public String mainpage(){
		
		return "member/user_home_test";
	}
	
	
	//회원가입 페이지 이동
	@RequestMapping(value="joinForm" , method = RequestMethod.GET)
	public String joinForm(Model model){
		logger.info("Controller 회원가입 폼 이동 시작");
		Member member = new Member();
		model.addAttribute("member", member);
		
		logger.info("Controller 회원가입 폼 이동 종료");
		return "member/joinForm";
	}
	
	//아이디 중복 체크
	@ResponseBody
	@RequestMapping(value="idCheck",  method = RequestMethod.POST)
	 public String idCheck(String member_id){
		logger.info("아이디 중복확인 시작");
		String result = service.idCheck(member_id);
		
		
		logger.info("아이디 중복확인 종료");
        return result;
	}
	
	//회원가입 완료
	@PostMapping(value="join")
	public String join(Model model,Member member){
		logger.info("회원 등록 시작");
		
		int result = service.joinComplete(member); 
		
		logger.info("회원 등록 종료");
		return "member/user_home_test";
	}
	
	
	//로그인 페이지 이동
	@RequestMapping(value="loginForm" , method = RequestMethod.GET)
	public String loginForm(Member member){
		logger.info("로그인폼 이동 시작");
		
		logger.info("로그인폼 이동 종료");
		
		return "member/loginForm";
	}
	
	//로그인 계정 확인
	@RequestMapping(value="login" , method = RequestMethod.POST)
	public String login(Member member, HttpSession session,Model model){
		//화면에 전달받은 ID로 DB검색
		logger.info("로그인 시작");
		System.out.println(member);
		member = service.membercheck(member);
		System.out.println(member);
		if(member == null){
			model.addAttribute("msg","아이디와 비밀번호를 확인하세요.");
			return "member/loginForm";
		}
		
		//세션처리
		session.setAttribute("loginId", member.getMember_id());
		session.setAttribute("loginName", member.getMember_name());
		session.setAttribute("point", member.getPoint());
		
		return"redirect:/";
	}
	//로그아웃
	@RequestMapping(value="logout", method = RequestMethod.GET)
	public String logout(HttpSession session){
		session.removeAttribute("loginId");
		session.removeAttribute("loginName");
		session.removeAttribute("point");
		logger.info("로그아웃");
		return"redirect:/";
	}
	
	//수정할 계정 체크
	@RequestMapping(value="updateForm", method = RequestMethod.GET)
	public String updateMember(Member member, Model model){
		logger.info("업데이트 시작");

		member = service.membercheck(member);
		System.out.println(member);
		model.addAttribute(member);
		return "member/updateForm";
	}
	
	//비밀번호 업데이트
	@RequestMapping(value="update", method = RequestMethod.POST)
	public String update(Member member,Model model){
		boolean updateFlag = service.update(member);
		
		if(!updateFlag){
			return "member/updateForm"; 
		}
		return "redirect:/";
	}
	
	
}
