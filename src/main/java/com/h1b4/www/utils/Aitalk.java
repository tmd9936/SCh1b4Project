package com.h1b4.www.utils;

import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;

import jp.ne.docomo.smt.dev.common.exception.SdkException;
import jp.ne.docomo.smt.dev.common.exception.ServerException;
import jp.ne.docomo.smt.dev.aitalk.AiTalkTextToSpeech;
import jp.ne.docomo.smt.dev.aitalk.data.AiTalkSsml;
import jp.ne.docomo.smt.dev.common.http.AuthApiKey;
import jp.ne.docomo.smt.dev.common.http.ProxyInfo;


public class Aitalk {
	public void Voice(String text) {
		// 音声文字列ＳＳＭＬ
		AiTalkSsml ssml;
		// 音声ＰＣＭリニアデータ
		byte[] resultData = null;
		try {
			// APIKEY の設定
			AuthApiKey.initializeAuth("55684a6c72694d576b7134376a493250466a47476c36676559657649794678563731336353763330384c37");
			// プロキシの設定
			// プロキシを使用しない場合はコメントにしてください
			ProxyInfo.getInstance();
			// のぞみさんの音声で、文字列を、ＳＳＭＬクラスに登録する
			AiTalkTextToSpeech search = new AiTalkTextToSpeech();
			ssml = new AiTalkSsml();
			ssml.startVoice("nozomi");
			ssml.startProsody(0.8f, 1.2f, 1.2f, 1.0f);
//			ssml.startProsody(1.4f, 1.3f, 1.2f, 1.1f);
			ssml.addText(text);
			ssml.endProsody();
			ssml.endVoice();
			
			/*
			ssml.addBreak(1000);

			ssml.startVoice("nozomi");
			ssml.addText("です。");
			ssml.endVoice();
			 */		
					
			// 要求処理クラスにリクエストデータを渡し、レスポンスデータを取得する
            resultData = search.requestAiTalkSsmlToSound(ssml.makeSsml());
			// 音声出力
            AudioFormat af = new AudioFormat(16000f,16,1,true,true);
            DataLine.Info info = new DataLine.Info (Clip.class, af);
            Clip clip = (Clip)AudioSystem.getLine(info);
            clip.open(af, resultData, 0, resultData.length);
            clip.start();
            Thread.sleep(resultData.length/32);
            clip.close();
		} catch (SdkException ex) {
			ex.printStackTrace();
		} catch (ServerException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
