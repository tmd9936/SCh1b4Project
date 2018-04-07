package com.h1b4.www.member.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.h1b4.www.vo.Member;

@Repository
public class MemberDAO {
	
	@Autowired
	SqlSession sqlsession;
	
	//회원가입
	public int joinMember(Member member){
		MemberMapper mapper = sqlsession.getMapper(MemberMapper.class);
		
		int result = 0;
			result = mapper.joinMember(member);
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	//회원정보 검색
	public Member searchMemberOne(String member_id) {

		MemberMapper mapper = sqlsession.getMapper(MemberMapper.class);
		Member memeber = null;
		
		try {
			memeber = mapper.searchMemberOne(member_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memeber;
		
	}
	
}
