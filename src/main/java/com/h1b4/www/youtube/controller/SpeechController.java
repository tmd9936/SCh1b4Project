package com.h1b4.www.youtube.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse.File;
import com.h1b4.www.youtube.speechapi.GoogleSpeechApi;

@Controller
public class SpeechController {

	
	GoogleSpeechApi googlespeechapi;
	
	@RequestMapping(value="translate", method=RequestMethod.GET)
	public String translate(File audio){
		
		String gcsuri = "gs://speechstorage_h1b4/1234567.flac";
		
		try {
			googlespeechapi.asyncRecognizeWords(gcsuri,1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "redirect:/";
	}
	
	
}
