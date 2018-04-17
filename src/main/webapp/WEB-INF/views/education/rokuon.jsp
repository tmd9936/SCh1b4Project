<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Web Audio API を使用した録音、及びWAVファイル出力のデモ - iroha Soft</title>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-3.2.1.js"></c:url>"></script>


<meta content="initial-scale=1, minimum-scale=1, width=device-width"
	name="viewport">
<meta
	content="Google Chrome is a browser that combines a minimal design with sophisticated technology to make the web faster, safer, and easier."
	name="description">
<link href="https://plus.google.com/100585555255542998765"
	rel="publisher">
<link href="https://www.google.com/images/icons/product/chrome-32.png"
	rel="icon" type="image/ico">

<link rel="stylesheet" type="text/css" href="resources/css/study.css">


</head>
<body>
	<input type="hidden" name="contents_num" value="223" id="contents_num">
	<input type="hidden" name="start" value="12" id="start">
	<input type="hidden" name="dur" value="4" id="dur">
	
	<h2>Web Audio API を使用した録音、及びWAVファイル出力のデモ</h2>

	※ 録音後、音声ファイル(WAVファイル)がダウンロードできます。
	<br>
	<br>

	<button id="start_button">시작</button>
	<button id="endBtn" onclick="stopRecording(this);" disabled>종료</button>

	<h3>録音ファイル</h3>
	<ul id="recordingslist"></ul>

	<h3>로그</h3>
	<pre id="log"></pre>
	
	<input type="hidden" id="streamVoice" name="voice">
	

	<form action="/www/transcript/streamOnMic" method="post" id="sendVoice"
		enctype="multipart/form-data"> 
		<input type="submit" value="가즈아">
	</form>
	<a href="webSTT">webSTT</a>
	<div class="browser-landing" id="main">
		<div class="compact marquee">
			<div id="info" style="visibility: hidden;">
				<p id="info_start" style="display: none;">Click on the
					microphone icon and begin speaking for as long as you like.</p>
				<p id="info_speak_now" style="display: inline;">Speak now.</p>
				<p id="info_no_speech" style="display: none">
					No speech was detected. You may need to adjust your <a
						href="https://support.google.com/chrome/bin/answer.py?hl=en&amp;answer=1407892">microphone
						settings</a>.
				</p>
				<p id="info_no_microphone" style="display: none">
					No microphone was found. Ensure that a microphone is installed and
					that <a
						href="https://support.google.com/chrome/bin/answer.py?hl=en&amp;answer=1407892">
						microphone settings</a> are configured correctly.
				</p>
				<p id="info_allow" style="display: none;">Click the "Allow"
					button above to enable your microphone.</p>
				<p id="info_denied" style="display: none">Permission to use
					microphone was denied.</p>
				<p id="info_blocked" style="display: none">Permission to use
					microphone is blocked. To change, go to
					chrome://settings/contentExceptions#media-stream</p>
				<p id="info_upgrade" style="display: none">
					Web Speech API is not supported by this browser. Upgrade to <a
						href="https://www.google.com/chrome">Chrome</a> version 25 or
					later.
				</p>
			</div>
			<!-- <div id="div_start">
				<button id="start_button" onclick="startButton(event)"
					style="display: inline-block;">
					<img alt="Start" id="start_img">
				</button>
			</div> -->
			<div id="results">
				<span class="final" id="final_span"></span> <span class="interim"
					id="interim_span"></span>
			</div>
		</div>
	</div>
	<div id="ytPitch" style="height: 250px; width: 40%;" class="pitchContainer"></div>
	
	<div id="memPitch" style="height: 250px; width: 40%;" class="pitchContainer"></div>
	
	 
	
	
	
	
	
	<script type="text/javascript" src="<c:url value="/resources/javascript/webSpeech.js"></c:url>"></script>

	<script src="<c:url value="/resources/js/recorder.js" />"></script>
</body>
</html>