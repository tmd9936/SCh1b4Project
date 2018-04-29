
package com.h1b4.www.transcript.controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

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
	@ResponseBody
	@RequestMapping(value="suzuki", method=RequestMethod.POST)
	public String suzuki(@RequestParam(value="sentence")String sentence,@RequestParam(value="contents_num")String contents_num) {
		System.out.println("무사히 여기로 도착1");
		        String clientId = "07Y3WutHWeMsdGPtw6AK";//애플리케이션 클라이언트 아이디값";
		        String clientSecret = "pvT5lX8Twe";//애플리케이션 클라이언트 시크릿값";
		        try {
		            String text = URLEncoder.encode(sentence, "UTF-8"); 
		            String apiURL = "https://openapi.naver.com/v1/voice/tts.bin";
		            URL url = new URL(apiURL);
		            HttpURLConnection con = (HttpURLConnection)url.openConnection();
		            con.setRequestMethod("POST");
		            con.setRequestProperty("X-Naver-Client-Id", clientId);
		            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
		            // post request
		            String postParams = "speaker=yuri&speed=0&text=" + text;
		            con.setDoOutput(true);
		            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		            wr.writeBytes(postParams);
		            wr.flush();
		            wr.close();
		            System.out.println("무사히 여기로 도착2");
		            int responseCode = con.getResponseCode();
		            BufferedReader br;
		            if(responseCode==200) { // 정상 호출
		                InputStream is = con.getInputStream();
		                int read = 0;
		                byte[] bytes = new byte[1024];
		                // 랜덤한 이름으로 mp3 파일 생성
		                String tempname = Long.valueOf(new Date().getTime()).toString();
		                File f = new File(tempname + ".mp3");
		                f.createNewFile();
		                System.out.println("무사히 여기로 도착3");
		                String bip = tempname+".mp3";
		                OutputStream outputStream = new FileOutputStream(f);
		                while ((read =is.read(bytes)) != -1) {
		                    outputStream.write(bytes, 0, read);
		                }
		                System.out.println("outputStream:"+outputStream);
		                System.out.println("is :"+is);
		                System.out.println("bip :"+bip);
		                is.close();
		            } else {  // 에러 발생
		                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		                String inputLine;
		                StringBuffer response = new StringBuffer();
		                while ((inputLine = br.readLine()) != null) {
		                    response.append(inputLine);
		                }
		                br.close();
		                System.out.println(response.toString());
		            }
		        } catch (Exception e) {
		            System.out.println(e);
		        }
		
		        System.out.println("마지막");
		return "";
		
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

