/**
 * 
 */

(function(e, p) {
	var m = location.href.match(/platform=(win8|win|mac|linux|cros)/);
	e.id = (m && m[1])
			|| (p.indexOf('Windows NT 6.2') > -1 ? 'win8' : p
					.indexOf('Windows') > -1 ? 'win'
					: p.indexOf('Mac') > -1 ? 'mac'
							: p.indexOf('CrOS') > -1 ? 'cros' : 'linux');
	e.className = e.className.replace(/\bno-js\b/, 'js');
})(document.documentElement, window.navigator.userAgent)

	function __log(e, data) {
		log.innerHTML += e + " " + (data || '') + '\n';
	}

	function Pitch(x,y){
		this.x = x;
		this.y = y;
	}
	
	
	function draw(arr,container,title){
		var pitchArr = [];
		
		for(var i = 0; i<arr.length; i++){
			pitchArr.push({x : i, y: arr[i]});
		}
		
		
		var chart = new CanvasJS.Chart(container,{
			animationEnabled : true,
		title : {
			title
		},
		axisY: {
			title : "",
			valueFormatString :"#0.",
			suffix:"pi",
			prefix : ""
		},
		data: [{
			type : "splineArea",
			color : "rgba(54,158,173,.7)",
			xValueFormatString: "##0",
			yValueFormatString: "$#,##0.##",
			makerSize : 5,
			dataPoints: pitchArr
		}]
		});
		
		chart.render();
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


	function createDownloadLink() {
		recorder && recorder.exportWAV(function(blob) {
			// var contents_num = 223;
			var contents_num = $('#contents_num').val();
			var start = $('#start').val();
			var dur = $('#dur').val();
			
			// alert(contents_num + " "+ start +" "+dur);
			
			var reader = new FileReader();
			var base64data;
			
			reader.readAsDataURL(blob);
			reader.onloadend = function(fileLoadedEvent) {
				base64data = reader.result;
				// alert(reader.result);
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
						draw(data.ytArr,ytPitch,'youtube');
						draw(data.memArr,memPitch,'member');
						
					},
					error: function(e){			
						console.log(e);
					}
				})/*
					 * .done(function(data) { console.log(data); });
					 */
				
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
	
	$('#start_button').on('click', function() {
		startRecording(this);
		startButton(event);
	});


// 무슨 스크립트지?
window.___gcfg = {
	lang : 'ja-JP'
};
(function() {
	var po = document.createElement('script');
	po.type = 'text/javascript';
	po.async = true;
	po.src = 'https://apis.google.com/js/plusone.js';
	var s = document.getElementsByTagName('script')[0];
	s.parentNode.insertBefore(po, s);
})();

//텍스트
var membertranscript = '';

// 구글 webSTT스크립트
var final_transcript = '';
var recognizing = false;
var ignore_onend;
var start_timestamp;
if (!('webkitSpeechRecognition' in window)) {
	upgrade();
} else {
	start_button.style.display = 'inline-block';
	var recognition = new webkitSpeechRecognition();
	recognition.continuous = true;
	recognition.interimResults = true;

	recognition.onstart = function() {
		recognizing = true;
		showInfo('info_speak_now');
		// start_img.src =
		// '/intl/en/chrome/assets/common/images/content/mic-animate.gif';
	};

	recognition.onerror = function(event) {
		if (event.error == 'no-speech') {
			// start_img.src =
			// '/intl/en/chrome/assets/common/images/content/mic.gif';
			showInfo('info_no_speech');
			ignore_onend = true;
		}
		if (event.error == 'audio-capture') {
			// start_img.src =
			// '/intl/en/chrome/assets/common/images/content/mic.gif';
			showInfo('info_no_microphone');
			ignore_onend = true;
		}
		if (event.error == 'not-allowed') {
			if (event.timeStamp - start_timestamp < 100) {
				showInfo('info_blocked');
			} else {
				showInfo('info_denied');
			}
			ignore_onend = true;
		}
	};

	recognition.onend = function() {
		recognizing = false;
		if (ignore_onend) {
			return;
		}
		// start_img.src =
		// '/intl/en/chrome/assets/common/images/content/mic.gif';
		if (!final_transcript) {
			showInfo('info_start');
			return;
		}
		showInfo('');
		if (window.getSelection) {
			window.getSelection().removeAllRanges();
			var range = document.createRange();
			range.selectNode(document.getElementById('final_span'));
			window.getSelection().addRange(range);
		}
		var tss = $('#final_span').html();
		//TODO : 테스트 비교 알고리즘 
		alert(tss);

	};
	


	recognition.onresult = function(event) {
		var interim_transcript = '';
		if (typeof (event.results) == 'undefined') {
			recognition.onend = null;
			recognition.stop();
			upgrade();
			return;
		}
		for (var i = event.resultIndex; i < event.results.length; ++i) {
			if (event.results[i].isFinal) {
				final_transcript += event.results[i][0].transcript;
			} else {
				interim_transcript += event.results[i][0].transcript;
			}
		}
		final_transcript = capitalize(final_transcript);
		final_span.innerHTML = linebreak(final_transcript);
		interim_span.innerHTML = linebreak(interim_transcript);
		if (final_transcript || interim_transcript) {
			showButtons('inline-block');
		}
	
	};
}

function upgrade() {
	start_button.style.visibility = 'hidden';
	showInfo('info_upgrade');
}

var two_line = /\n\n/g;
var one_line = /\n/g;
function linebreak(s) {
	return s.replace(two_line, '<p></p>').replace(one_line, '<br>');
}

var first_char = /\S/;
function capitalize(s) {
	return s.replace(first_char, function(m) {
		return m.toUpperCase();
	});
}

function startButton(event) {
	if (recognizing) {
		recognition.stop();
		return;
	}
	final_transcript = '';
	recognition.lang = "ja-JP"; // 여기가 언어 바꾸는 곳
	recognition.start();
	ignore_onend = false;
	final_span.innerHTML = '';
	interim_span.innerHTML = '';
	// start_img.src =
	// '/intl/en/chrome/assets/common/images/content/mic-slash.gif';
	showInfo('info_allow');
	showButtons('none');
	start_timestamp = event.timeStamp;
}

function showInfo(s) {
	if (s) {
		for (var child = info.firstChild; child; child = child.nextSibling) {
			if (child.style) {
				child.style.display = child.id == s ? 'inline' : 'none';
			}
		}
		info.style.visibility = 'visible';
	} else {
		info.style.visibility = 'hidden';
	}
}
var current_style;
function showButtons(style) {
	if (style == current_style) {
		return;
	}
	current_style = style;
}

function stopRecording(button) {
	recorder && recorder.stop();
	button.disabled = true;
	button.previousElementSibling.disabled = false;
	__log('Stopped recording.');
	
	recognition.stop();
	createDownloadLink();
	
	recorder.clear();
}