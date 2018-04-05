package com.h1b4.www.contents.dao;

import com.h1b4.www.vo.Contents;

public interface ContentsMapper {
	//<select id="searchByUrlContents" parameterType="java.lang.String" resultType="contents">
	public Contents searchByUrlContents(String contents_url);
	
	//<insert id="insertContents" parameterType="contents">
	public int insertContents(Contents contents);
	
}
