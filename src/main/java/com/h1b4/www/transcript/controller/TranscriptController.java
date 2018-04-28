
package com.h1b4.www.transcript.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.h1b4.www.transcript.dao.TranscriptMapper;
import com.h1b4.www.transcript.service.TranscriptService;
import com.h1b4.www.utils.Aitalk;
import com.h1b4.www.utils.programs.ConsoleMain;
import com.h1b4.www.vo.Transcript;
import com.h1b4.www.youtube.download.YoutubeDownService;


@Controller
@RequestMapping(value="transcript")
public class TranscriptController {

	
	Document doc = null;
	
	/*@Autowired
	transcriptDAO tsdao;*/
	
	@Autowired
	TranscriptService transcriptService;
	
	@Autowired
	YoutubeDownService youService;
	
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
	
	
	//전체 리스트 불러오기 지금은 임시방편으로 컨텐츠 넘버 임의 설정했지만 원래는 파라미터로 받아야 한다.
	//이거는 문장 학습하기 버튼을 눌렀을 때 작동하는 것임
	@ResponseBody
	@RequestMapping(value="listTest", method=RequestMethod.POST)
	public ArrayList<Transcript> selectList( @RequestParam(value="contents_num")int contents_num, Model model) {
		ArrayList<Transcript> tsList = new ArrayList<>(); 
		
		//컨텐츠 넘버를 보낸다
		tsList = transcriptService.selectList(contents_num);
		model.addAttribute("contents_num", contents_num);
		model.addAttribute("tsList", tsList);
		
		return tsList;
	}
	
//	체크한 문장 번호를 가지고 문제 푸는 페이지로 출발
	@RequestMapping(value="test", method=RequestMethod.POST)
	public String qPage(String[] ts_num,Model model, String contents_num) {
		ArrayList<Transcript> tslist = new ArrayList<>();
		for (String num : ts_num) {
			Transcript transcript = transcriptService.tsnum(Integer.parseInt(contents_num),Integer.parseInt(num));
			tslist.add(transcript);
		}
		
		model.addAttribute("tslist", tslist);
		model.addAttribute("contents_num", contents_num);
		return "transcript/test";
	}
	
//	@RequestMapping(value="wordDetail", method=RequestMethod.GET)
//	public String wordDetail(int ts_num, int contents_num) {
//		System.out.println(ts_num);
//		System.out.println(contents_num);
//		
//		return "transcript/wordDetail";
//	}
	@RequestMapping(value="suzuki", method=RequestMethod.GET)
	public String suzuki(String contents_num,String ts_num) {
		System.out.println("무사히 여기로 도착");
		Transcript ts = transcriptService.tsnum(Integer.parseInt(contents_num), Integer.parseInt(ts_num));
		System.out.println("스즈키ts"+ts);
		Aitalk at = new Aitalk();
		at.Voice(ts.getTs_text());
		return "../contents/studySpace?contents_num="+contents_num;
		
	}
	
	@RequestMapping(value = "streamOnMic", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> streamOnMic(@RequestBody String base64data) {

		// 세션에서 멤버아이디 가져와야됨
		String member_id = "h1b4";
		String ytFileName = "mG68_hkc29po";
		double per = 0;
		
		try {

			System.out.println("incoming message ...");
			// int size = base64data.split("\n").length;
			String s[] = base64data.split("\n");

			String base64 = s[3].replace("\r", "");
			String contents_num = s[7].replace("\r", "");
			int start = (int) Float.parseFloat(s[11].replace("\r", ""));
			float dur = Float.parseFloat(s[15].replace("\r", ""));
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

	@RequestMapping(value="wordDetail", method=RequestMethod.GET)
	public String wordDetail(String[] words,int ts_num,int contents_num,String explanation, Model model) {

        String clientId = "ApXjZWS0hsBZjfMcPmq6";
        String clientSecret = "7A01nsIhTY";
        String result="";
        String word="";
        for (String string : words) {
			word += string+"";
		}
        System.out.println(word);
        try {
            String text = URLEncoder.encode(word, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/language/translate";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            
            
            String postParams = "source=ko&target=ja&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { 
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            String answer = response.substring(112);
            String[] words1;
            words1 = answer.split("\"");  
            
           
            result = words1[1];
        } catch (Exception e) {
            //System.out.println(e);
        	System.out.println("입력이 올바르지 않습니다");
        }
        Transcript ts= transcriptService.tsnum(contents_num, ts_num);
        model.addAttribute("ts", ts);
        model.addAttribute("explanation", explanation);
        model.addAttribute("result", result);
        model.addAttribute("contents_num", contents_num);
        return "transcript/wordDetail";
	}
}

