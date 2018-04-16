package com.h1b4.www.contents.dao;

import java.util.ArrayList;

import com.h1b4.www.vo.Category;
import com.h1b4.www.vo.Contents;

public interface ContentsMapper {
	//<select id="searchByUrlContents" parameterType="java.lang.String" resultType="contents">
	public Contents searchByUrlContents(String contents_url);
	
	//<insert id="insertContents" parameterType="contents">
	public int insertContents(Contents contents);
	
	//신규 컨텐츠
	public ArrayList<Contents> NewContents();
	
	//램던 컨텐츠
	public ArrayList<Contents> RndContents();
	
	//전체 카테고리
	public ArrayList<Category> getCategoryList();
	
}
