package com.h1b4.www.member.dao;

import java.util.HashMap;

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
	
	//회원정보 수정
	public int updateMember(Member member){
		
		MemberMapper mapper = sqlsession.getMapper(MemberMapper.class);
		
		int result = 0;
		
		try {
			result = mapper.updateMember(member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;		
	}
	
	//멤버 포인트
		public void updateMemberExp(HashMap<String, Object> map){
			MemberMapper mapper = sqlsession.getMapper(MemberMapper.class);
			
			try {
				mapper.updateMemberPnt(map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
}
