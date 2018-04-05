package com.h1b4.www.youtube.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.h1b4.www.HomeController;
import com.h1b4.www.transcript.service.TranscriptService;
import com.h1b4.www.vo.Transcript;
import com.h1b4.www.youtube.download.YoutubeDownService;
import com.h1b4.www.youtube.speechapi.GoogleSpeechApi;


@Controller
public class YoutubeController {

	@Value("gs://speechstorage_h1b4/123456.flac")
	private Resource gcsFile;
	
	@Value("speechstorage_h1b4")
	private String bucketName;
	
	@Value(value="classpath:H1B4-a8035531e67e.json")
	public Resource accountResource;
	
	@Autowired
	YoutubeDownService downService;
	
	@Autowired
	TranscriptService tsService;
	
	private static final Logger logger = LoggerFactory.getLogger(YoutubeController.class);
	
	private String location = "";
	

	@RequestMapping(value = "readGcs", method=RequestMethod.GET)
	public String readGcsFile() throws IOException{
		return StreamUtils.copyToString(gcsFile.getInputStream(), Charset.defaultCharset()) + "\n";
	}
	
	@RequestMapping(value="ytDown", method= RequestMethod.GET)
	public String writeGcs(HttpServletResponse resp, HttpServletRequest req,String youtube) throws IOException, Exception{
		logger.info("유튭 다운 시작");
		int result = downService.download(youtube);
		System.out.println(result);
		/*if(result == 0 || result == -1) {
			return "test";
		}
		if(youtube.contains("https://www.youtube.com/watch?v=")) {
			youtube = youtube.replace("https://www.youtube.com/watch?v=", "");
		}
		String filename = "m"+youtube+".flac";
		
		Storage storage = StorageOptions.newBuilder()
				.setCredentials(ServiceAccountCredentials.fromStream(accountResource.getInputStream()))
				.build()
				.getService();
		
		BlobInfo blobInfo = storage.create( BlobInfo.newBuilder(bucketName, filename) // Modify access list to allow all users with link to read file 
				.setAcl(new ArrayList<>(Collections.singletonList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER)))) 
				.build()
				, new FileInputStream(new File("c:/tmp/test/"+filename))); 
		
		GoogleSpeechApi speechApi = new GoogleSpeechApi();
		ArrayList<Transcript> tsList = speechApi.runTranslate(filename,result);
		
		if(tsList == null) {
			logger.info("추출할 음성이 없음");
			return "test";
		}
		
		tsService.insertTranscript(tsList);*/
		
		// return the public download link 
		//Files.deleteIfExists(new File("c:/tmp/test/42.flac").toPath());
		logger.info("유튭 다운 종료");
		return "test";
		
	}
	
	
	
}
