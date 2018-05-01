package com.h1b4.www.transcript.dao;

import java.util.ArrayList;

import com.h1b4.www.vo.Voca;

public interface VocaMapper {
	
	public void insertVoca(Voca voca);
	
	public ArrayList<Voca> selectAllVoca(Voca voca);
	
	public void deleteVoca(Voca voca);
}
