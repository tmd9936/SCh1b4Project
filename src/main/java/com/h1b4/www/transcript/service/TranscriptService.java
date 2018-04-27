package com.h1b4.www.transcript.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.h1b4.www.transcript.dao.TranscriptDAO;
import com.h1b4.www.utils.programs.ConsoleMain;
import com.h1b4.www.vo.Transcript;
import com.h1b4.www.youtube.download.YoutubeDownService;

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
			//랜덤으로 10개만 받고
			int i=10;
			
			//근데 문제로 만들 수 없을 내용(명사나 동사가 포함되지 않은)은 걸러낸다.
			while(i>0) {
				int num = r.nextInt(tsList.size());
				temp = tsList.get(num);
				String body = "{\"sentence\":\""+temp.getTs_text()+"\",\"info_filter\":\"form|pos|read\",\"pos_filter\":\"名詞|連用詞|動詞活用語尾|動詞接尾辞|動詞語幹\"}";
				try {
					URI uri = new URI("https://api.apigw.smt.docomo.ne.jp/gooLanguageAnalysis/v1/morph?APIKEY=766258364c33527044357054725a7149306e684c4a4243764c384673444c355a2e554863662f306f696238");
					URL url = uri.toURL();
					HttpsURLConnection huc = (HttpsURLConnection)url.openConnection();
					huc.setRequestMethod("POST");
					huc.setDoInput(true);
					huc.setDoOutput(true);
					huc.setRequestProperty("Content-Type", "application/json");
					OutputStream os = huc.getOutputStream();
					os.write(body.getBytes("UTF-8"));
					os.flush();
					os.close();
					BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream(),"utf-8"));
					String line="";
					String page="";
					while((line= br.readLine())!=null){
						page += line;
					}
						int word = page.indexOf(":[")+3;	
						String meishi = page.substring(word);
						System.out.println(meishi);
						if(meishi.length()<20) {
							continue;
						}
						if(meishi.length()>200) {
							tempList.add(temp);
							return tempList;
						}
				}catch (Exception e) {
					e.printStackTrace();
				}
				i--;
				System.out.println("temp:"+temp);
				tempList.add(temp);
			}
		return tempList;
		}
		
		
		
	//문제풀기용 리스트 가져오기
	public Transcript tsnum(int contents_num,int ts_num) {
		Transcript transcript = new Transcript();
		transcript = tsdao.ts_num(contents_num,ts_num);
		return transcript;
	}
	
	
	
	public HashMap<String, Object> pitchCompare(String base64data,String member_id,YoutubeDownService youService){
		String ytFileName = "mG68_hkc29po";
		double per = 0;
		
		//컨텐츠 교육 화면에서 Filename이랑 ts_dur,ts_num,ts_start,ts_text 가져와야됨
				try {

					System.out.println("incoming message ...");
					// int size = base64data.split("\n").length;
					String s[] = base64data.split("\n");

					String base64 = s[3].replace("\r", "");
					String contents_num = s[7].replace("\r", "");
					int start = (int) Float.parseFloat(s[11].replace("\r", ""));
					float dur = Float.parseFloat(s[15].replace("\r", ""));
					ytFileName = s[19].replace("\r", "");
					// int end =
					// (int)(Float.parseFloat(s[11].replace("\r",""))+Float.parseFloat(s[15].replace("\r","")));

					System.out.println(contents_num + " " + start + " " + dur);
					byte[] decodedByte = org.apache.commons.codec.binary.Base64.decodeBase64(base64.split(",")[1].getBytes());

					// String test = base64data.split(",")[1].substring(0,
					// base64data.compareTo("------WebKitFormBoundary"));
					// System.out.println(t);

					/*
					 * File file = new File("c:/tmp/"+member_id); if(!file.isDirectory()) {
					 * file.mkdir(); }
					 */

					FileOutputStream fos = new FileOutputStream("c:/tmp/test/" + member_id + ytFileName + ".wav");

					// 저장할 파일명을 오늘 날짜의 년월일로 생성
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					String savedFilename = sdf.format(new Date());

					savedFilename += member_id;

					File safile = new File("c:/tmp/test/" + savedFilename + ".wav");
					if (safile.isFile()) {
						safile.delete();
					}

					// 유저아이디 붙히기
					String command = "ffmpeg -ss " + start + " -t " + dur + " -i c:/tmp/test/" + ytFileName
							+ ".wav -acodec copy " + savedFilename + ".wav";

					youService.commandffmpeg(command);

					fos.write(decodedByte);
					fos.flush();
					fos.close();

					ConsoleMain consoleMain = new ConsoleMain(savedFilename + ".wav", member_id);
					List<Double> ytArr = consoleMain.getData();

					ConsoleMain consoleMain2 = new ConsoleMain(member_id + ytFileName + ".wav", member_id);
					List<Double> memArr = consoleMain2.getData();
						
					System.out.println("size = " + ytArr.size()); 
					System.out.println("size = " + memArr.size());
					
					
					if(ytArr.size() > memArr.size()) {
						for(int i=memArr.size(); i<ytArr.size(); i++) {
							memArr.add(0.0);
						}
					}else {
						memArr = memArr.subList(0, ytArr.size());
					}
					
					int val = 200;
					
					System.out.println("size = " + ytArr.size());
					System.out.println("size = " + memArr.size());
					
					int cnt = ytArr.size()/5;
					int a = 0;
					int b = 0;
					
					int x = 0;
					int y = 0;
					int ok = 0; // 횟수
					
					if(cnt == 0) {
						for(int i = 0; i<ytArr.size(); i++) {
							a += ytArr.get(i); 
						}
						for(int j = 0; j<memArr.size(); j++) {
							b += memArr.get(j);
						}
						
						per = (a>b) ? b/a : a/b;
					}else {
						for(int i=0; i<cnt; i++) {
							for(a=b; a<b+5; a++) {
								x += ytArr.get(a);
								y += memArr.get(b);
							}
							if(y>x-val && y<x+val) {
								ok++;
							}
							b +=5;
							x=0;
							y=0;
						}
						for(int i=cnt*5; i<ytArr.size(); i++) {
							x += ytArr.get(i);
							y += memArr.get(i);	
						}
						if(y>x-val && y<x+val) {
							ok++;
						}
					}
					
					per = ((ok*1.0)/(cnt*1.0))*100;
					HashMap<String, Object> resultMap = new HashMap<>();
					resultMap.put("per", per);
					resultMap.put("ytArr", ytArr);
					resultMap.put("memArr",memArr);
					
					
					return resultMap;
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}

				System.out.println("로그");
				if (!base64data.equals("")) {
					
					return null;

				}
				return null;
	}
	
	public  ArrayList<Transcript> getTsList(int contents_num){
		ArrayList<Transcript> tsList = tsdao.selectList(contents_num);
		
		return tsList;
	}
}
