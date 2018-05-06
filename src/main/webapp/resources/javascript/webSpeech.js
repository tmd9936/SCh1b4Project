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
			var filename = $('#filename').val();
			
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
				fd.append("filename",filename);
				
				$.ajax({			
					type : 'POST',
					url : '../transcript/streamOnMic',
					data : fd,
					processData : false,
					contentType : false,
					success:function(data){
						console.log(data);
						draw(data.ytArr,ytPitch,'youtube');
						draw(data.memArr,memPitch,'member');
						//$('.perContainer').html(data.per);
						$('.pitchPercent').html(data.per);
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
			//alert('No web audio support in this browser!');
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
	
	$('#startBtn').on('click', function() {
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
	//start_button.style.display = 'inline-block';
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
		var tss = $('#final_span').text();
		var ytText = $('.Notice').text();
		
		//console.log("tss : "+tss);
		//console.log("ytText : "+ytText);

		//TODO 텍스트 비교 알고리즘
		//textCompares(tss,ytText);
		hyngteaso(tss,ytText,textCompares);
		
		recognizing = false;
		if (ignore_onend) {
			return;
		}
		
		
		
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

function textCompare(tts,ytText){
	var big = 0;
	var small = 0;
	
	if(tts.length >= ytText){
		big = tts.length;
		small = ytText.length;
	}else{
		small = tts.length;
		big = ytText.length;
	}
	var son = 1;
	var parent = 1;
	for(var i = 0; i<small; i++){
		if(tts[i]==ytText[i]){
			son++;
		}
		parent++;
	}
	parent = parent+(big-small);
	var textPer = ((son*1.0)/(parent*1.0))*100;
	alert('textPer : '+textPer.toFixed(3)+"%");
	
}


function textCompares(ttsList,ytList){
/*	var ytList = hyngteaso(ytText);
	var ttsList = hyngteaso(tts);
	var textPer = 0.0;
	
	
	
	console.log(ytList);
	console.log(ttsList);*/
	
	var son = 0;
	var parent = 0;
	if(ytList.length == 0 || ttsList.length == 0){
		alert('스트링 오류입니다.');
		return 0;
	}
	
	
	if(ytList.length == 1){
		if(ytList[0] == ttsList[0]){
			son++;
		}
		parent = ttsList.length-1
		textPer = ((son*1.0)/(parent*1.0))*100;
		return textPer;
	}else if(ttsList.length == 1){
		if(ytList[0] == ttsList[0]){
			son++;
		}
		parent = ytList.length-1
		textPer = ((son*1.0)/(parent*1.0))*100;
		return textPer;
	}
	if(ytList[0] == ttsList[0]){
		son++;
	}
	
	for(var i=1; i<ytList.length; i++){
		for(var j=1; j<ttsList.length; j++){
			if(ytList[i] == ttsList[j]){
				if(ytList[i-1] == ttsList[j-1]){
					son++;
				}
			}
		}
		parent++;
	}
	if(ytList.length <= ttsList.length){
		parent += ttsList.length- ytList.length+1;
	}else{
		parent += ytList.length-ttsList.length+1;
	}
	
	textPer = ((son*1.0)/(parent*1.0))*100;
	return textPer;
}

var speechdialog = document.querySelector('#percentDialog');
function hyngteaso(ytText,tts,textCompares){
	var ytList = new Array();
	var ttsList = new Array();
	
    $.ajax({
        type : "POST",
        url : "https://api.apigw.smt.docomo.ne.jp/gooLanguageAnalysis/v1/morph?APIKEY=6b612f392f7967397036713151474445533866346141694d7646516a6c366a2f5a5a576231615a36526836",
        ContentType : "application/json; charset=utf-8",
        dataType : 'json',
        data : {
           "request_id": num, //필수 아님
           "sentence": ytText,   //여기가 문자열
           "info_filter" : "form" //원문, 형태소포지션, 읽는 방법
           //,"pos_filter" : "名詞|連用詞|動詞活用語尾|動詞接尾辞|動詞語幹" 
        },
        success : function(obj){
        	//API로 받아온 분석결과는 2중 배열에 갇혀 있기 때문에 꺼내준다.
            $.each(obj.word_list,function(index,item){
            	$.each(item,function(index2,item2){
            		ytList.push(item2[0]);
                });
            });
            
            $.ajax({
                type : "POST",
                url : "https://api.apigw.smt.docomo.ne.jp/gooLanguageAnalysis/v1/morph?APIKEY=6b612f392f7967397036713151474445533866346141694d7646516a6c366a2f5a5a576231615a36526836",
                ContentType : "application/json; charset=utf-8",
                dataType : 'json',
                data : {
                   "request_id": num, //필수 아님
                   "sentence": tts,   //여기가 문자열
                   "info_filter" : "form" //원문, 형태소포지션, 읽는 방법
                   //,"pos_filter" : "名詞|連用詞|動詞活用語尾|動詞接尾辞|動詞語幹" 
                },
                success : function(obj){
                	//API로 받아온 분석결과는 2중 배열에 갇혀 있기 때문에 꺼내준다.
                    $.each(obj.word_list,function(index,item){
                    	$.each(item,function(index2,item2){
                    		ttsList.push(item2[0]);
                        });
                    });
                    var persent = textCompares(ytList,ttsList);
                    //alert(persent.toFixed(3));
                   $('.textPercent').html(persent.toFixed(3));
                   speechdialog.showModal();
                    
                },//success 끝
                error : function(){
                	console.log("실패");
                }//error끝
            });//ajax끝	
            
        },//success 끝
        error : function(){
        	console.log("실패");
        }//error끝
    });//ajax끝
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

$('#closeButton').on('click',function(){
	speechdialog.close();
});