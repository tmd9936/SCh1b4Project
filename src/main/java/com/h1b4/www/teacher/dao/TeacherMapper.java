package com.h1b4.www.teacher.dao;

import java.util.ArrayList;

import com.h1b4.www.vo.Teacher;

public interface TeacherMapper {
	
	public ArrayList<Teacher> selectAllTeacherList();
	
	public Teacher selectOneTeacher(String teacher_id);
}
