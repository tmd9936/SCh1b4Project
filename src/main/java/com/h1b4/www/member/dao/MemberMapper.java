package com.h1b4.www.member.dao;

import java.util.HashMap;

import com.h1b4.www.vo.Contents;
import com.h1b4.www.vo.Member;

public interface MemberMapper {
	
	//회원 정보 저장
	public int joinMember(Member member);
	
	//특정 회원 검색
	public Member searchMemberOne(String member_id); 
	
	//회원정보 수정
	public int updateMember(Member member);
	
	//회원 Pnt(포인트)업데이트
	public void updateMemberPnt(HashMap<String, Object> map);
}
