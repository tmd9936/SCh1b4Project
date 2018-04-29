package com.h1b4.www.teacher.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.h1b4.www.teacher.service.TeacherService;
import com.h1b4.www.vo.Teacher;

@Controller
@RequestMapping(value="contents")
public class TeacherController {
	private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);
	@Autowired
	TeacherService teacherService;
	
	
	@ResponseBody
	@RequestMapping(value="selectAllTeacherList", method = RequestMethod.GET)
	public ArrayList<Teacher> selectAllTeacherList(){
		
		logger.info("선생님 리스트 출력 시작");
		
		ArrayList<Teacher> teacherList = teacherService.selectAllTeacherList();
		
		return teacherList;
	}
	
}
