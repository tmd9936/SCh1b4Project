package com.h1b4.www.contents.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;

import com.h1b4.www.vo.ContentsReply;

public interface ContentsReplyMapper {

	public void insertContentsReply(ContentsReply contentsReply);
	
	public ArrayList<ContentsReply> selectAllContentsReply(HashMap<String, Object> map , RowBounds rb);
	
	public int getTotal(HashMap<String, Object> map);
	
	public void deleteContentsReply(ContentsReply contentsreply);
	
	public void updateContentsReply(ContentsReply contentsreply);
	
	public ContentsReply selectOneContentsReply(int reply_num);
	
}
