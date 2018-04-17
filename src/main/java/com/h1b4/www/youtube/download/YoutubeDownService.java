package com.h1b4.www.youtube.download;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;

import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.h1b4.www.contents.dao.ContentsDAO;
import com.h1b4.www.transcript.service.TranscriptService;
import com.h1b4.www.vo.Contents;
import com.h1b4.www.vo.Transcript;
import com.h1b4.www.youtube.information.DataExtractor;
import com.h1b4.www.youtube.speechapi.GoogleSpeechApi;
import com.h1b4.www.youtube.storage.CloudStorageHelper;

@Service
public class YoutubeDownService {
	// 커맨드가 싱글로 돌아가서 충동이 안되게 싱크로나이즈 해줘야됨
	private String url = "";

	@Autowired
	TranscriptService tsService;

	@Autowired
	ContentsDAO contentsDAO;

	@Value("speechstorage_h1b4")
	private String bucketName;

	@Value(value = "classpath:H1B4-a8035531e67e.json")
	public Resource accountResource;

	private final Logger logger = LoggerFactory.getLogger(YoutubeDownService.class);

	/**
	 * 컨텐츠가 있으면 0 없는데 xml 자막이 있이면 1
	 * 
	 * @param youtubeUrl
	 * @param askExportPath
	 * @return String
	 */
	public int download(String youtubeUrl) {
		System.out.println("Downloading~");
		url = youtubeUrl;
		if (contentsDAO == null) {
			System.out.println("널임");
		}

		if (url.contains("https://www.youtube.com/watch?v=")) {
			url = url.replace("https://www.youtube.com/watch?v=", "");
		}

		// TODO : 여기서 이미 유튜브 영상이 contents 테이블에 존재하는지 확인
		// 있으면 바로 리턴
		Contents con = contentsDAO.searchByUrlContents(url);
		if (con != null) {
			return -1;
		}

		// 영상이 contants에 없을 때 contents 생성하고 contents의 번호 리턴
		DataExtractor extractor = new DataExtractor();
		try {
			con = extractor.getVideoData(youtubeUrl);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return -1;
		}

		System.out.println(contentsDAO.insertContents(con));
		System.out.println(con.getContents_num());
		// =======================================================//

		logger.debug("contents 넣기 끝");
		// 유튜브 영상에 생성된 자막이 있는지 확인
		Elements elements = null;
		try {
			elements = tsService.checkXml(url);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return -1;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return -1;
		}

		//음성 파일은 있어야 되니까 파일은 만들어둠
		String flacCommand = "/win64/usr/bin/youtube-dl --extract-audio --audio-format wav -o \"%(id)s.%(ext)s\" "
				+ url;
		String monoCommand = "ffmpeg -i " + url + ".wav -ac 1 -ar 16000 " + " m" + url + ".wav";
		String filename = "m" + url + ".wav";
		try {
			executeCommand(flacCommand, monoCommand);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 있으면 xml받아서 바로 테이터베이스에 넣고 리턴

		ArrayList<Transcript> tlist = null;
		if (elements != null) {
			tlist = tsService.xmlIntodatabase(con.getContents_num(), elements);
			tsService.insertTranscript(tlist);
			logger.debug("xml파일이 있을 때 넣기 끝");
			return 0;
		}

		// 생성된 자막이 없으면 구글 에서 파싱시작
		try {
			insertFile(filename);
			GoogleSpeechApi speechApi = new GoogleSpeechApi();
			tlist = speechApi.runTranslate(filename, con.getContents_num());
			tsService.insertTranscript(tlist);
			logger.debug("xml없을 때 파일 넣기 끝");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("ss");
		return con.getContents_num();
	}

	private String executeCommand(String flacCommand, String monoCommand) throws IOException, InterruptedException {
		int exitCode = 0;
		// String result = "";
		Process process;
		ProcessBuilder builder = new ProcessBuilder(flacCommand.replaceAll("[ ]+", " ").split(" "));
		builder.directory(new File("/tmp/test"));
		builder.inheritIO();
		process = builder.start();
		exitCode = process.waitFor();

		builder = new ProcessBuilder(monoCommand.replaceAll("[ ]+", " ").split(" "));
		builder.directory(new File("/tmp/test"));
		builder.inheritIO();
		process = builder.start();
		exitCode = process.waitFor();

		new File("/tmp/test/" + url + ".wav").delete();
		url = "";

		// TODO 파일을 구글 api로 텍스트 얻음
		// 텍스트 얻고나면 xml로 바꿈
		// 바꾼 xml을 데이터베이스에 저장

		return getStringFromInputStream(process.getInputStream());
	}
	
	public String commandffmpeg(String command) {
		int exitCode = 0;
		Process process = null;
		ProcessBuilder builder = new ProcessBuilder(command.replaceAll("[ ]+", " ").split(" "));
		builder.directory(new File("/tmp/test"));
		builder.inheritIO();
		try {
			process = builder.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			exitCode = process.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getStringFromInputStream(process.getInputStream());
	}

	private String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}

	/**
	 * 구글 스토리지에 파일 넣기
	 * 
	 * @param filename
	 * @throws Exception
	 */
	public void insertFile(String filename) throws Exception {
		Storage storage = StorageOptions.newBuilder()
				.setCredentials(ServiceAccountCredentials.fromStream(accountResource.getInputStream())).build()
				.getService();

		BlobInfo blobInfo = storage.create(BlobInfo.newBuilder(bucketName, filename) // Modify access list to allow all
																						// users with link to read file
				.setAcl(new ArrayList<>(Collections.singletonList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
				.build(), new FileInputStream(new File("c:/tmp/test/" + filename)));

		// return the public download link
		Files.deleteIfExists(new File("c:/tmp/test/" + filename).toPath());

	}
}
