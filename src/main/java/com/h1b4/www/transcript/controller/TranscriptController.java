package com.h1b4.www.transcript.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.OutputStreamFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.h1b4.www.transcript.dao.TranscriptMapper;
import com.h1b4.www.transcript.service.TranscriptService;
import com.h1b4.www.vo.Transcript;


@Controller
@RequestMapping(value="transcript")
public class TranscriptController {

	
	Document doc = null;
	
	/*@Autowired
	transcriptDAO tsdao;*/
	
	@Autowired
	TranscriptService transcriptService;
	
	
	@RequestMapping(value="tstest", method = RequestMethod.GET)
	public String transcript(String youtube) {
		
		//TODO: 컨텐츠 넣기 컨텐츠의 번호 얻어오기
		
		//passer.insertTranscript(passer.xmlIntodatabase("RTDaq9X3Sws"));
		
		//YoutubeDown.download(youtube, true);
		
	return "transcript/tstest";
	}
	
	
	
	/**
	 *  function : 자막넣기
	 * 	Parameter : contents_num, ts_start, ts_dur ? end 값은 어디감?
	 * */	
	@RequestMapping(value="tsinsert", method = RequestMethod.POST)
	public String insertT(ArrayList<Transcript> tsList){
		
		//TODO: 자막 Insert 
		
		transcriptService.insertTranscript(tsList);
		
		
		return "redirect:/";
	}
	
	

	/**
	 *  function : 자막 불러오기
	 * 	Parametr : contents_num  
	 *  Return :ts 객체
	 * */	
	@RequestMapping(value="tsselect", method = RequestMethod.GET)
	public String selectT(Transcript ts, Model model){
		
		//TODO: 자막 Select
		//ArrayList<Transcript> traList = dao.selectTranscript();
		
		return "";
	}
	
	
//	작업시간이 30초이상 걸려서 이 방법은 봉인
//	@RequestMapping(value="javaTest",method=RequestMethod.POST)
//	public String notAllList(Model model) {
//		ArrayList<Transcript> tsList = new ArrayList<>(); 
//		ArrayList<Transcript>  temp = new ArrayList<>();
//		//컨텐츠 넘버를 보낸다
//		temp = transcriptService.selectList(221);
//		
//		
//		for (Transcript transcript : temp) {
//			String body = "{\"sentence\":\""+transcript.getTs_text()+"\",\"info_filter\":\"form|pos|read\",\"pos_filter\":\"名詞|連用詞\"}";
//			try {
//				URI uri = new URI("https://api.apigw.smt.docomo.ne.jp/gooLanguageAnalysis/v1/morph?APIKEY=483567313073493142416249757669777545574a5575626e2f755145677a5a4c2f63394d69364757646532");
//				URL url = uri.toURL();
//				HttpsURLConnection huc = (HttpsURLConnection)url.openConnection();
//				huc.setRequestMethod("POST");
//				huc.setDoInput(true);
//				huc.setDoOutput(true);
//				huc.setRequestProperty("Content-Type", "application/json");
//				OutputStream os = huc.getOutputStream();
//				os.write(body.getBytes("UTF-8"));
//				os.flush();
//				os.close();
//				
//				BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream(),"utf-8"));
//				String line="";
//				String page="";
//				while((line= br.readLine())!=null){
//					page += line;
//				}
//				
//					String meishi = "\"名詞\"";
//					int io_meishi = page.indexOf(meishi);	
//					String meishiWord;
//					meishiWord = page.substring(io_meishi+meishi.length()+2,(page.substring(io_meishi).indexOf("]")+io_meishi)-1);
//					tsList.add(transcript);
//			}catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		model.addAttribute("tsList", tsList);
//			return "transcript/qPage";
//	}
	
	//전체 리스트 불러오기
	@RequestMapping(value="listTest", method=RequestMethod.GET)
	public String selectList(Model model) {
		ArrayList<Transcript> tsList = new ArrayList<>(); 
		
		//컨텐츠 넘버를 보낸다
		tsList = transcriptService.selectList(221);
		model.addAttribute("contents_num", 221);
		model.addAttribute("tslist", tsList);
		
		return "transcript/test";
	}
	
//	체크한 문장 번호를 가지고 문제 푸는 페이지로 출발
	@RequestMapping(value="qPage", method=RequestMethod.POST)
	public String qPage(String[] ts_num,Model model, String contents_num) {
		ArrayList<Transcript> tsList = new ArrayList<>();
		for (String num : ts_num) {
			Transcript transcript = transcriptService.tsnum(Integer.parseInt(contents_num),Integer.parseInt(num));
			tsList.add(transcript);
		}
		model.addAttribute("tsList", tsList);
		
		
		
		return "transcript/qPage";
	}
}
