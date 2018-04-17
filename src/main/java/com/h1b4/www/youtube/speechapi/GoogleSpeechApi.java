package com.h1b4.www.youtube.speechapi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.api.gax.rpc.ApiStreamObserver;
import com.google.api.gax.rpc.BidiStreamingCallable;
import com.google.cloud.speech.v1.LongRunningRecognizeMetadata;
import com.google.cloud.speech.v1.LongRunningRecognizeResponse;
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.cloud.speech.v1.StreamingRecognitionConfig;
import com.google.cloud.speech.v1.StreamingRecognitionResult;
import com.google.cloud.speech.v1.StreamingRecognizeRequest;
import com.google.cloud.speech.v1.StreamingRecognizeResponse;
import com.google.cloud.speech.v1.WordInfo;
import com.google.common.util.concurrent.SettableFuture;
import com.google.protobuf.ByteString;
import com.h1b4.www.vo.Transcript;


public class GoogleSpeechApi {

	// 1�� �̻��� ����� �����϶�, (** GCS�� �÷��� �̿��ؾ� ��)

	public static void longRecognitionSpeech(String filePath) {

		try {
			SpeechClient speech = SpeechClient.create();

			// ����� ���Ͽ� ���� �����κ�
			RecognitionConfig config = RecognitionConfig.newBuilder().setEncoding(RecognitionConfig.AudioEncoding.FLAC)
					.setSampleRateHertz(48000)
					// .setSampleRateHertz(44100)

					.setLanguageCode("en-US").build();

			RecognitionAudio audio = getRecognitionAudio(filePath); // Audio ���Ͽ� ���� RecognitionAudio �ν��Ͻ� ����

			// Non-Blocking ���� ȣ���� �ϸ� �� ���� �϶��� LongRunningRecognizeResponse��
			// �����
			OperationFuture<LongRunningRecognizeResponse, LongRunningRecognizeMetadata> response = speech
					.longRunningRecognizeAsync(config, audio);

			while (!response.isDone()) {
				System.out.println("Waiting for response...");
				Thread.sleep(10000);
			}

			List<SpeechRecognitionResult> results = response.get().getResultsList();

			for (SpeechRecognitionResult result : results) {
				SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
				System.out.printf("Transcription: %s\n", alternative.getTranscript());
			}
			speech.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static RecognitionAudio getRecognitionAudio(String filePath) throws IOException {
		RecognitionAudio recognitionAudio;

		// ������ GCS�� �ִ� ���
		if (filePath.startsWith("gs://")) {
			recognitionAudio = RecognitionAudio.newBuilder().setUri(filePath).build();
		} else { // ������ ���ÿ� �ִ� ���
			Path path = Paths.get(filePath);
			byte[] data = Files.readAllBytes(path);
			ByteString audioBytes = ByteString.copyFrom(data);

			recognitionAudio = RecognitionAudio.newBuilder().setContent(audioBytes).build();
		}

		return recognitionAudio;
	}

	public static void asyncRecognizeGcs(String gcsUri) throws Exception {
		// Instantiates a client with GOOGLE_APPLICATION_CREDENTIALS
		try (SpeechClient speech = SpeechClient.create()) {

			// Configure remote file request for Linear16
			RecognitionConfig config = RecognitionConfig.newBuilder().setEncoding(AudioEncoding.FLAC)
					.setLanguageCode("ja-JP").setSampleRateHertz(16000).build();
			RecognitionAudio audio = RecognitionAudio.newBuilder().setUri(gcsUri).build();

			// Use non-blocking call for getting file transcription
			OperationFuture<LongRunningRecognizeResponse, LongRunningRecognizeMetadata> response = speech
					.longRunningRecognizeAsync(config, audio);
			while (!response.isDone()) {
				System.out.println("Waiting for response...");
				Thread.sleep(10000);
			}

			List<SpeechRecognitionResult> results = response.get().getResultsList();

			for (SpeechRecognitionResult result : results) {
				// There can be several alternative transcripts for a given chunk of speech.
				// Just use the
				// first (most likely) one here.
				SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
				System.out.printf("Transcription: %s\n", alternative.getTranscript());
			}
		}
	}

	public static ArrayList<Transcript> asyncRecognizeWords(String gcsUri,int contents_num) throws Exception, IOException {
		// Instantiates a client with GOOGLE_APPLICATION_CREDENTIALS
		SpeechClient speech = SpeechClient.create();

		// Configure remote file request for Linear16
		RecognitionConfig config = RecognitionConfig.newBuilder().setEncoding(AudioEncoding.LINEAR16).setSampleRateHertz(16000)
				.setLanguageCode("ja-JP").setEnableWordTimeOffsets(true).build();
		RecognitionAudio audio = RecognitionAudio.newBuilder().setUri(gcsUri).build();

		// Use non-blocking call for getting file transcription
		OperationFuture<LongRunningRecognizeResponse, LongRunningRecognizeMetadata> response = speech
				.longRunningRecognizeAsync(config, audio);
		while (!response.isDone()) {
			System.out.println("Waiting for response...");
			Thread.sleep(10000);
		}

		List<SpeechRecognitionResult> results = response.get().getResultsList();
		ArrayList<Transcript> transcriptList = new ArrayList<>();
		ArrayList<String> rr = new ArrayList<>();

		for (SpeechRecognitionResult result : results) {
			// There can be several alternative transcripts for a given chunk of speech.
			// Just use the
			// first (most likely) one here.
			Transcript transcript = new Transcript();
			SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);

			// transcript.setTs_text(alternative.getTranscript());

			System.out.printf("Transcription: %s\n", alternative.getTranscript());

			String r = alternative.getTranscript();
			rr.add(r);

			/*
			 * ArrayList<HashMap<String, String>> params = new ArrayList<>();
			 * HashMap<String, String> param = null;
			 */
			String first = "";
			String last = "";
			
			int i = 0;
			for (WordInfo wordInfo : alternative.getWordsList()) {
				/*
				 * System.out.println(wordInfo.getWord());
				 * System.out.printf("\t%s.%s sec - %s.%s sec\n",
				 * wordInfo.getStartTime().getSeconds(), wordInfo.getStartTime().getNanos() /
				 * 100000000, wordInfo.getEndTime().getSeconds(),
				 * wordInfo.getEndTime().getNanos() / 100000000);
				 */
				if (first.equals("")) {
					first = wordInfo.getStartTime().getSeconds() + "." + wordInfo.getStartTime().getNanos() / 100000000;
					// transcript.setStarttime(Double.parseDouble(first));
				} else {

				}

				last = wordInfo.getEndTime().getSeconds() + "." + wordInfo.getEndTime().getNanos() / 100000000;
				// transcript.setEndtime(Double.parseDouble(last));
				// transcript = new
				// Transcript(alternative.getTranscript(),Double.parseDouble(first) ,
				// Double.parseDouble(last));
				transcript.setTs_text(alternative.getTranscript());
				transcript.setTs_start(Double.parseDouble(first) + "");
				transcript.setTs_dur((Double.parseDouble(last) - Double.parseDouble(first)) + "");
				transcript.setContents_num(contents_num);
				transcript.setTs_num(i++);
				/*
				 * param.put("starttime",
				 * wordInfo.getStartTime()+"."+wordInfo.getStartTime().getNanos() / 100000000);
				 * param.put("endtime",
				 * wordInfo.getEndTime()+"."+wordInfo.getEndTime().getNanos() / 100000000);
				 * params.add(param);
				 */
			}
			transcriptList.add(transcript);
		}
		for (Transcript s : transcriptList) {
			System.out.println(s);

		}

		speech.close();

		return transcriptList;
	}

	public static void streamingRecognizeFile(String fileName) throws Exception, IOException {
		Path path = Paths.get(fileName);
		byte[] data = Files.readAllBytes(path);

		// Instantiates a client with GOOGLE_APPLICATION_CREDENTIALS
		SpeechClient speech = SpeechClient.create();

		// Configure request with local raw PCM audio
		RecognitionConfig recConfig = RecognitionConfig.newBuilder().setEncoding(AudioEncoding.FLAC)
				.setLanguageCode("ja-JP").setSampleRateHertz(16000).build();
		StreamingRecognitionConfig config = StreamingRecognitionConfig.newBuilder().setConfig(recConfig).build();

		class ResponseApiStreamingObserver<T> implements ApiStreamObserver<T> {
			private final SettableFuture<List<T>> future = SettableFuture.create();
			private final List<T> messages = new java.util.ArrayList<T>();

			@Override
			public void onNext(T message) {
				messages.add(message);
			}

			@Override
			public void onError(Throwable t) {
				future.setException(t);
			}

			@Override
			public void onCompleted() {
				future.set(messages);
			}

			// Returns the SettableFuture object to get received messages / exceptions.
			public SettableFuture<List<T>> future() {
				return future;
			}
		}

		ResponseApiStreamingObserver<StreamingRecognizeResponse> responseObserver = new ResponseApiStreamingObserver<StreamingRecognizeResponse>();

		BidiStreamingCallable<StreamingRecognizeRequest, StreamingRecognizeResponse> callable = speech
				.streamingRecognizeCallable();

		ApiStreamObserver<StreamingRecognizeRequest> requestObserver = callable.bidiStreamingCall(responseObserver);

		// The first request must **only** contain the audio configuration:
		requestObserver.onNext(StreamingRecognizeRequest.newBuilder().setStreamingConfig(config).build());

		// Subsequent requests must **only** contain the audio data.
		requestObserver
				.onNext(StreamingRecognizeRequest.newBuilder().setAudioContent(ByteString.copyFrom(data)).build());

		// Mark transmission as completed after sending the data.
		requestObserver.onCompleted();

		List<StreamingRecognizeResponse> responses = responseObserver.future().get();

		for (StreamingRecognizeResponse response : responses) {
			// For streaming recognize, the results list has one is_final result (if
			// available) followed
			// by a number of in-progress results (if iterim_results is true) for subsequent
			// utterances.
			// Just print the first result here.
			StreamingRecognitionResult result = response.getResultsList().get(0);
			// There can be several alternative transcripts for a given chunk of speech.
			// Just use the
			// first (most likely) one here.
			SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
			System.out.println(alternative.getTranscript());
		}
		speech.close();
	}

	/**
	 * 구글 스토리지의 파일을 스피치API로 변환시켜서 텍스트로 가져오기
	 * 
	 * @param filename
	 *            .flac포함 파일 이름
	 */
	public ArrayList<Transcript> runTranslate(String filename,int contents_num) {
		String gcsuri = "gs://speechstorage_h1b4/" + filename;
		ArrayList<Transcript> list = null;
		try {
			list = asyncRecognizeWords(gcsuri,contents_num);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

}
