package com.h1b4.www.transcript.dao;

import java.util.ArrayList;

import com.h1b4.www.vo.Transcript;

public interface TranscriptMapper {
	
	public void insertTsList(ArrayList<Transcript> ts);
	
	public ArrayList<Transcript> selectT(Transcript ts);
}
