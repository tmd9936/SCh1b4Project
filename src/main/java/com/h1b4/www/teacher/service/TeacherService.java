package com.h1b4.www.teacher.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h1b4.www.teacher.dao.TeacherDAO;
import com.h1b4.www.vo.Teacher;

@Service
public class TeacherService {
	@Autowired
	TeacherDAO dao;
	
	
	private static final Logger logger = LoggerFactory.getLogger(TeacherService.class);

	public ArrayList<Teacher> selectAllTeacherList(){
		
		logger.info("선생님 리스트 출력 시작 ");
		
		ArrayList<Teacher> teacherList = dao.selectAllTeacherList();
		
		return teacherList;
	}
	
	
	public Teacher selectOneTeacher(String teacher_id){
		
		logger.info("선생님 선택");
		
		Teacher teacher = dao.selectOneTeacher(teacher_id);
		
		return teacher;
	}
		
}
