package com.h1b4.www.member.dao;

import com.h1b4.www.vo.Member;

public interface MemberMapper {
	//회원 정보 저장
	public int joinMember(Member member);
	//특정 회원 검색
	public Member searchMemberOne(String member_id); 
	//
}
