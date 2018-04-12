<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Web Audio API を使用した録音、及びWAVファイル出力のデモ - iroha Soft</title>
<style type='text/css'>
ul {
	list-style: none;
}

#recordingslist audio {
	display: block;
	margin-bottom: 10px;
}
</style>
</head>
<body>
	<input type="hidden" name="contents_num" value="223" id="contents_num">
	<input type="hidden" name="start" value="5" id="start">
	<input type="hidden" name="dur" value="2" id="dur">
	
	<h2>Web Audio API を使用した録音、及びWAVファイル出力のデモ</h2>

	※ 録音後、音声ファイル(WAVファイル)がダウンロードできます。
	<br>
	<br>

	<button id="startBtn" onclick="startRecording(this);">시작</button>
	<button id="endBtn" onclick="stopRecording(this);" disabled>종료</button>

	<h3>録音ファイル</h3>
	<ul id="recordingslist"></ul>

	<h3>ステータス</h3>
	<pre id="log"></pre>
	
	<input type="hidden" id="streamVoice" name="voice">
	

	<form action="/www/transcript/streamOnMic" method="post" id="sendVoice"
		enctype="multipart/form-data"> 
		<input type="submit" value="가즈아">
	</form>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script>
		function __log(e, data) {
			log.innerHTML += e + " " + (data || '') + '\n';
		}

		var audio_context;
		var recorder;

		function startUserMedia(stream) {
			var input = audio_context.createMediaStreamSource(stream);
			__log('Media stream created.');

			recorder = new Recorder(input);
			__log('Recorder initialised.');
		}

		function startRecording(button) {
			recorder && recorder.record();
			button.disabled = true;
			button.nextElementSibling.disabled = false;
			__log('Recording...');
		}

		function stopRecording(button) {
			recorder && recorder.stop();
			button.disabled = true;
			button.previousElementSibling.disabled = false;
			__log('Stopped recording.');

			createDownloadLink();
			recorder.clear();
		}

		function createDownloadLink() {
			recorder && recorder.exportWAV(function(blob) {
				//var contents_num = 223;
				var contents_num = $('#contents_num').val();
				var start = $('#start').val();
				var dur = $('#dur').val();
				
				//alert(contents_num + " "+ start +" "+dur);
				
				var reader = new FileReader();
				var base64data;
				
				reader.readAsDataURL(blob);
				reader.onloadend = function(fileLoadedEvent) {
					base64data = reader.result;
					//alert(reader.result);
					var fd = new FormData();
					fd.append('file', base64data);
					fd.append("contents_num",contents_num);
					fd.append("start",start);
					fd.append("dur",dur);
					
					$.ajax({			
						type : 'POST',
						url : 'transcript/streamOnMic',
						data : fd,
						processData : false,
						contentType : false,
						success:function(data){	
							console.log(data);
						},
						error: function(e){			
							console.log(e);
						}
					})/* .done(function(data) {
						console.log(data);
					}); */
					
				}

			});
		}

		window.onload = function init() {
			try {
				window.AudioContext = window.AudioContext
						|| window.webkitAudioContext;
				navigator.getUserMedia = navigator.getUserMedia
						|| navigator.webkitGetUserMedia;
				window.URL = window.URL || window.webkitURL;

				audio_context = new AudioContext;
				__log('Audio context set up.');
				__log('navigator.getUserMedia '
						+ (navigator.getUserMedia ? 'available.'
								: 'not present!'));
			} catch (e) {
				alert('No web audio support in this browser!');
			}

			navigator.getUserMedia({
				audio : true
			}, startUserMedia, function(e) {
				__log('No live audio input: ' + e);
			});
		};

		var recLength = 0, recBuffers = [], sampleRate = undefined, numChannels = undefined;

		self.onmessage = function(e) {
			switch (e.data.command) {
			case 'init':
				init(e.data.config);
				break;
			case 'record':
				record(e.data.buffer);
				break;
			case 'exportWAV':
				exportWAV(e.data.type);
				break;
			case 'getBuffer':
				getBuffer();
				break;
			case 'clear':
				clear();
				break;
			}
		};
	</script>

	<script src="<c:url value="/resources/js/recorder.js" />"></script>
</body>
</html>