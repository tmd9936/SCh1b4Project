package com.h1b4.www.member.service;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.h1b4.www.member.controller.MemberController;
import com.h1b4.www.member.dao.MemberDAO;
import com.h1b4.www.vo.Member;

@Service
public class MemberService {
	
	@Autowired
	MemberDAO dao;
	
	private static final Logger logger = LoggerFactory.getLogger(MemberService.class);
	
	//아이디 중복 체크
	public String idCheck(String member_id) {
		Member member = new Member();

		member = dao.searchMemberOne(member_id);
		String result = "no";
		if (member == null) {
			result = "yes";
		}
		
		return result;
	}
	
	//회원가입 성공여부
	public int joinComplete(Member member){
		
		int result = dao.joinMember(member);
		
		if(result != 1){
			logger.info("회원등록 실패");
		}
		return result;
	}
	
	//로그인 할 계정을 DB에 있는지 검색
	public Member membercheck(Member member){
		logger.info("ID 검사 시작");
		Member result = dao.searchMemberOne(member.getMember_id());
		System.out.println(result);
		//입력한 아이디가 없으면 로그인창으로
		if(result == null){
			logger.info("로그인 실패1");
			return null;
		}
		logger.info("ID 검사 종료");
		//비밀번호 체크해서 틀리면 다시 로그인창
		logger.info("비밀번호 검사 시작");
		if (!(result.getMember_password().equals(member.getMember_password()))) {
			logger.info("로그인 실패2");
			return null;
		}
		logger.info("비밀번호 검사 종료");
		
		return result;
	}
	
	//비밀번호 수정할 계정 확인
	public Member updatecheck(HttpSession session){
		logger.info("계정  확인 시작");
		
		String loginId = (String) session.getAttribute("loginId");
		
		Member member = dao.searchMemberOne(loginId);
		
		return member;
	}
	
	//비밀번호 수정
	 public boolean update(Member member){
		 logger.info("비밀번호 수정 시작");
		 int result = dao.updateMember(member);
		 
		 if(result != 1){
			 logger.info("수정 실패");
			 return false;
		 }
		 return true;
	 }
	 
	 
	 //회원 포인트 수정
	 public void updatePnt(String member_id, String pntType){
		 logger.info("회원포인트 수정 시작");
		 
		 HashMap<String, Object> map = new HashMap<>();
		 map.put("pntType", pntType);
		 map.put("member_id", member_id);
		 dao.updateMemberExp(map);
		 
	 }
	
}
