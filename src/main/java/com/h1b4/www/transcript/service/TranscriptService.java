package com.h1b4.www.transcript.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.h1b4.www.transcript.dao.TranscriptDAO;
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

		tsdao.insertTsList(tsList);
	}

	/*
	 * public ArrayList<Transcript> selectTranscript(){
	 * 
	 * return list; }
	 */
	
	//전체리스트 가져오기
	//dao에서는 전체 리스트를 받고, service에서 몇 개만 랜덤으로 꺼낸다.	
	public ArrayList<Transcript> selectList(int contents_num) {
		//전체를 받아오고
		ArrayList<Transcript> tsList = new ArrayList<>();
		//몇 개만 랜덤으로 받고
		ArrayList<Transcript> tempList = new ArrayList<>();
		// 몇 (개)
		Transcript temp = new Transcript();
		//랜덤
		Random r = new Random();
		//전체를 받아오고
		tsList = tsdao.selectList(contents_num);
		int k=0;
		
//				5개만 꺼낼 것이야
			while(k<=4) {
//					랜덤으로 뿅
			int num = r.nextInt(tsList.size());
			temp = tsList.get(num);
			
//			서버와 통신. ajax에서 하는 걸 자바페이지에서 한다고 보셈
			try {
				URI uri = new URI("https://api.apigw.smt.docomo.ne.jp/gooLanguageAnalysis/v1/morph?APIKEY=6b574a385741326e7041464e6b4b636d57556144426b4b6a746d5470796b465461597a6263482f41664e41");
				URL url = uri.toURL();
				HttpsURLConnection huc = (HttpsURLConnection)url.openConnection();
				huc.setRequestMethod("POST");
				huc.setDoInput(true);
				huc.setDoOutput(true);
				huc.setRequestProperty("Content-Type", "application/json");
				OutputStream os = huc.getOutputStream();
				String body = "{\"sentence\":\""+temp.getTs_text()+"\",\"info_filter\":\"form|pos|read\",\"pos_filter\":\"名詞|連用詞|格助詞\"}";
				os.write(body.getBytes("UTF-8"));
				BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream(),"utf-8"));
				String line="";
				String page="";
				String pageTemp="";
//				결과를 스트링 문장으로 일단 받고
				while((line= br.readLine())!=null){
					page += line;
				}
				
//				원문은 놔두고 temp로 복사. 이걸 사용함
				pageTemp = page;
				
//				URL통로 닫고
				os.flush();
				os.close();
				
//				단어분석 결과가 들어있는 곳 전까지 필요없는 부분 다 자르고
				int wordlist = page.indexOf(":[[");
				pageTemp =  page.substring(wordlist+3);
				System.out.println("자른 문장:"+pageTemp);
				
//				3대장 : 명사와 연용사 격조사를 판정 해보자
				String meishi = "\",\"名詞\",\"";
				String renyoshi = "\",\"連用詞\",\"";
				String kakujoshi = "\",\"格助詞\",\"";
				
//				한 문장 안에 몇 개가 3대장에 포함되는지 살핀다 (랜덤으로 꺼내는 거라서 하나도 없는 경우도 있음)
				int count = StringUtils.countOccurrencesOf(pageTemp, "[")-1;
				System.out.println(count);
				
//				이제 자를 것이야
				while(count>0)	{
					
	//				명사
					if(pageTemp.contains(meishi)) {
//						하나씩 카운트를 줄여나간다
						count--;
						
//						시작 부분 찾아서 컷
						int io_meishi = pageTemp.indexOf("[");	
						System.out.println("io_meishi :"+io_meishi);
						
//						여기에 담아
						String meishiWord;
						meishiWord = pageTemp.substring(pageTemp.indexOf("\"")+1,pageTemp.indexOf(",")-1);
						System.out.println("meishiWord :" +meishiWord);
						
//						다음 3대장 단어가 시작되는 부분까지 문장도 잘라내 버린다
						if(count>=1) {
							int cut=pageTemp.indexOf("[",2);
							pageTemp = pageTemp.substring(cut);;
							System.out.println("pageTemp : "+pageTemp);
						}
					}
					
	//				격조사	
					if(pageTemp.contains(kakujoshi)) {
						count--;
						int io_kakujoshi = pageTemp.indexOf("[");	
						System.out.println("io_kakujoshi :"+io_kakujoshi);
						String kakujoshiWord;
						kakujoshiWord = pageTemp.substring(pageTemp.indexOf("\"")+1,pageTemp.indexOf(",")-1);
						System.out.println("kakujoshiiWord :" +kakujoshiWord);
						if(count>=1) {
							int cut=pageTemp.indexOf("[",2);
							pageTemp = pageTemp.substring(cut);;
							System.out.println("pageTemp : "+pageTemp);
						}
					}
					
	//				연용사
					if(pageTemp.contains(renyoshi)) {
						count--;
						int io_renyoshi = pageTemp.indexOf("[");	
						System.out.println("io_renyoshi :"+io_renyoshi);
						String renyoshiWord;
						renyoshiWord = pageTemp.substring(pageTemp.indexOf("\"")+1,pageTemp.indexOf(",")-1);
						System.out.println("renyoshiWord :" +renyoshiWord);
						if(count>=1) {
							int cut=pageTemp.indexOf("[",2);
							pageTemp = pageTemp.substring(cut);;
							System.out.println("pageTemp : "+pageTemp);
						}
					}
				}
				
				
				tempList.add(temp);
				k++;
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tempList;
	}
//		for (Transcript transcript : temp) {
//		String body = "{\"sentence\":\""+transcript.getTs_text()+"\",\"info_filter\":\"form|pos|read\",\"pos_filter\":\"名詞|連用詞\"}";
//		try {
//			URI uri = new URI("https://api.apigw.smt.docomo.ne.jp/gooLanguageAnalysis/v1/morph?APIKEY=6c7352564c78664e796348556c437466386776722e4e43305a6a6a4854594b764f656958484741372f6844");
//			URL url = uri.toURL();
//			HttpsURLConnection huc = (HttpsURLConnection)url.openConnection();
//			huc.setRequestMethod("POST");
//			huc.setDoInput(true);
//			huc.setDoOutput(true);
//			huc.setRequestProperty("Content-Type", "application/json");
//			OutputStream os = huc.getOutputStream();
//			os.write(body.getBytes("UTF-8"));
//			os.flush();
//			os.close();
//			
//			BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream(),"utf-8"));
//			String line="";
//			String page="";
//			while((line= br.readLine())!=null){
//				page += line;
//			}
//			
//				String meishi = "\"名詞\"";
//				int io_meishi = page.indexOf(meishi);	
//				String meishiWord;
//				meishiWord = page.substring(io_meishi+meishi.length()+2,(page.substring(io_meishi).indexOf("]")+io_meishi)-1);
//				tsList.add(transcript);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	model.addAttribute("tsList", tsList);
//		return "transcript/qPage";
//}
	
	//문제풀기용 리스트 가져오기
	public Transcript tsnum(int contents_num,int ts_num) {
		Transcript transcript = new Transcript();
		transcript = tsdao.ts_num(contents_num,ts_num);
		return transcript;
	}
}
