package com.h1b4.www.transcript.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.api.client.http.HttpMediaType;
import com.google.api.client.json.Json;
import com.h1b4.www.transcript.service.TranscriptService;
import com.h1b4.www.utils.programs.ConsoleMain;
import com.h1b4.www.utils.programs.Intsint;
import com.h1b4.www.utils.programs.Momel;
import com.h1b4.www.vo.Transcript;
import com.h1b4.www.youtube.download.YoutubeDownService;

@Controller
@RequestMapping(value = "transcript")
public class TranscriptController {

	Document doc = null;

	/*
	 * @Autowired transcriptDAO tsdao;
	 */

	@Autowired
	TranscriptService transcriptService;

	@Autowired
	YoutubeDownService youService;

	@RequestMapping(value = "tstest", method = RequestMethod.GET)
	public String transcript(String youtube) {

		// TODO: 컨텐츠 넣기 컨텐츠의 번호 얻어오기

		// passer.insertTranscript(passer.xmlIntodatabase("RTDaq9X3Sws"));

		// YoutubeDown.download(youtube, true);

		return "transcript/tstest";
	}

	/**
	 * function : 자막넣기 Parameter : contents_num, ts_start, ts_dur ? end 값은 어디감?
	 */
	@RequestMapping(value = "tsinsert", method = RequestMethod.POST)
	public String insertT(ArrayList<Transcript> tsList) {

		// TODO: 자막 Insert
		System.out.println("어레이리스트"+tsList);
		//System.out.println(contents_num);
		transcriptService.insertTranscript(tsList);

		return "redirect:/";
	}

	/**
	 * function : 자막넣기 Parameter : contents_num, ts_start, ts_dur ? end 값은 어디감?
	 */
	@ResponseBody
	@RequestMapping(value = "insertTs", method = RequestMethod.POST)
	public String insertTs(@RequestBody List<Transcript> tsList) {

		System.out.println(tsList);
		// TODO: 자막 Insertk
		/*for(String l : tsList){
			System.out.println(l);
		}*/
		
		//System.out.println(contents_num);
		transcriptService.insertTranscript(tsList);

		return "redirect:/";
	}

	
	/**
	 * function : 편집용 자막넣기 Parameter : contents_num, ts_start, ts_dur ? end 값은 어디감?
	 */
	@ResponseBody
	@RequestMapping(value = "insertEdit", method = RequestMethod.POST)
	public String insertEdit(@RequestBody List<Transcript> tsList) {

		System.out.println(tsList);
		// TODO: 자막 Insertk
		/*for(String l : tsList){
			System.out.println(l);
		}*/
		
		//System.out.println(contents_num);
		transcriptService.insertEditList(tsList);

		return "redirect:/";
	}
	
	
	
	
	/**
	 * function : 자막 불러오기 Parametr : contents_num Return :ts 객체
	 */
	@RequestMapping(value = "tsselect", method = RequestMethod.GET)
	public String selectT(Transcript ts, Model model) {

		// TODO: 자막 Select
		 ArrayList<Transcript> traList = transcriptService.selectTranscript(ts);
		 
		 model.addAttribute("tsList", traList);

		return "publish/editSpace";
	}
	

	/**
	 * function : 편집용 자막 불러오기 Parametr : contents_num Return :ts 객체
	 */
	@RequestMapping(value = "editselect", method = RequestMethod.GET)
	public String editselectT(Transcript ts, Model model) {

		// TODO: 자막 Select
		 ArrayList<Transcript> traList = transcriptService.selectEditList(ts);
		 
		 model.addAttribute("editList", traList);

		return "publish/editSpace";
	}
	

	@RequestMapping(value = "streamOnMic", method = RequestMethod.POST)
	@ResponseBody
	public String streamOnMic(@RequestBody String base64data) {

		// 세션에서 멤버아이디 가져와야됨
		String member_id = "h1b4";
		String ytFileName = "mzvXkRNRHvLk";
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
			return per+"";
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		System.out.println("로그");
		if (!base64data.equals("")) {
			return "no";

		}
		return "no";
	}

	class AudioFile {
		private String base64data;
		private String contents_num;

		public String getBase64data() {
			return base64data;
		}

		public void setBase64data(String base64data) {
			this.base64data = base64data;
		}

		public String getContents_num() {
			return contents_num;
		}

		public void setContents_num(String contents_num) {
			this.contents_num = contents_num;
		}

	}
	
	@RequestMapping(value="goEditSpace", method = RequestMethod.GET)
	public String goEditSpace(Model model, Transcript ts){
		
		ArrayList<Transcript> tsList =  transcriptService.selectTranscript(ts);
		
		String youtubeUrl = transcriptService.selectContentsUrl(ts.getContents_num());
		
		model.addAttribute("url", youtubeUrl);
		model.addAttribute("tsList", tsList);
		
		return "publish/editSpace";
	}
	
	//편집공간 자막 한줄씩 넣기((사용안함))
	@RequestMapping(value = "insertTsOne" , method = RequestMethod.POST)
	@ResponseBody
	public boolean insertTsOne(Transcript transcript){
	
			
		
		return false;
	}
	
	@RequestMapping(value= "deleteTs" , method = RequestMethod.POST)
	public String deleteTranscript(int contents_num){
		int result = transcriptService.deleteTranscript(contents_num);
		
		return "";
	}
	

}
