

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

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;

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
	

	
	
	//전체 리스트 불러오기
	@RequestMapping(value="listTest", method=RequestMethod.GET)
	public String selectList(Model model) {
		ArrayList<Transcript> tsList = new ArrayList<>(); 
		int contents_num=221;
		//컨텐츠 넘버를 보낸다
		tsList = transcriptService.selectList(contents_num);
		model.addAttribute("contents_num", contents_num);
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
	
	@RequestMapping(value="wordDetail", method=RequestMethod.GET)
	public String wordDetail(int ts_num, int contents_num) {
		System.out.println(1);
		
		return "transcript/wordDetail";
	}


	@RequestMapping(value = "streamOnMic", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> streamOnMic(@RequestBody String base64data,HttpSession session) {
		
		String member_id = (String)session.getAttribute("loginId");
		
		// 세션에서 멤버아이디 가져와야됨
		//String member_id = "h1b4";
		//String ytFileName = "mG68_hkc29po";
		//double per = 0;
		
		return transcriptService.pitchCompare(base64data, member_id, youService);
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

