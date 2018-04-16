package com.h1b4.www;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.h1b4.www.youtube.download.YoutubeDownService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	/*@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! ssssThe client locale is {}.", locale);
		
		return "home";
	} */
	
	//메인페이지 이동
	@RequestMapping(value="/" , method = RequestMethod.GET)
	public String mainpage(){
		
		return "redirect:/contents/toUserHome"; 
	}

	//테스트페이지
	@RequestMapping(value="test" , method = RequestMethod.GET)
		public String testpage(){
			
			return "/test";
		}
	
	/*@GetMapping(value="dd") 
	public String home(String youtube){
		logger.info("시작ff");
		
		//youtube-dl --extract-audio --audio-format mp3 <video URL>
		
		//YoutubeDown.download(youtube, true);
		
		youtubeDownService.download(youtube);
		
		logger.info("끝");
		return "test";
	}*/
	
}
