package com.h1b4.www.member.service;

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
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	public String idCheck(String member_id) {
		Member member = new Member();

		member = dao.searchMemberOne(member_id);
		String result = "no";
		if (member == null) {
			result = "yes";
		}
		
		return result;
	}
	
	public int joinComplete(Member member){
		
		int result = dao.joinMember(member);
		
		if(result != 1){
			logger.info("회원등록 실패");
		}
		
		return result;
	}

}
