package com.h1b4.www.member.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.h1b4.www.member.dao.MemberDAO;
import com.h1b4.www.member.service.MemberService;
import com.h1b4.www.vo.Member;


@Controller
@RequestMapping(value = "member")
@SessionAttributes("member")
public class MemberController {
	
	@Autowired
	MemberService service;
	
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@RequestMapping(value="joinForm" , method = RequestMethod.GET)
	public String joinForm(Model model){
		logger.info("Controller 회원가입 폼 이동 시작");
		Member member = new Member();
		model.addAttribute("member", member);
		
		logger.info("Controller 회원가입 폼 이동 종료");
		return "member/joinForm";
	}
	
	@ResponseBody
	@RequestMapping(value="idCheck",  method = RequestMethod.POST)
	 public String idCheck(String member_id){
		logger.info("아이디 중복확인 시작");
		String result = service.idCheck(member_id);
		
		
		logger.info("아이디 중복확인 종료");
        return result;
	}
	
	@PostMapping(value="join")
	public String join(Model model,Member member){
		logger.info("회원 등록 시작");
		
		int result = service.joinComplete(member); 
		
		logger.info("회원 등록 종료");
		return "home";
	}
	
}
