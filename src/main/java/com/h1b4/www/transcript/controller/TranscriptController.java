package com.h1b4.www.transcript.controller;

import java.io.FileOutputStream;
import java.util.ArrayList;


import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.h1b4.www.transcript.service.TranscriptService;
import com.h1b4.www.vo.Transcript;


@Controller
@RequestMapping(value = "transcript")
public class TranscriptController {

	Document doc = null;

	/*
	 * @Autowired transcriptDAO tsdao;
	 */

	@Autowired
	TranscriptService transcriptService;

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

		transcriptService.insertTranscript(tsList);

		return "redirect:/";
	}

	/**
	 * function : 자막 불러오기 Parametr : contents_num Return :ts 객체
	 */
	@RequestMapping(value = "tsselect", method = RequestMethod.GET)
	public String selectT(Transcript ts, Model model) {

		// TODO: 자막 Select
		// ArrayList<Transcript> traList = dao.selectTranscript();

		return "";
	}

	@RequestMapping(value = "streamOnMic", method = RequestMethod.POST)
	@ResponseBody
	public boolean streamOnMic(@RequestBody String base64data) {

		try {

			System.out.println("incoming message ...");
			byte[] decodedByte = org.apache.commons.codec.binary.Base64
					.decodeBase64(base64data.split(",")[1].getBytes());

			FileOutputStream fos = new FileOutputStream("c:/tmp/test/myAudio.wav");
			fos.write(decodedByte);
			fos.flush();
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		System.out.println("로그");
		if (!base64data.equals("")) {
			return true;

		}
		return false;
	}

}
