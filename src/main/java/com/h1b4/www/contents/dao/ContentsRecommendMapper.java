package com.h1b4.www.contents.dao;

import com.h1b4.www.vo.Emotion;

public interface ContentsRecommendMapper {

	
	public void insertRecommend(Emotion emotion);
	
	public int selectCountRecommend(int contents_num);
	
	public Emotion selectRecommendOrNot(Emotion emotion);
	
	public void deleteRecommend(Emotion emotion);
}
