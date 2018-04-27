/**
 * 
 */


var youTubePlayer;
var done = true;
var dur = 3000;

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
		
		if(speakState){
			$('.Notice').text(speakText);
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
    var height = 360;
    var width = 640;
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
		
		var str = '<div class="Notice">';  
			str +=  "비디오에서 SPEAK1할 문장을 선택하세요:";
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
			
			
			
		$('.divNewView').html(str);
		$('.speachView').css('visibility','visible');
		speakSpace = true;
	}else{
		$('.divNewView').html('');
		$('.speachView').css('visibility','hidden');
		speakSpace = false;
	}
	
}


function LearnTheWords(tslist){
	var div = document.getElementById("divNewGSTL");
	div.style.overflow = "scroll";
	if(!learnSpace){
	var str3 = '<div class="Notice">';
	str3 += '</div>';
	$('.divNewView').html(str3);
	div.innerHTML  = str3;
	div.style.width="598px";
	div.style.height="700px";
	div.style.border="1px solid";
	var str2='';
		for (var i = 0; i < tslist.length; i++) {
			var num  = tslist[i].ts_num;
			var text = tslist[i].ts_text;
			var start = tslist[i].ts_start-1;
			var dur = tslist[i].ts_dur+1;
			var str = '<input class="mdl-button mdl-js-button mdl-button--primary" type="button" value="'+i+'. 재생" onclick="javascript:playsound('+start+','+dur+','+num+')">'
			str += ' <input type="hidden" id="answers'+num+'">';
			str += ' <div id="outputDiv'+num+'" style="display: inline;"></div>';
			str += '<div id="list'+num+'" style="display: none;"></div>';
			str += '<div style="display: none;" id="inputAnswer'+num+'">정답을 입력 :&nbsp;&nbsp;&nbsp;<input id="yourText'+num+'" type="text"   style="border-left: none; border-right: none; border-top: none; "></div><br>';
			this.test(num,text);
			str2 += str;
		}
		
	
	$('.secondView').html(str2);
	div.style.display="block";
	learnSpace=true;
	}else{
		div.innerHTML='';
		div.style.display="none";
		learnSpace=false;
	}
}

function playsound(start7,dur7,num7){
	done = false;
	dur = dur7*1500;
	youTubePlayer.seekTo(start7,true);// 유튜브 시작위치
	youTubePlayer.playVideo(); //유튜브 재생
	
	
}


function test(num,text){
	console.log("num:"+num);
	console.log("text:"+text);
    var object;
    var compare=[];
    compare.push(text);
    
    //문장을 API를 이용해 분해,분석 하자.
    $(document).ready(function(){
        $.ajax({
            type : "POST",
            url : "https://api.apigw.smt.docomo.ne.jp/gooLanguageAnalysis/v1/morph?APIKEY=766258364c33527044357054725a7149306e684c4a4243764c384673444c355a2e554863662f306f696238",
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
            	console.log(JSON.stringify(obj));
              var list = '';
              var str = obj.word_list[0];
              var c=0;
              
              //API로 받아온 분석결과는 2중 배열에 갇혀 있기 때문에 꺼내준다.
              $.each(str,function(index,item){
                //list += '인덱스: '+index+'<br>';
		              $.each(item,function(index2,item2){
		                switch(c){
		               case 0:
		                 list += '원문: '+item2;
		                 //list += item2;
		                 compare.push(item2);
		                 c++;
		                 break;
	    	           case 1:
	        	          list += ' 품사: '+item2;
	           		       //list += item2;
		                 c++;
		                 break;
		               case 2:
		                 list += ' 읽기: '+item2;
		                 //list += item2;
		                 c=0;
		                 break;
		               }
		             });
		         });
		         $('#list'+num).text(list);
		         var answers = document.getElementById('answers'+num);
		        	answers.value = compare;
		          //문제가 만들어지는 공간마다 답 입력칸을 만들어 준다.
	          	for (var i = 1; i < compare.length; i++) {
	          		console.log("compare[i]:"+compare[i]);
	          		//너무 짧은 건 넘기고
	        	if(compare[i].length<2)continue;
	        	
	          		//일치하는 부분을 답 입력 칸으로 변-환
	          	    text = text.replace(compare[i],'<input type="text" id="'+num+''+i+'" onkeypress="answer('+num+','+i+')"; size="5"; style="border-left: none; border-right: none; border-top: none;">');
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
 		//엔터를 입력했다면
	if(event.keyCode==13){
		
		//일치하면 입력 칸을 막고 일치하지 않으면 새 창을 띄워 문장에 대한 정보를 제공할 수 있는 버튼을 등장시킨다.
		var yourAnswer = document.getElementById(num+''+i);
		var correctAnswer = document.getElementById('answers'+num);
		var compare = [];
		compare = correctAnswer.value.split(",");
		console.log("compare[i] :"+compare[i]);
		var x = document.createElement("INPUT");
		if(yourAnswer.value != compare[i]){
			if(document.getElementById('view'+num)== null){
				x.setAttribute("id","view"+num);
				x.setAttribute("type", "button");
				x.setAttribute("value","정보 보기");
				x.setAttribute("class","mdl-button mdl-js-button mdl-button--accent");
				document.getElementById('outputDiv'+num).appendChild(x);
				x.onclick = function(){viewInfo(compare,num,contents_num)};
			}
			document.getElementById('view'+num).style.display="block";
			
		}else{
			yourAnswer.disabled=true;
			if(document.getElementById('view'+num)!=null)
			document.getElementById('view'+num).style.display="none";
			console.log("compare[0]:"+compare[0]);
		}
	}
}
 	//새 창 띄워서 정보 보기
 	function viewInfo(words,ts_num,contents_num){
 		console.log("ts_num :"+ts_num);
 		console.log("contents_num :"+contents_num);
 		var explanation = document.getElementById('list'+ts_num).innerHTML;
 		console.log("explanation :"+explanation);
	          window.open("../transcript/wordDetail?words="+words+"&ts_num="+ts_num+"&contents_num="+contents_num+"&explanation="+explanation, "wordDetail", "width=578, height=215, toolbar=no, menubar=no, scrollbars=no, location=no, status=no, resizable=no" );
 	}  


function GoLive() {
	/* var user_id = document.getElementById(""); */
	
	window.open("goLive", "newWindow", "top=300", "left=300", "width=500", "height=500");
	
	
}

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
			alert("정지")	;	 
		 //youTubePlayer.stopVideo();
			 youTubePlayer.pauseVideo();
			}
	
	else if(youTubePlayer.getPlayerState() == 2 || youTubePlayer.getPlayerState() == 5){
		alert("재생");
		youTubePlayerPlay();
	}
	 
	else if(youTubePlayer.getPlayerState() == 3){
		
		alert("버퍼링");
		youTubePlayerPlay();
	}
	
	
}








