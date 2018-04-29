package com.h1b4.www.teacher.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.h1b4.www.member.dao.MemberMapper;
import com.h1b4.www.vo.Member;
import com.h1b4.www.vo.Teacher;

@Repository
public class TeacherDAO {
	
	@Autowired
	SqlSession sqlsession;
	
	//선생님 리스트
	public ArrayList<Teacher> selectAllTeacherList(){
		
		TeacherMapper mapper = sqlsession.getMapper(TeacherMapper.class);
		ArrayList<Teacher> teacherList = null;
		
		try{
			teacherList = mapper.selectAllTeacherList();
			
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		return teacherList;
	}
	
	
	
	
	//선생님 한명 선택
		public Teacher selectOneTeacher(String teacher_id) {

			TeacherMapper mapper = sqlsession.getMapper(TeacherMapper.class);
			Teacher teacher = null;
			
			try {
				teacher = mapper.selectOneTeacher(teacher_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return teacher;
			
		}
}
