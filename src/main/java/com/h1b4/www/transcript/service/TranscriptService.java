package com.h1b4.www.transcript.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	//여기서 리스트를 다 가져오면 너무 많으니까 몇 개만 추려서 가져온다
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
		//랜덤으로 5개만 받고
		for(int i=0;i<5;i++) {
			int num = r.nextInt(tsList.size());
			temp = tsList.get(num);
			tempList.add(temp);
		}
//		for (Transcript transcript : temp) {
//		String body = "{\"sentence\":\""+transcript.getTs_text()+"\",\"info_filter\":\"form|pos|read\",\"pos_filter\":\"名詞|連用詞\"}";
//		try {
//			URI uri = new URI("https://api.apigw.smt.docomo.ne.jp/gooLanguageAnalysis/v1/morph?APIKEY=483567313073493142416249757669777545574a5575626e2f755145677a5a4c2f63394d69364757646532");
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
		return tempList;
	}
	//문제풀기용 리스트 가져오기
	public Transcript tsnum(int contents_num,int ts_num) {
		Transcript transcript = new Transcript();
		transcript = tsdao.ts_num(contents_num,ts_num);
		return transcript;
	}
}
