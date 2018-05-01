package com.h1b4.www.transcript.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.h1b4.www.transcript.service.VocaService;
import com.h1b4.www.vo.Voca;

@RestController
@RequestMapping(value="contents")
public class VocaController {
	
	@Autowired
	VocaService service;
	
	private static final Logger logger = LoggerFactory.getLogger(VocaController.class);

	
	@RequestMapping(value="insertVoca", method = RequestMethod.POST)
	public void insertVoca(@RequestBody Voca voca, HttpSession session){
		logger.info("단어장 입력 시작");
		String member_id = (String)session.getAttribute("loginId");
		voca.setMember_id(member_id);
		
		service.insertVoca(voca);
		
	}
	
	@RequestMapping(value="selectAllVoca", method = RequestMethod.GET)
	public ArrayList<Voca> selectAllVoca(int contents_num, HttpSession session){
		logger.info("단어장 목록 출력 시작");
		System.out.println(contents_num);
		String member_id = (String)session.getAttribute("loginId");
		Voca voca = new Voca();
		voca.setMember_id(member_id);
		voca.setContents_num(contents_num);
		
		ArrayList<Voca> vocaList = service.selectAllVoca(voca); 
		
		return vocaList;
	}
	
	@RequestMapping(value="deleteVoca", method = RequestMethod.POST)
	public void deleteVoca(int voca_num, HttpSession session){
		
		logger.info("단어장 삭제 시작");
		String member_id = (String)session.getAttribute("loginId");
		Voca voca = new Voca();
		voca.setMember_id(member_id);
		voca.setVoca_num(voca_num);
		
		service.deleteVoca(voca);
		
	}
	
	
}
