/**
 * 
 */


//document.write("<script src='b.js'></script>");





var youTubePlayer;
var done = true;
var dur = 3000;
var correctCount = 10;
var ts_num = '';
var spTime = (100/allTime)/4; //seekPointTime 0.25초에 crub이 가는 시간 
var intervals = new Array();
var speakSpace = false;
var speakState = false;
var speakText = '';


var learnSpace = false;


$(function(){
	
	
	
	$('.script-bar').on('click',function(){
		speakState = true;
		$('#start').val($(this).attr('start'));
		$('#dur').val($(this).attr('dur'));
		speakText = $(this).attr('text');
		
		if(speakState && speakSpace){
			$('.Notice').text(speakText);
			$('.startEndBtn').css('visibility','visible');
		}
		
		if(intervals.length >0){
			$.each(intervals,function(index,inter){
				clearInterval(inter);
			});
		}
		$('.script-bar').attr('click','false');
		$(this).attr('click','true');
		
		//crub위치 바꾸기
		var left = $(this).attr('left');
		$('.seek-crub').css('left',left+'%');
		ts_num = $(this).attr('num');
		num = ts_num;
		
		var crubsp = parseFloat(left);
		var endTime = ((parseFloat($(this).attr('start'))+parseFloat($(this).attr('dur')))/allTime*100);
		var ss = setInterval(function(){
			$('.seek-crub').css('left',crubsp+'%');
			//alert((parsefloat($('.seek-crub').css('left').replace('%',''))+spTime));
			crubsp += spTime;
			console.log(intervals.length);
			if(num != ts_num||crubsp>endTime){
				clearInterval(ss);
			}
			
		},250);
		
		intervals.push(ss);
		
		dur = parseInt(($(this).attr('dur')))*1090;
		done = false;
		
		var start = $(this).attr('start'); //유튜브 시작 초
		youTubePlayer.seekTo(start,true);// 유튜브 시작위치
		youTubePlayer.playVideo(); //유튜브 재생
		
		setTimeout(changeSeekerColor,dur,this);
		
	});
});

function changeSeekerColor(bar){
	if($(bar).attr('click')=='true')
		$(bar).css('background-color', 'red');
}

function moveCrub(num){
	
}


function pauseVideo(num){
	if(ts_num == num)
		youTubePlayer.pauseVideo();
}

function stopVideo() {
	youTubePlayer.stopVideo();
 }


function onYouTubeIframeAPIReady() {
    'use strict';

    var inputVideoId = document.getElementById('YouTube-video-id');
    var videoId = inputVideoId.value;
    var suggestedQuality = 'tiny';
    var width = 480;
    var height = 360;
    var youTubePlayerVolumeItemId = 'YouTube-player-volume';


    function onError(event) {
        youTubePlayer.personalPlayer.errors.push(event.data);
    }


    function onReady(event) {
        var player = event.target;

        player.loadVideoById({suggestedQuality: suggestedQuality,
                              videoId: videoId
                             });
        player.pauseVideo();
        //youTubePlayerDisplayFixedInfos();
    }


    function onStateChange(event) {
        var volume = Math.round(event.target.getVolume());
        var volumeItem = document.getElementById(youTubePlayerVolumeItemId);

        if (volumeItem && (Math.round(volumeItem.value) != volume)) {
            volumeItem.value = volume;
        }
        if (event.data == YT.PlayerState.PLAYING && !done) {
        	done = true;
            setTimeout(pauseVideo, dur,ts_num);
        }
        if (event.data == YT.PlayerState.PLAYING && !done) {
            done = true;
             setTimeout(pauseVideo, dur7,num7);
             youTubePlayer.pauseVideo();
        }
    }


    youTubePlayer = new YT.Player('YouTube-player',
                                  {videoId: videoId,
                                   height: height,
                                   width: width,
                                   playerVars: {
                                	   			'wmode' : "transparent",
                                	   			'autohide': 0,
                                                'cc_load_policy': 0,
                                                'controls': 2,
                                                'disablekb': 1,
                                                'iv_load_policy': 3,
                                                'modestbranding': 1,
                                                'rel': 0,
                                                'controls' : 0,
                                                'showinfo': 0,
                                                'start': 3
                                               },
                                   events: {'onError': onError,
                                            'onReady': onReady,
                                            'onStateChange': onStateChange
                                           }
                                  });

    // Add private data to the YouTube object
    youTubePlayer.personalPlayer = {'currentTimeSliding': false,
                                    'errors': []};

    
}


/**
 * :return: true if the player is active, else false
 */
function youTubePlayerActive() {
    'use strict';

    
    
    
    return youTubePlayer && youTubePlayer.hasOwnProperty('getPlayerState');
}


/**
 * Get videoId from the #YouTube-video-id HTML item value,
 * load this video, pause it
 * and show new infos.
 */
function youTubePlayerChangeVideoId() {
    'use strict';

    var inputVideoId = document.getElementById('YouTube-video-id');
    var videoId = inputVideoId.value;

    youTubePlayer.cueVideoById({suggestedQuality: 'tiny',
                                videoId: videoId
                               });
    youTubePlayer.pauseVideo();
    youTubePlayerDisplayFixedInfos();
}






/**
 * Display
 *   some video informations to #YouTube-player-infos,
 *   errors to #YouTube-player-errors
 *   and set progress bar #YouTube-player-progress.
 */
/* 
 function youTubePlayerDisplayInfos() {
    'use strict';

    if ((this.nbCalls === undefined) || (this.nbCalls >= 3)) {
        this.nbCalls = 0;
    }
    else {
        ++this.nbCalls;
    }

    var indicatorDisplay = '<span id="indicator-display" title="timing of informations refreshing">' + ['|', '/', String.fromCharCode(8212), '\\'][this.nbCalls] + '</span>';

    if (youTubePlayerActive()) {
        var state = youTubePlayer.getPlayerState();
		
        var current = youTubePlayer.getCurrentTime();
        var duration = youTubePlayer.getDuration();
        var currentPercent = (current && duration
                              ? current*100/duration
                              : 0);

        var fraction = (youTubePlayer.hasOwnProperty('getVideoLoadedFraction')
                        ? youTubePlayer.getVideoLoadedFraction()
                        : 0);

        var url = youTubePlayer.getVideoUrl();

        if (!current) {
            current = 0;
        }
        if (!duration) {
            duration = 0;
        }

        var volume = youTubePlayer.getVolume();

        if (!youTubePlayer.personalPlayer.currentTimeSliding) {
            document.getElementById('YouTube-player-progress').value = currentPercent;
        }

    

    }
    else {
    }
} 

 */
 
/**
 * Pause.
 */
function youTubePlayerPause() {
    'use strict';

    if (youTubePlayerActive()) {
        youTubePlayer.pauseVideo();
    }
}


/**
 * Play.
 */
function youTubePlayerPlay() {
    'use strict';

    if (youTubePlayerActive()) {
        youTubePlayer.playVideo();
    }
}


/**
 * Return the state decription corresponding of the state value.
 * If this value is incorrect, then return unknow.
 *
 * See values:
 * https://developers.google.com/youtube/iframe_api_reference#Playback_status
 *
 * :param number: any
 * :param unknow: any
 *
 * :return: 'unstarted', 'ended', 'playing', 'paused', 'buffering', 'video cued' or unknow
 */
function youTubePlayerStateValueToDescription(state, unknow) {
    'use strict';

    var STATES = {'-1': 'unstarted',   // YT.PlayerState.
                  '0': 'ended',        // YT.PlayerState.ENDED
                  '1': 'playing',      // YT.PlayerState.PLAYING
                  '2': 'paused',       // YT.PlayerState.PAUSED
                  '3': 'buffering',    // YT.PlayerState.BUFFERING
                  '5': 'video cued'};  // YT.PlayerState.CUED

    return (state in STATES
            ? STATES[state]
            : unknow);
}


/**
 * Stop.
 */
function youTubePlayerStop() {
    'use strict';

    if (youTubePlayerActive()) {
        youTubePlayer.stopVideo();
        youTubePlayer.clearVideo();
    }
}




/**
 * Main
 */
(function () {
    'use strict';

    function init() {
        // Load YouTube library
        var tag = document.createElement('script');

        tag.src = 'https://www.youtube.com/iframe_api';

        var first_script_tag = document.getElementsByTagName('script')[0];

        first_script_tag.parentNode.insertBefore(tag, first_script_tag);
        

        // Set timer to display infos
       // setInterval(youTubePlayerDisplayInfos, 1000);
    }


    if (window.addEventListener) {
        window.addEventListener('load', init);
    } else if (window.attachEvent) {
        window.attachEvent('onload', init);
    }
}());


/* 화면 클릭시 Div 생성  */


function GoSpeakTheLine(){
	
	if(!speakSpace){
		
		var str = '<div class="Notice typo-styles__demo mdl-typography--headline">';  
			str +=  "動画から SPEAKする文章をお選びください:";
			str += '</div>';
			/*str +=' <button id="start_button">시작</button>';
			str += '<button id="endBtn" onclick="stopRecording(this);" disabled>종료</button>';
			str += '<div class="browser-landing" id="main"><div class="compact marquee"><div id="results">'
			str	+= '<span class="final" id="final_span"></span> <span class="interim" id="interim_span"></span>'
			str += '</div></div></div>';
			str + '<div id="ytPitch" style="height: 250px; width: 40%;" class="pitchContainer"></div>';
			str + '<div id="memPitch" style="height: 250px; width: 40%;" class="pitchContainer"></div>';*/
			
			str += '<style type="text/css">';
			str += '.secondView{'
			str += 'width: 598px;';
			str += 'height: 700px;';
			str += 'border:1px solid;';
			str +=	'}';
			str	+= '</style>';
			
		$('#divNewView').removeAttr('style');	
		$('#divNewView').html('');	
		$('#divNewView').html(str);
		$('.speachView').css('visibility','visible');
		speakSpace = true;
		learnSpace=false;
	}else{
		$('.divNewView').html('');
		$('.startEndBtn').css('visibility','hidden');
		$('.speachView').css('visibility','hidden');
		speakSpace = false;
	}
	
}


function LearnTheWords(tslist){
	var contents_num;
	contents_num = document.getElementById('contents_num').value;
	var div = document.getElementById("divNewView");
	div.style.overflow = "scroll";
	div.style.width="598px";
	div.style.height="700px";
	div.style.border="1px solid";
	if(!learnSpace){
	var str3 = '<div class="Notice">';
	str3 += '<input type="button" class="mdl-button mdl-js-button mdl-button--primary" value="問題生成" onclick="javascript:getTime('+contents_num+')">';
	str3 += '<input class="mdl-button mdl-js-button mdl-button--primary" type="button" value="<<" onclick="javascript:playsound2(0)">';
	str3 += '<input class="mdl-button mdl-js-button mdl-button--primary" type="button" value=">>" onclick="javascript:playsound2(1)">';
	str3 += '<hr>';
	str3 += '</div>';
	//$('.divNewView').html(str3);
	//div.innerHTML  = str3;
	var str2='';
	str2 += str3;
		for (var i = 0; i < tslist.length; i++) {
			var num  = tslist[i].ts_num;
			var text = tslist[i].ts_text;
			var start = tslist[i].ts_start-1;
			var dur = tslist[i].ts_dur+1;
			var str='';
			str = '<input class="mdl-button mdl-js-button mdl-button--primary" type="button" value="再生" onclick="javascript:playsound('+start+','+dur+')" name="a'+num+'">'
			str += ' <input type="hidden" id="answers'+num+'">';
			str += ' <input type="hidden" id="kana'+num+'">';
			str += ' <div id="outputDiv'+num+'" style="display: inline;" ></div>';
			
			str += '<div id="list'+num+'" style="display: none;"></div>';
			str += '<div style="display: none;" id="inputAnswer'+num+'">正解を入力 :&nbsp;&nbsp;&nbsp;<input id="yourText'+num+'" type="text"   style="border-left: none; border-right: none; border-top: none; "></div><br>';
			this.test(num,text);
			str2 += str;
		}
		
	
		$('.divNewView').html(str2);
		div.style.display="block";
		learnSpace=true;
		$('.speachView').css('visibility','hidden');
		$('.startEndBtn').css('visibility','hidden');
		speakSpace = false;
	}else{
		div.innerHTML='';
		div.style.display="none";
		learnSpace=false;
	}
	
}
function makeLearnWord(ts){
	$('.Notice').remove();
	var str4 = '<div class="Notice">';
	str4 += '<input type="button" class="mdl-button mdl-js-button mdl-button--primary" value="問題生成" onclick="javascript:getTime('+contents_num+')">';
	str4 += '<input class="mdl-button mdl-js-button mdl-button--primary" type="button" value="<<" onclick="javascript:playsound2(0)">';
	str4 += '<input class="mdl-button mdl-js-button mdl-button--primary" type="button" value=">>" onclick="javascript:playsound2(1)">';
	str4 += '<hr>';
	str4 += '</div>';
	//$('.divNewView').html(str3);
	//div.innerHTML  = str3;
	var str2='';
	str2 += str4;
	var str3='';
			var num  = ts.ts_num;
			var text = ts.ts_text;
			var start = ts.ts_start-1;
			var dur = ts.ts_dur+1;
			var str='';
			str = '<input class="mdl-button mdl-js-button mdl-button--primary" type="button" value="再生" onclick="javascript:playsound('+start+','+dur+')" name="a'+num+'">'
			str += ' <input type="hidden" id="answers'+num+'">';
			str += ' <input type="hidden" id="kana'+num+'">';
			str += ' <div id="outputDiv'+num+'" style="display: inline;" ></div>';
			
			str += '<div id="list'+num+'" style="display: none;"></div>';
			str += '<div style="display: none;" id="inputAnswer'+num+'">正解を入力 :&nbsp;&nbsp;&nbsp;<input id="yourText'+num+'" type="text"   style="border-left: none; border-right: none; border-top: none; "></div><br>';
			this.test(num,text);
			str2 += str;
			str3 = $('.divNewView').html()
			str2 += str3;
			$('.divNewView').html(str2);
}

function getTime(contents_num){
	var currentTime = youTubePlayer.getCurrentTime();
	$(document).ready(function(){
	$.ajax({
         type : "POST",
         url : "../transcript/getTt",
         ContentType : "application/json; charset=utf-8",
         data : {
        	 "contents_num" : contents_num,
        	 "currentTime" : currentTime
         },
         success : function(ts){
        	 console.log(JSON.stringify(ts));
        	 makeLearnWord(ts);
         },
         error : function(){
        	 //alert('fail');
         }
	 });
	})
}
function playsound(start7,dur7){
	done = false;
	dur = dur7*2000;
	youTubePlayer.seekTo(start7,true);// 유튜브 시작위치
	youTubePlayer.playVideo(); //유튜브 재생
}

function playsound2(num){
	done = false;
	var currentTime;
	switch(num){
	case 1:
		currentTime = youTubePlayer.getCurrentTime()+3;
		break;
	case 0:
		currentTime = youTubePlayer.getCurrentTime()-3;
		break;
	}
	youTubePlayer.seekTo(currentTime,true);// 유튜브 시작위치
	youTubePlayer.playVideo(); //유튜브 재생
}


function test(num,text){
	var flag;
	var oneWordAnswer=[];
    var object;
    var compare=[];
    var kana=[];
    var hinshi=[];
    compare.push(text);
    kana.push(text);
    hinshi.push(text);
    //문장을 API를 이용해 분해,분석 하자.
    $(document).ready(function(){
        $.ajax({
            type : "POST",
            url : "https://api.apigw.smt.docomo.ne.jp/gooLanguageAnalysis/v1/morph?APIKEY=6b612f392f7967397036713151474445533866346141694d7646516a6c366a2f5a5a576231615a36526836",
            ContentType : "application/json; charset=utf-8",
            dataType : 'json',
            data : {
               "request_id": num, //필수 아님
               "sentence": text,	//여기가 문자열
               "info_filter" : "form|pos|read", //원문, 형태소포지션, 읽는 방법
               "pos_filter" : "名詞|連用詞|動詞活用語尾|動詞接尾辞|動詞語幹" 
              },
              
            //성공한다면
            success : function(obj){
              var list = '';
              var str = obj.word_list[0];
              var c=0;
              
              //API로 받아온 분석결과는 2중 배열에 갇혀 있기 때문에 꺼내준다.
              $.each(str,function(index,item){
                //list += '인덱스: '+index+'<br>';
            	  var oneWord='';
            	  var oneWord2=''; var oneWord3=''; var oneWord4='';
		              $.each(item,function(index2,item2){
		                switch(c){
		               case 0:
		                 list += '原文: '+item2;
		                 //list += item2;
		                 compare.push(item2);
		                 if(item2.length>=2){
		                 oneWord2 = item2+':';
		                 }
		                 c++;
		                 break;
	    	           case 1:
	        	          list += ' 品詞: '+item2;
	        	          hinshi.push(item2);
	        	          console.log(item2);
	           		       //list += item2;
	        	          if(oneWord2!=''){
	        	          oneWord3 = item2+':';
	        	          }
	        	          c++;
		                 break;
		               case 2:
		                 list += ' 読み: '+item2+'<br>';
		                 kana.push(item2);
		                 //list += item2;
		                 if(oneWord2!=''){
		                 oneWord4 = item2;
		                 }
		                 c=0;
		                 break;
		               }
		             });
		              if(oneWord2!=''){
		            	  oneWord =oneWord2+""+oneWord3+""+oneWord4;
		            	  oneWordAnswer.push(oneWord);
		            	  console.log("저장 직후의 상태 :"+oneWordAnswer);
		              }
		         });
		         $('#list'+num).text(oneWordAnswer);
		         
		         //히든 칸에 답을 입력한다
		         var answers = document.getElementById('answers'+num);
		        	answers.value = compare;
		         var kanas = document.getElementById('kana'+num);
		         	kanas.value = kana;
		        	
		        	
		          //문제가 만들어지는 공간마다 답 입력칸을 만들어 준다.
	          	for (var i = 1; i < compare.length; i++) {
	          		//너무 짧은 건 넘기고
	        	if(compare[i].length<2)continue;
	          		//일치하는 부분을 답 입력 칸으로 변-환
	        	flag++;
	        	var holder = compare[i].length;
	        	
	          	    text = text.replace(compare[i],'<div style="display: inline;" class="mdl-textfield mdl-js-textfield is-upgraded" data-upgraded=",MaterialTextfield"><input type="text" id="'+num+''+i+'" onkeypress="answer('+num+','+i+')"; class="mdl-textfield__input"; placeholder="'+holder+','+hinshi[i]+'"style="border-left: none; border-right: none; border-top: none; width:90px; display: inline; font-size: 15px; text-align : center;"></div>'+' ');
	          	    console.log('compare['+i+'] :'+compare[i]);
	          	}
	          	compare.push(flag);
	          	//문제가 만들어지지 않았다면
	          	if(!text.includes('<input type="text"') ){
	          		var failed = document.getElementsByName("a"+num);
	          		for (var i = 0; i < failed.length; i++) {
						failed[i].style.display="none";
					}
	          		return;
	          	}
	          	
	          	
    $('#outputDiv'+num).html(text);
    
    return true;
  },
  error : function(){
    console.log("실패");
  }
});
    })
  }

//유저가 답을 입력한다. 여기에는 정답과, 문제 칸을 특정할 수 있는 조합을 불러와야 한다.
function answer(num,i){
	var contents_num;
	$(document).ready(function(){
	contents_num = $('#contents_num').val();
	console.log('contents_num.value'+contents_num);
	})
	
 		//엔터를 입력했다면
	if(event.keyCode==13){
		
		var yourAnswer = document.getElementById(num+''+i);
		var correctAnswer = document.getElementById('answers'+num);
		var correctKana = document.getElementById('kana'+num);
		var compare = [];
		var kana = [];
		compare = correctAnswer.value.split(",");
		kana = correctKana.value.split(",");
		console.log("compare["+i+"] :"+compare[i]);
		console.log("flag :"+compare.length);
		console.log("kana["+i+"] :"+kana[i]);
		var x = document.createElement("INPUT");
		
		//답이 일치하면
		if(yourAnswer.value == compare[i] || yourAnswer.value == kana[i]){
			yourAnswer.value = compare[i];
			//yourAnswer.style.textAlign = "center";
			yourAnswer.style.color = "#ff0000"; 
			yourAnswer.disabled=true;
			if(document.getElementById('view'+num)!=null)
			document.getElementById('view'+num).style.display="none";
			console.log('1번'+'view'+num);
			console.log('2번'+'view'+(num+1));
			var count = correctCount;
			correctCount = count-1; 
			if(correctCount<=0){
				alert('学習を完了しました\n10ポイントアップ!!');
				correctCount = 10;
			}
			lUP();	
			//틀리면
		}else{
			if(document.getElementById('view'+num)== null){
				x.setAttribute("id","view"+num);
				x.setAttribute("type", "button");
				x.setAttribute("value","情報確認");
				x.setAttribute("class","mdl-button mdl-js-button mdl-button--accent");
				x.setAttribute("data-value",num);
				x.setAttribute('onclick','viewInfo(\''+compare+'\','+num+','+contents_num+');'+onclick);
				document.getElementById('outputDiv'+num).appendChild(x);
			}
			document.getElementById('view'+num).style.display="block";
		}
		/*
		x.onclick = function(){
			console.log("data-value :"+this.getAttribute("data-value"));
			console.log("compare"+compare);
			var mynum = this.dataset.value;
			console.log("mynum"+mynum);
			viewInfo(compare,mynum,contents_num);
			};
		 */
	}
}
//1포인트 업
function lUP(){
	$(document).ready(function(){
		$.ajax({
 	            type : "POST",
 	            url : "../member/lUP",
 	            success : function(){
 	            },
 	            error : function(){
 	            	console.log("ポイントアップエラー");
 	            },
 	        });
	})
}
 	//다이얼로그 띄워서 정보 보기
 	function viewInfo(words,ts_num,contents_num){
 		console.log("viewInfo"+ts_num);
 		//words, explanation 가져옴 div 만들어서 밑에 붙임 (거기에 다이얼로그)
 		console.log("words:"+words);
 		var div = document.createElement('div');
 		document.getElementById('outputDiv'+ts_num).appendChild(div);
 		var explanation = document.getElementById('list'+ts_num).innerHTML;
 		console.log("explanation :"+explanation);
 		
 		// 다이얼로그
 		//데이터 밸류를 가져와서
 		var temp1 = words.split(",");
 		var sentence = temp1[0];
 		var read = sentence;
 		//var sentence = words[0];
 		//var sentence = document.getElementById('answers'+ts_num).value[0];
 		var answers=[];
 		for(var i=1;i<temp1.length;i++){
 			if(temp1[i].length<2)continue;
 			answers.push(temp1[i]);
 		}
 		console.log("sentence"+sentence);
 		console.log("answers"+answers);
 		var color=sentence;
 		for(var j=0;j<answers.length;j++){
 			var str = '<span  style="color: red; cursor:pointer" onclick="javascript:checkWord('+contents_num+','+ts_num+','+j+')">'+answers[j]+'</span>';
 			color = color.replace(answers[j],str);
 		}
 		console.log("read"+read);
 		//다이얼로그 내부 구문을 완성하고
 		var str = '<dialog class="mdl-dialog" id="viewInho">';
 		str += '<h4 class="mdl-dialog__title"> Study sentence </h4>';
 		str += '<input class="mdl-button mdl-js-button mdl-button--primary" type="button" value="再生" onclick="javascript:suzukikun('+ts_num+')">';
 		str += ' <input type="button" class="mdl-button mdl-js-button mdl-button--primary" id="korean" value="ハングル翻訳" onclick="javascript:wordDetail(\''+temp1+'\')">';
 		str += '<br><div id="transhidden" style="none"></div>'
 		str += ' <div class="mdl-dialog__content"></div>';
 		str += '<div id="saveVoca" style="display: none"></div>';
 		str += '<div class=	"mdl-dialog__underunder" style="display: none"></div>'
 		str += ' <div class="mdl-dialog__actions"><button type="button" class="mdl-button" style="font-weight:bold; font-size:12pt;">確認</button>';
 		//str += ' <button type="button" class="mdl-button close">아니요</button>
 		str += '</div>';
 		str += '</dialog>';
 		
 		//넣은 다음에 출력
// 		var x = document.getElementsByClassName('.mdl-dialog__content');
// 		console.log("x:"+x.length);
// 		x.innerHTML(color); 
 		div.innerHTML = str;
 		$(document).ready(function(){
 		 $('.mdl-dialog__content').html(color);
 		 
 		 })
 		var dialog = document.getElementById('viewInho');
 		 if (! dialog.showModal) {
	    	  dialogPolyfill.registerDialog(dialog);
	      }
 		    dialog.showModal(); 	
 		 dialog.querySelector('.mdl-dialog__actions').addEventListener('click', function() {
 			$('.mdl-dialog__underunder').html('');
 			$('#saveVoca').html('');
 			div.innerHTML="";
 		      dialog.close();
 		     checkMP3();
 		    });
	         // window.open("../transcript/wordDetail?words="+words+"&ts_num="+ts_num+"&contents_num="+contents_num+"&explanation="+explanation, "wordDetail", "width=578, height=215, toolbar=no, menubar=no, scrollbars=no, location=no, status=no, resizable=no" );
 	}  
 	
 	//한글 번역
 	function wordDetail(word){
 		$(document).ready(function(){
 			if($('#korean').val()=='戻す'){
 				$('#translate').hide();
 				//$('.mdl-dialog__content').show();
            	$('.mdl-dialog__underunder').show();
            	$('#saveVoca').show();
 				$('#korean').val('ハングル翻訳');
 				$("#transhidden").hide();
 				return;
 			}
 			
 			var tong = word.split(",");
 			console.log("tong"+tong);
 			var words = tong[0];
 			console.log('words:'+words);
 			
			 $.ajax({
	            type : "POST",
	            url : "../transcript/wordDetail",
	           ContentType : "application/json; charset=utf-8",
	            //dataType : "json",
	            data : {
	               "words" : words, 
	              },
	            success : function(sentence){
	            	var Ca = /\+/g;
	            	var ajaxName=decodeURIComponent( sentence.replace(Ca, " ") );
	            	//var ajaxName = decodeURIComponent( sentence );
	            	console.log("ajaxname"+ajaxName);
	            	var str = '<br><div id="translate"></div>';
	            	$("#transhidden").show();
	            	$("#transhidden").html(str);
	            	$('#translate').html(ajaxName);
	            	$('.mdl-dialog__underunder').append(str);
	            	//$('.mdl-dialog__content').hide();
	            	$('.mdl-dialog__underunder').hide();
	            	$('#saveVoca').hide();
	            	$('#korean').val("戻す");
	            	
	            	$(document).ready(function(){
	            		$('.mdl-button mdl-js-button mdl-button--primary')
	            	})
	            },
	            error : function(){
	            	console.log("fail");
	            },
	        });
		 })
 	}
 	
 	//단어를 체크
 	function checkWord(contents_num,ts_num,j){
 		$(document).ready(function(){
 			console.log("j :"+j);
 			var list= $('#list'+ts_num).text();
 			console.log("list :"+list);
 			var compare=[];
 			compare = list.split(",");
 			console.log("compare :"+compare);
 			var kanji; var part; var mean;
 			var sentence = compare[j].split(":");
 			console.log("sentence :"+sentence);
 			kanji = sentence[0];
 			part = sentence[1];
 			mean = sentence[2];
 			console.log("kanji :"+kanji);console.log("part :"+part);console.log("mean :"+mean);
 			$('.mdl-dialog__underunder').html("原文 : "+kanji+" 品詞 : "+part+" 意味 : "+mean);
 			$('.mdl-dialog__underunder').show();

 			$('#saveVoca').html('<input type="button" id ="saveToVoca"class="mdl-button mdl-js-button mdl-button--primary" value="セーブ" onclick="javascript:saveVoca('+ts_num+','+contents_num+',\''+sentence+'\')">');

 			$('#saveVoca').show();
 			// $('.mdl-dialog__underContent').show();
 			 
 			
 			/*단어장 저장 시작*/
 			$('#saveToVoca').on('click', function(){
 				var contents_num = $('#contents_num').val();
 				
 					console.log("ts:"+kanji);
 					console.log("ts:"+part);
 					console.log("ts:"+mean);
 					console.log("ts:"+contents_num);
 				$.ajax({
 				
 					url : "insertVoca",
 					type : "POST",
 					contentType : "application/json; charset=utf-8",
 					data : JSON.stringify({
 						contents_num : contents_num,
 						kanji :kanji,
 						part : part,
 						mean : mean
 					}),
 					success : function(){
 						console.log("単語帳にセーブしました");
 						
 						
 						
 					},
 					error : function(err){
 						
 						console.log(err);
 					}


 					
 					
 				})
 				
 				
 				
 				
 			});/*단어장 저장 끝*/
 			
 			
 		})
 		
 	 		 
 	}

 	function saveVoca(ts_num,contents_num,sentence){
 		//단어 저장 ajax로
 	}
	//음성합성
 	function suzukikun(ts_num){

 		/*
 		console.log("sentence옴:"+sentence);
 		location.href="../transcript/suzuki?sentence="+sentence+"&contents_num="+contents_num;
 		*/
 		console.log("contents_num in suzukikun"+contents_num);
 		console.log("ts_num in suzukikun"+ts_num);
 		$(document).ready(function(){
 			var contents_num = $('#contents_num').val();
 			
 			 $.ajax({
 	            type : "POST",
 	            url : "../transcript/suzuki",
 	           ContentType : "application/json; charset=utf-8",
 	           dataType : 'json',
 	            data : {
 	               "contents_num": contents_num,	//여기가 문자열
 	               "ts_num" : ts_num 
 	              },
 	            success : function(){
 	            	checkMP3();
 	            	
 	            },
 	            error : function(){
 	            	console.log("fail");
 	            },
 	        });
 		 })
 		
 	}
 	function checkMP3(){
 		$(document).ready(function(){
 			$.ajax({
 	            type : "POST",
 	            url : "../transcript/checkMP3",
 	            success : function(){
 	            	console.log("success");
 	            },
 	            error : function(){
 	            	console.log("fail");
 	            }
 	        });
 		})
 	}
 

 	 var VocabState = false;
 	/*단어장 리스트 시작*/
 	function Vocab(){
 		var div = document.getElementById("divNewView");
 		var contents_num = $('#contents_num').val();
 		
 	 		if(!VocabState){
 	 			
 	 				
 	 	 		var str = '';
 	 	 		initAllVoca();
 	 			
 	 		}else{
 	 			VocabState = false;
 	 			str = '';
 	 			div.innerHTML = str;
 	 			
 	 		}
 		
 	 		
 	 		function initAllVoca(){
 	 			$.ajax({
 	 		 		
 	 	 			url : "selectAllVoca",
 	 	 			type : "GET",
 	 	 			data:{
 	 					contents_num : contents_num
 	 				},
 	 				dataType : "json",
 	 				success: function(vocaList){
 	 					
 	 	 	            
 	 	 	        str +=  '<table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp">'
 	 	 	        str  += '<tr>';
 	 	 	        str  += '<th>番号</th>';
 	 	 	        str  += '<th>漢字</th>';
 	 	 	        str  += '<th>品詞</th>';
 	 	 	        str  += '<th>意味</th>';
 	 	 	        str  += '<th>日付</th>';
 	 	 	        str  += '</tr>';    
 	 	 	        
 	 	 	             $.each(vocaList ,function(index, item){
 	 	 	            	 	index = index+1;
 	 	 	           str += '<tr>'; 	 	
 	 	 	           str += '<td>'+index+'</td>';
 	 	 	           str += '<td>'+item.kanji+'</td>';
 	 	 	           str += '<td>'+item.part+'</td>';
 	 	 	           str += '<td>'+item.mean+'</td>';
 	 	 	           str += '<td>'+item.vocadate+'</td>';
 	 	 	           str += '<td><input type="button" value="削除" class="deleteVoca mdl-button mdl-js-button mdl-button--raised mdl-button--colored" data_num="'+item.voca_num+'"></td>';
 	 	 	           str  += '</tr>';    	 
 	 	 	             	})
 	 	 	             	
 	 	 	           str += '</table>';  	
 	 	 	             	div.innerHTML = str; 	
 	 	 	             	VocabState = true;
 	 	 	                
 	 	 	               /*   삭제 시작*/
 	 	 			   		$('.deleteVoca').on('click', function(){
 	 	 			   			
 	 	 			   			var voca_num = $(this).attr('data_num');
 	 	 			   			
 	 	 			   			$.ajax({
 	 	 			   				
 	 	 			   				url : "deleteVoca",
 	 	 			   				type : "POST",
 	 	 			   				data : {
 	 	 			   				voca_num : voca_num
 	 	 			   				},
 	 	 			   				success : function(){
 	 	 			   					console.log("단어 삭제 성공");
 	 	 			   					str = '';
 	 	 			   					div.innerHTML = str; 	
 	 	 			   					
 	 	 			   					initAllVoca();
 	 	 			   				    
 	 	 			   				},
 	 	 			   				
 	 	 			   				error : function(error){
 	 	 			   					console.log(error);
 	 	 			   				}
 	 	 			   				
 	 	 			   			});
 	 	 			   			
 	 	 			   		});
 	 	 	                
 	 	 	                
 	 	 	                
 	 	 	        	},
 	 	 	        	
 	 	 	        error : function(error){
 	 	 	        	console.log(error);
 	 	 	        }	
 	 	 	        	
 	 	 	        });
 	 		}
 	 		
 	}
 	
 	/*단어장 리스트 종료*/
 	
 	/*채팅 시작*/
 var GoLiveState = false;
 
function GoLive(){
	var div = document.getElementById("divNewView");
	//var teacher_name = document.getElementById("teacher_name");
	
	if(!GoLiveState){
	$.ajax({
		
		url : "selectAllTeacherList",
		type : "GET",
		dataType : "json",
		success : function(obj){	
			var str = '';
			
			/*초대할 친구 목록*/
			str += '<div class ="chat_box">';
			str += '<div class="chat_head" id="chat_head">Chatbox</div>';
			str += '<div calss="chat_body" id="chat_body">';
			
			$.each(obj, function(index, item){
				
				str += '<div class="user" id="user'+index+'" data_index="'+index+'" teacher_name="'+item.teacher_name+'">';
				//onclick="javascript:init3('+index+')"
				str += ''+item.teacher_name+'';
				str += '</div>';
				                                                                                                           
			});
			
			str += '</div>';
			str += '</div>';
			
			div.innerHTML = str;
			
			$('#chat_head').on('click' ,function(){
				
				$('#chat_body').slideToggle('slow');
				//$('#chat_body').css('height','0px');
			});
			init3();
			GoLiveState = true;
			$('.speachView').css('visibility','hidden');
			$('.startEndBtn').css('visibility','hidden');
			
		},
		error : function(error){
		console.log(error);	
		}
			
		});/*선생님 리스트 불러오기 종료*/
	}else{
		GoLiveState = false;
		str = '';
		div.innerHTML = str;
	    
	 // stop any single stream: audio or video or screen
 		connection.streams.stop('audio');
 		connection.streams.stop('video');

 		// stop multiple streams
 		connection.streams.stop({
 		  remote: true,
 		  video: true,
 		  screen: true
 		});
		
	}

}

function init3(){
    /*유저 목록 클릭시*/
	
	$(".user").on('click', function(){
    	var div = document.getElementById("divNewView");
    	//var teacher_name = $(this).attr('teacher_name');
    	
    	
    	var str = '<div id="local-Videos-Container"></div>';
    	str += '<div id="remote-Videos-Container"></div>';
    	
    	/*메시지 박스 */
    	
    	str += '<div class="msg_box" >';
    	str += '<div class="msg_head" id="msg_head">';
    	str += '대화창';
    	str += '<div class="close" id="close">x</div>';
    	str += '</div>';
    	
    	str += '<div id="msg_wrap">';
    	
    	str += '<div class="msg_body">';
   	
    	str += '<div class ="msg_insert" id="chatMessage" style="overFlow: auto; max-height: 500px;">';	
    	str += '</div>';
    	
    	str += '</div>';
    	str += '<div class="msg_footer"><input type="text" class="msg_input" id="message" placeholder="メッセージ内容"/></div>';
    	str += '<input type="hidden" id="to" value="">';
    	
    	str += '<div class="btn-open-or-join-room" id="btn-open-or-join-room">';
		str += 'Open Or JoinRoom';
		str += '<div>';
		str += '<div><input id="txt-roomid" placeholder ="Unique Room ID"><div>';
		
    	str += '</div>';
    	str += '</div>';
    	
    	div.innerHTML = str;
    	
    		$('#msg_head').on('click' ,function(){
    		
    		$('#msg_wrap').slideToggle('slow');
    	});
    		
    		
    		$('#close').click(function(){
    			
    			$('.msg_box').hide();
    			
    		});
    	
    	// 테스트 시작
        var connection = new RTCMultiConnection();

     // or a free signaling server
     connection.socketURL = 'https://rtcmulticonnection.herokuapp.com:443/';
     
     // if you want audio+video conferencing
     connection.session = {
         audio: true,
         video: true
     };
    
     connection.sdpConstraints.mandatory = {
    		 OfferToReceiveAudio: true,
     		 OfferToReceiveVideo:true
     };
     
     var roomid = document.getElementById('txt-roomid');
     
     roomid.value = connection.token(); 
    	 //(Math.random() * 1000).toString().replace('.','');
     
     document.getElementById('btn-open-or-join-room').onclick = function(){
    	 this.disabled = true;
    	 connection.openOrJoin(roomid.value || 'predefiend-roomid', function(isRoomExists, roomid) {
          	  alert(isRoomExists ? 'You joined room' : 'You created room');
        	});
     }
    
     connection.onstream = function(event){
    	 var video = event.mediaElement;
    	 console.log(event);
    	 if(event.type === 'local'){
    		 $('#local-Videos-Container').html(video);
    	 }
    	 
    	 if(event.type === 'remote'){
    		 $('#remote-Videos-Container').html(video);
    	 }
    	 
     };
     
     /*소켓 메세지, 방 생성 */
 	var sock = null;
 	var message = {};
 	
 		sock = new SockJS("/www/echo");
 		
 	  	console.log(sock);
 	  	
 	    sock.onopen = function() {
 	    	
 	        message={};
 	        message.message = "ようこそ.";
 	        message.type = "one";
 	        message.to = "all";
 	        sock.send(JSON.stringify(message)); 
 	        
 	        }
 	     
 	    sock.onmessage = function(evt) {
 	        console.log(evt);
 	        
 	        $("<div class ='msg_a'>"+evt.data+"</div>").insertBefore('.msg_insert');
	             $('.msg_body').scrollTop($('.msg_body')[0].scrollHeight);
	             
 	    	
 	    };
 	     
 	    sock.onclose = function() {
 	    	
 	         sock.send("チャットを終了します.");
 	    }
 	     
 	     $("#message").keydown(function (key) {
 	         if (key.keyCode == 13) {
 	        	 var msg=$(this).val();
 	        	 $(this).val('');
 	        	 $("<div class ='msg_b'>"+msg+"</div>").insertBefore('.msg_insert');
 	             $('.msg_body').scrollTop($('.msg_body')[0].scrollHeight);
 	             
 	             message={};
  	            message.message = msg;
  	            message.type = "all";
  	            message.to = "all";
  	             
  	            var to = $("#to").val();
  	            if ( to != "") {
  	                message.type = "one";
  	                message.to = to;
  	            }
  	            
  	           sock.send(JSON.stringify(message));
  	            
 	         }
 	         
 	      });
 	  
 	    
 	    $('#user').on('click', function(){
 	    	
 	    	$('.msg_box').show();
 	    });
     			
 	    
     
     
     
     
	});
};
     /*
    	 //테스트 종료 
        
    	
    	var sock = null;
    	var message = {};
    	
    		sock = new SockJS("ws://10.10.15.46:8888/www/echo");
    		
    	  	console.log(sock);
    	  	
    	    sock.onopen = function() {
    	    	
    	        message={};
    	        message.message = "반갑습니다.";
    	        message.type = "all";
    	        message.to = "all";
    	        sock.send(JSON.stringify(message)); 
    	        
    	        }
    	     
    	    sock.onmessage = function(evt) {
    	        console.log(evt);
    	        
    	        $("<div class ='msg_a'>"+evt.data+"</div>").insertBefore('.msg_insert');
	             $('.msg_body').scrollTop($('.msg_body')[0].scrollHeight);
	             
    	    	
    	    };
    	     
    	    sock.onclose = function() {
    	    	
    	        // sock.send("채팅을 종료합니다.");
    	    }
    	     
    	     $("#message").keydown(function (key) {
    	         if (key.keyCode == 13) {
    	        	 var msg=$(this).val();
    	        	 $(this).val('');
    	        	 $("<div class ='msg_b'>"+msg+"</div>").insertBefore('.msg_insert');
    	             $('.msg_body').scrollTop($('.msg_body')[0].scrollHeight);
    	             
    	             message={};
     	            message.message = msg;
     	            message.type = "all";
     	            message.to = "all";
     	             
     	            var to = $("#to").val();
     	            if ( to != "") {
     	                message.type = "one";
     	                message.to = to;
     	            }
     	            
     	           sock.send(JSON.stringify(message));
     	            
    	         }
    	         
    	      });
    	  
    	    
    	    $('#user').on('click', function(){
    	    	
    	    	$('.msg_box').show();
    	    });
    	    
    	 
        			
    	    
    });*/

/*init3() 종료*/
    




/**
 * Seek the video to the currentTime.
 * (And mark that the HTML slider *don't* move.)
 *
 * :param currentTime: 0 <= number <= 100
 */
function youTubePlayerCurrentTimeChange(currentTime) {
    'use strict';

    youTubePlayer.personalPlayer.currentTimeSliding = false;
    if (youTubePlayerActive()) {
        youTubePlayer.seekTo(currentTime*youTubePlayer.getDuration()/100, true);
    }
}


/**
 * Mark that the HTML slider move.
 */
function youTubePlayerCurrentTimeSlide() {
    'use strict';

    youTubePlayer.personalPlayer.currentTimeSliding = true;
}

function WatchTheVideo(){
	
	alert(youTubePlayer.getPlayerState());
	
	 if(youTubePlayer.getPlayerState() == 1){
		 //youTubePlayer.stopVideo();
			 youTubePlayer.pauseVideo();
			}
	
	else if(youTubePlayer.getPlayerState() == 2 || youTubePlayer.getPlayerState() == 5){
		youTubePlayerPlay();
	}
	 
	else if(youTubePlayer.getPlayerState() == 3){
		
		youTubePlayerPlay();
	}
	
}

$(function(){
	/*댓글 리스트 출력*/
	init2();	 
	
	$('#formButton').on('click', function(){
		
		var member_id = $('#member_id').val();
		var contents_num = $('#contents_num').val();
		var reply_text = $('#reply_text').val();
		
		if(reply_text.length == 0){
			alert("コメントを入力してください");
			return;
		}
		
		$.ajax({
		
			url : "contentsReplyInsert",
			type : "POST",
			contentType : "application/json; charset=utf-8",
			data : JSON.stringify({
				
				member_id : member_id,
				contents_num : contents_num,
				reply_text : reply_text
				
			}),
			success : function(){
				
				alert("コメント登録");
				
				$('#reply_text').val('');
				/*댓글 리스트 출력*/
				init2();
			},
			error : function(err){
				console.log(err)
			}
			
		});
		
	});
	
	/*좋아요 입력*/
	$('#thumbsUp').on('click',function(){
		var contents_num = $('#contents_num').val();
		$.ajax({
			url:"selectRecommendOrNot",
			type:"GET",
			data:{
				contents_num : contents_num
			},
			success: function(data){
				
				if(data){
					
					$.ajax({
						url:"insertRecommend",
						type: "POST",
						data:{
							contents_num : contents_num	
						},
						success: function(){
							var contents_num = $('#contents_num').val();
							
							$.ajax({
								url:"selectRecommendCount",
								type: "GET",
								data:{
									contents_num : contents_num
								},
								success: function(data){
									if(data){
										$('#thumbsUp').attr('src','/www/resources/icon/star_after.svg');
										var str = '<h4>'+data+'</h4>';
										$('#recommendCountDiv').html(str);
									}
									
								},
								error:function(err){
									console.log(err);
								}
							});//내부의내부함수
						},
						error: function(err){
							console.log(err);
						}
						
						
					});//THUMBSUP 함수 내부 1 AJAX
		
				}else{
					alert("いいね！をもう押しました。");
					
				}
				
			},
			error: function(err){
				console.log(err);
			}
		});
		
	});
	
	
	$('#thumbsUp').mouseover(function(){
		$('#thumbsUp').css('cursor','pointer')
	})
	
	/*북마크 입력*/
	$('#bookMark').on('click',function(){
		var contents_num = $('#contents_num').val();
		$.ajax({
			url:"selectBookmarkOrNot",
			type:"GET",
			data:{
				contents_num : contents_num
			},
			success: function(data){
				
				if(data){
					
					$.ajax({
						url:"bookmarkInsert",
						type: "POST",
						data:{
							contents_num : contents_num	
						},
						success: function(){
							var contents_num = $('#contents_num').val();
						$('#bookMark').attr('src','/www/resources/icon/bookmark_black.svg');
						alert("ブックマーク登録");
						
						},
						error: function(err){
							console.log(err);
						}
							
					});
		
				}else{
			
						$.ajax({
							
							url : "bookmarkDelete",
							type : "POST",
							data :{
								
								contents_num : contents_num
							},
							success :function(){
								var contents_num = $('#contents_num').val();
								$('#bookMark').attr('src','/www/resources/icon/bookmark_border_black.svg');
								alert("ブックマーク削除");
							},
							error : function(err){
								console.log(err);
							}
							
						});
				}
				
			},
			error: function(err){
				console.log(err);
			}
		});
		
		
	});
	
	
	$('#bookMark').mouseover(function(){
		$('#bookMark').css('cursor','pointer')
	})
	
});//레디함수 종료 


function init2(){
		
	var contents_num = $('#contents_num').val();
	var page = 1;
	var loading = true;
	
	$(document).scroll(function(){
		
		var maxHeight = $(document).height();
	    var currentScroll = $(window).scrollTop() + $(window).height();
	   // var flag = false;
	   
	    if (maxHeight <= currentScroll + 100) {
	    	
	    /*	if(flag == true)return;
		    	setTimeout(() => {
					flag = true;
				}, 2000);
				*/
	    	
	    	$('html').scrollTop(0);
	    	
	    	if(loading){
	    		page = page+1;
	    		
	    		loading = false;
	    		
	    		$.ajax({
	    			
	    			url : "selectAllContentsReply",
	    			type : "POST",
	    			data : {
	    				
	    				contents_num : contents_num,
	    			    		 page : page
	    			},
	    			
	    			dataType : "json",
	    			success : function(obj){
	    				console.log("追加ページリスト ");
	    					console.log(obj);
	    				
	    				var str = '';
	    				
	    					$.each(obj.contentsReply, function(index, item){
	    					   
	    						if(index == 0){
	    							str += '<li id="page'+page+'" class="mdl-list__item mdl-list__item--three-line">';	
		    					//str +=	'<li id="page'+page+'" class="mdl-list__item mdl-list__item--three-line">';
		    					}else{
		    						
		    						str += '<li id="page'+page+'" class="mdl-list__item mdl-list__item--three-line">';
		    						
		    					}
	    					/*
	    					 str += '<td class="mdl-data-table__cell--non-numeric">'+item.reply_num+'</td>';
	    					 str += '<td>'+item.member_id+'</td>';
	    					 str += '<td>'+item.reply_text+'</td>';
	    					 str += '<td>'+item.inputdate+'</td>';
	    					 str += '<td><input type="button" value="삭제" class="deleteContentsReply mdl-button mdl-js-button mdl-button--raised mdl-button--colored" data_num="'+item.reply_num+'"></td>'; 
	    					 str += '<td><input type="button" value="수정" class= "updateContentsReplyForm mdl-button mdl-js-button mdl-button--raised mdl-button--colored" data_num="'+item.reply_num+'" data_text="'+item.reply_text+'" data_index="'+index+'"></td>';
	    					 str += '<td><div class="updateContentsReplyFormDiv'+index+'"></div></td>';
	    					 str += '</tr>';
	    					  */
	    						
	    					 	//str += 	'<li class="mdl-list__item mdl-list__item--three-line">';
	    						str +=  '<span class="mdl-list__item-primary-content">';
	    						str +=  '<i class="material-icons mdl-list__item-avatar">person</i>';
	    						str +=  '<span>'+item.member_id+'</span>';
	    						str +=  '<span class="mdl-list__item-text-body">';
	    						str +=  '<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">';
	    						str +=  '<input class="mdl-textfield__input" type="text" id="div'+item.reply_num+'" value="'+item.reply_text+'" readonly="readonly"/>';		
	    						str +=  '</div>';
	    						str +=	'</span>';
	    						str +=  '</span>';
	    						str +=  '<span class="mdl-list__item-secondary-content"><input type="button" value="削除" class="deleteContentsReply mdl-button mdl-js-button mdl-button--raised mdl-button--colored" data_num="'+item.reply_num+'"></span>';	
	    						str +=  '<span class="mdl-list__item-secondary-content"><input type="button" value="修正" class= "updateContentsReplyForm mdl-button mdl-js-button mdl-button--raised mdl-button--colored" data_num="'+item.reply_num+'" data_text="'+item.reply_text+'" data_index="'+index+'"></span>';		
	    						str +=  '</li>';
	    						
	    					  if(page <= obj.navi.currentPage){
	    						 //$('#loading').addClass('mdl-spinner mdl-spinner--single-color mdl-js-spinner is-active');
	    						 loading = true;
	    						 
	    					  }else{
	    						  
	    						  //$('#loading').removeClass('mdl-spinner mdl-spinner--single-color mdl-js-spinner is-active');
	    						  str ='';
	    						  loading = false;
	    						  //alert("끝 페이지 ");
	    						  $('html').scrollTop(0);
	    						
	    					  }
	    					
	    				});
	    					
	    				$('#page'+(page-1)).after(str);

	    		        /*   삭제 시작*/
	    		   		$('.deleteContentsReply').on('click', function(){
	    		   			
	    		   			var reply_num = $(this).attr('data_num');
	    		   			console.log(reply_num);
	    		   			
	    		   			$.ajax({
	    		   				
	    		   				url : "contentsReplyDelete",
	    		   				type : "POST",
	    		   				contentType : "application/json; charset=UTF-8",
	    		   				data : JSON.stringify({
	    		   					reply_num : reply_num
	    		   				}),
	    		   				success : function(){
	    		   					init2();
	    		   				},
	    		   				
	    		   				error : function(error){
	    		   					console.log(error);
	    		   				}
	    		   				
	    		   			});
	    		   			
	    		   		});
	    		   		
	    		   		
	    		   		/*수정 시작*/
	    		   		$('.updateContentsReplyForm').on('click', function(){
	    		   			
	    		   			var reply_num = $(this).attr('data_num');
	    		   			var reply_text = $(this).attr('data_text');
	    		   			var index = $(this).attr('data_index');
	    		   			
	    		   			var str = '';
	    		   			
	    		   			console.log(reply_num);
	    		   			console.log(reply_text);
	    		   			console.log(index);
	    		   			
	    		   			str = '<input type="hidden" id ="reply_num"  value="'+reply_num+'">';
	    		            str += '<input type="text" id ="reply_text2"  value="'+reply_text+'" >';
	    		            str += '<input type="button" value="修正完了" id="updateContentsReply" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored">';
	    		   			
	    		               $('.updateContentsReplyFormDiv'+index).html(str);
	    		               console.log(str);
	    		               
	    		               $('#updateContentsReply').on('click', function(){
	    		               	
	    		               	var reply_num = $('#reply_num').val();
	    		               	var reply_text = $('#reply_text2').val();
	    		               	console.log(reply_num);
	    		               	console.log(reply_text);
	    		               	
	    		               	$.ajax({
	    		               	
	    		               		url : "contentsReplyUpdate",
	    		               		type : "POST",
	    		               		contentType : "application/json; charset=utf-8",
	    		               		data : JSON.stringify({
	    		               		
	    		               			reply_num : reply_num,
	    		               			reply_text : reply_text
	    		               			
	    		               		}),
	    		               		
	    		               		success : function(){
	    		               			
	    		               			init2();
	    		               			
	    		               		},     		
	    		               		error : function(error){	    		               			
	    		               			console.log(error);
          			
	    		               		}     		
	    		               	});               	
	    		               });
	    		   		});       	
	    			},
	    			
	    			error : function(err){
	    				
	    				console.log(err);
	    				
	    			}
	    		});
	}    	
	    }
	});
	$.ajax({
		
		url : "selectAllContentsReply",
		type : "POST",
		data : {
			
			contents_num : contents_num,
		    		 page : page
		
	},
	dataType : "json",
	success : function(obj){
		//console.log("첫 페이지 리스트 ");
		//console.log(obj.navi);
		//console.log(obj.contentsReply);
		
		var str = '';
		/*
		str += '<tr>';
		str += '<th class="mdl-data-table__cell--non-numeric">번호</th>';
	    str += '<th>아이디</th>';
	    str += '<th>내용</th>';
	    str += '<th>날짜</th>';
	    str += '<th>삭제</th>';
	    str += '<th>수정</th>';
		str += '<th></th>';
		str += '</tr>';
		*/
		
		
		$.each(obj.contentsReply, function(index, item){
				console.log(index);
				
		str += 	'<li id="page1" class="mdl-list__item mdl-list__item--three-line">';
		str +=  '<span class="mdl-list__item-primary-content">';
		str +=  '<i class="material-icons mdl-list__item-avatar">person</i>';
		str +=  '<span>'+item.member_id+'</span>';
		str +=  '<span class="mdl-list__item-text-body">';
		str +=  '<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">';
		str +=  '<input class="mdl-textfield__input" type="text" id="div'+item.reply_num+'" value="'+item.reply_text+'" readonly="readonly"/>';		
		str +=  '</div>';
		str +=	'</span>';
		str +=  '</span>';
		str +=  '<span class="mdl-list__item-secondary-content"><input type="button" value="削除" class="deleteContentsReply mdl-button mdl-js-button mdl-button--raised mdl-button--colored" data_num="'+item.reply_num+'"></span>';	
		str +=  '<span class="mdl-list__item-secondary-content"><input type="button" value="修正" class= "updateContentsReplyForm mdl-button mdl-js-button mdl-button--raised mdl-button--colored" data_num="'+item.reply_num+'" data_text="'+item.reply_text+'" data_index="'+index+'"></span>';		
		str +=  '</li>';
		
		/*
			str += '<tr id="page1">';
			str += '<td >'+item.reply_num+'</td>';
			str += '<td >'+item.member_id+'</td>';
			str += '<td >'+item.reply_text+'</td>';
			str += '<td >'+item.inputdate+'</td>';
			str += '<td><input type="button" value="삭제" class="deleteContentsReply mdl-button mdl-js-button mdl-button--raised mdl-button--colored" data_num="'+item.reply_num+'"></td>'; 
			str += '<td><input type="button" value="수정" class= "updateContentsReplyForm mdl-button mdl-js-button mdl-button--raised mdl-button--colored" data_num="'+item.reply_num+'" data_text="'+item.reply_text+'" data_index="'+index+'"></td>';
			str += '<td><div class="updateContentsReplyFormDiv'+index+'"></div></td>';
			str += '</tr>';
			*/
			
		})
		
		
		 $('#replyDiv').html(str);
		
		
		/*삭제 시작*/
		$('.deleteContentsReply').on('click', function(){
			
			var reply_num = $(this).attr('data_num');
			console.log(reply_num);
			//alert(reply_num);
			$.ajax({
				
				url : "contentsReplyDelete",
				type : "POST",
				contentType : "application/json; charset=UTF-8",
				data : JSON.stringify({
					
					reply_num : reply_num
			
				}),
				success : function(){
					
					init2();
				},
				
				error : function(error){
					
					console.log(error);
				}
				
			});
			
		});
		
		
		/*수정 시작*/
		$('.updateContentsReplyForm').on('click', function(){
			
			var reply_num = $(this).attr('data_num');
			var reply_text = $('#div'+reply_num).val();
			$('#div'+reply_num).focus();
			$('#div'+reply_num).removeAttr('readonly');
			
			$(this).attr('value', '修正完了');
			$(this).attr('class', 'mdl-button mdl-js-button mdl-button--raised mdl-button--accent');
			
			var str = '';
					
			str = '<input type="hidden" id ="reply_num"  value="'+reply_num+'">';
            str += '<input type="text" id ="reply_text2"  value="'+reply_text+'" >';
			
            $('#div'+reply_num).html(str);
            
            $('.mdl-button, mdl-js-button, mdl-button--raised, mdl-button--accent').on('click', function(){
            	
            	var reply_num = $('#reply_num').val();
            	var reply_text2 = $('#reply_text2').val();
            	//alert(reply_text2);
            	$.ajax({
            	
            		url : "contentsReplyUpdate",
            		type : "POST",
            		contentType : "application/json; charset=utf-8",
            		data : JSON.stringify({
            		
            			reply_num : reply_num,
            			reply_text : reply_text2
            			
            		}),
            		
            		success : function(){
            			//alert("수정완료");
            			$(this).attr('value', '修正');
            			$('#div'+reply_num).attr('value', reply_text2);
            			
            			init2();
            			
            			
            		},
            		
            		error : function(error){
            			
            			console.log(error);
            			
            		}
            		
            	});
            	
            });
		});
		
		
	},
	error : function(error){
		
		console.log(error);
	}
	});
	
	/*좋아요 */
	$.ajax({
		url:"selectRecommendOrNot",
		type:"GET",
		data:{
			contents_num : contents_num
		},
		success: function(data){
			console.log(!data);
			if(!data){
				$('#thumbsUp').attr('src','/www/resources/icon/star_after.svg');
			}
			
		},
		error: function(err){
			console.log(err);
		}
	}); //2번 ajax
	
	$.ajax({
		url:"selectRecommendCount",
		type: "GET",
		data:{
			contents_num : contents_num
		},
		success: function(data){
			var str = '<h4>'+data+'</h4>';
			$('#recommendCountDiv').html(str);
		},
		error:function(err){
			console.log(err);
		}
	});//3번 ajax
	

	$.ajax({
		url : "selectBookmarkOrNot",
		type : "GET",
		data :{
			
			contents_num : contents_num
		},
		success : function(data){
			if(!data){
				$('#bookMark').attr('src','/www/resources/icon/bookmark_black.svg');
			}
			},
		error : function(err){
			console.log(err);
			
		}	
			
		});


}
//init2()함수 종료





