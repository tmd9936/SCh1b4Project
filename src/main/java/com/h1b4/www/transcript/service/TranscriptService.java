package com.h1b4.www.transcript.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.h1b4.www.transcript.dao.TranscriptDAO;
import com.h1b4.www.transcript.dao.TranscriptMapper;
import com.h1b4.www.vo.Transcript;

@Service
public class TranscriptService {

	@Autowired
	TranscriptDAO tsdao;

	public ArrayList<Transcript> xmlIntodatabase(int contents_num, Elements datas) {

		ArrayList<Transcript> tsList = new ArrayList<>();
		if (datas.size() <= 1) {
			return null;
		}
		
		int i = 0;
		for (Element e : datas) {

			Transcript ts = new Transcript();
			ts.setTs_start(e.attr("start"));
			ts.setTs_dur(e.attr("dur"));
			ts.setTs_text(e.text());
			ts.setContents_num(contents_num);
			ts.setTs_num(i++);
			
			System.out.println(ts);
			tsList.add(ts);
			

		}
		//System.out.println(tsList);

		return tsList;

	}

	public Elements checkXml(String url) throws MalformedURLException, IOException {
		Document doc;
		doc = Jsoup.parse(new URL("https://video.google.com/timedtext?lang=ja&v=" + url).openStream(), "UTF-8",
				"https://video.google.com/timedtext?lang=ja&v=" + url);

		Elements datas = (Elements) doc.select("transcript text");

		if (datas.size() <= 1) {
			return null;
		}
		return datas;
	}

	public void insertTranscript(ArrayList<Transcript> tsList) {
		/*
		 * Passer passer = new Passer(); ArrayList<Transcript> tsList =
		 * passer.xmlIntodatabase();
		 */

		int i = 0;
		for (Transcript t : tsList) {

			Transcript ts = new Transcript();
			t.setTs_num(i++);
			
		}
		System.out.println(tsList);
		
		tsdao.insertTsList(tsList);
	} 
	
	public void insertTranscript(List<Transcript> tsList) {
		/*
		 * Passer passer = new Passer(); ArrayList<Transcript> tsList =
		 * passer.xmlIntodatabase();
		 */

		
		int i = 0;
		for (Transcript t : tsList) {

			Transcript ts = new Transcript();
			t.setTs_num(i++);
			
		}
		System.out.println(tsList);
		
		tsdao.insertTsList(tsList);
	} 
	

	
	public void insertTranscriptOne(Transcript transcript){
		int resultFlag = tsdao.insertTsOne(transcript);
		if(resultFlag == 1){
			System.out.println("삽입성공");
		}else {
			System.out.println("삽입실패");
		}
	}
	
	 public ArrayList<Transcript> selectTranscript(Transcript ts){
		 
		 
		 ArrayList<Transcript> tsList =  tsdao.selectT(ts);
		 
		 if(tsList == null){
			 System.out.println("자막 값 없음");
			 return null;
		 }else {
			System.out.println("자막 불러오기 성공");
		}
		 
		 
	 return tsList; 
	 }
	 
	 public String selectContentsUrl(int contents_num){
		 
		 String rawContentsUrl = tsdao.selectContentsUrl(contents_num);
		 
		 if(rawContentsUrl.equals("")){
			 return "fail";
		 }
		 
		String[] youtubeUrl  = rawContentsUrl.split("embed/");
		 
		 
		 return youtubeUrl[1];
	 }
	 
	 public int deleteTranscript (int contents_num){
		 int result = tsdao.deleteTranscript(contents_num);
		 
		 if(result != 1){
			 System.out.println("자막 삭제 실패 :: TS_Service");
			 return result;
		 }
		 
		 System.out.println("자막 삭제 성공 :: TS_Service");
		 return result;
		 
		
	 }
	
}
