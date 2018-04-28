<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">
<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Edit Space</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"> </script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<style type="text/css">
	main {
	    margin-left: 1em;
	    margin-right: 1em;
	}
	
	main > section { margin-bottom: 1em; }
	
	main > section div.framed > div {
	    border: 1px solid black;
	    margin-bottom: 1ex;
	    padding: 1ex;
	}
	
	main > section button { padding: 0.5ex 1em; }
	
	main > section input[type="range"] { width: 400px; }
	
	main > section label[for^="YouTube-player-"] { position: absolute; }
	
	main > section textarea {
	    height: 9ex;
	    margin: 0;
	    padding: 0;
	    vertical-align: top;
	    width: 99%;
	}
	
	
	.center { text-align: center; }
	
	.margin-left-m { margin-left: 1em; }
	.margin-right-m { margin-right: 1em; }
	
	.nowrap { white-space: nowrap; }
	
	
	#indicator-display {
	    background-color: #f0f0f0;
	    border-radius: 3ex;
	    display: inline-block;
	    float: right;
	    font-family: monospace;
	    height: 2ex;
	    line-height: 2ex;
	    margin: 1ex 1ex 0 0;
	    padding: 1ex;
	    text-align: center;
	    vertical-align: middle;
	    width: 2ex;
	}
	
	#YouTube-video-id { font-family: monospace; }
	
	
	@media (max-width: 600px) {
	    main > section iframe {
	      height: 240px;
	      width: 320px;
	    }
	
	    main > section input[type="range"] { width: 320px; }
	}
	
	@media (max-width: 400px) {
	    main > section iframe {
	      height: 200px;
	      width: 200px;
	    }
	
	    main > section input[type="range"] { width: 200px; }
	}


 #YouTube-player-progress {
 	margin-left : 50%;
 	width: 38700px;
 	height:80px; 
 	position : absolute;
  	opacity: 50;
 	
 }
 .playRow {
 	width : 100%;
 	height : 260px;
 	overflow : hidden;
	padding-top: 0px;
	padding-bottom: 0px;
	overflow-x: none;
 }
 .genSubtitle{
 
 }

#playerSection player{
	z-index: -1;
	margin-bottom: 0px;
	margin-top:0px;
}




.transcript-float-video{
	z-index : 999;
	background-color: gray;
	opacity: 10;
	position: absolute;
	color: white;
	text-align: center;
	margin-left: 10%;
	margin-top: 150px;
	display: inline;
	
}

#timeLineMarker{
	background: #FC0D1B;
    content: "";
    display: block;
    height: 65px;
    left: 50%;
    position: absolute;
    width: 1px;
    z-index: 54;
    float: left;

	
}

#playRow{
	background-color: #1a1a1a;
	height: 65px;
	overflow: hidden;
	overflow-y:auto;
}

#timeViewer{
	height: 70px;
	position : static;

}

.transcriptTextArea{
	
	padding: 6px 10px 2px 30px;
}
#insertCell{
	overflow: auto;
	height: 386px;
}

.subtitle{
	height: 28px;
	width: 400px;
	background-color: white;
	opacity : 90;
	position: absolute;
	top: 31%;
	margin-left: 51%;
	cursor: move;
	text-overflow: ellipsis;
}

#transcripts {
	overflow: auto;
	height: 386px;
	}

#infoRow{
	height : 200px;
	padding-bottom: 7px;
	padding-top: 0px;
}

.timing{
	font-size: 10px;
}
.mdl-cell mdl-cell--4-col{
	padding-bottom: 0px;
}
#container{
	  background-color:  #333;
}

html {
   overflow-x: hidden !important;
 
}

.material-icons md-18{
	position: absolute;
}

.transcriptTextArea mdl-textfield__input {
font-size: 1rem;
}
.timing_start_point{
font-size: 0.89rem;
}
.timing_end_point{
font-size: 0.89rem;
}
.disabled{
    pointer-events:none;
    opacity:0.4;
}
li:enabled:active{

}
.dragEditList{
	position: relative;
	left: -151px;
    top: 35px;
    
}
.delEditList{
	position: relative;
    top: -24px;
    right: -400px;
   }
   
.transcriptDiv{
	height: 44px;

}

.timing_start_point{
	position : relative;
	right : -383px;
	top : -42px;
}
   
  .timing_end_point{
	position : relative;
	right : -328px;
	top : -27px;
}

</style>
</head>
<body>
	<div class="container">
		<div class="mdl-grid" id="infoRow">
			<div class="mdl-cell mdl-cell--4-col">

				<b>Keyboard controls</b>

				<p>Tab : 재생/정지</p>

				<p>Ctrl + X : 내용 복사</p>

				<p>Shift + Tab : 재생/정지 (구현예정)</p>


			</div>
			<div class="mdl-cell mdl-cell--5-col" id="playerSection">
				<div class="transcript-float-video"></div>

				<div id="YouTube-player"></div>


				<span class="nowrap margin-left-m margin-right-m"> 
				<input	id="YouTube-video-id" type="hidden" value="${url }" size="12" pattern="[_\-0-9A-Za-z]{11}"	onchange="youTubePlayerChangeVideoId();">

				</span>

				<!--    <span class="nowrap">
	          <button onclick="youTubePlayerPlay();">Play</button>
	          <button onclick="youTubePlayerPause();">Pause</button>
	          <button onclick="youTubePlayerStop();">Stop</button>
	        </span> -->


			</div>
			<div class="mdl-cell mdl-cell--3-col">
				<div>
					<ol>
						<li id="firstRow">Type what you hear <br>
						<button	class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored" id="startBtn">Start Sync!</button>
						</li>
						<hr>
						<li class="disabled" id="secondRow"> Sync Timing <br>
						</li>
						<hr>
						<li class="disabled" id="thirdRow">Review and Complete <br>
						</li>
					</ol>
					
					<script type="text/javascript">
						$('#startBtn').on('click',function(){
							var str = '';
							str += '<button	class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored" id="syncBtn">Start Review!</button>'
							
							$('#startBtn').closest("li").attr('class','disabled');
							$('#startBtn').css('display','none');
							$('#playRow').css('display','inherit');
							$('#secondRow').append(str);
							$('#secondRow').attr('class','active');
							$('#syncBtn').on('click', function(){
								var str = '';
								str += '<button	class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored" id="publishBtn">Publish</button>'
								$('#syncBtn').closest("li").attr('class','disabled');
								$('#syncBtn').css('display','none');
								
								$('#thirdRow').append(str);
								$('#thirdRow').attr('class','active');
								
								
							 	$('#publishBtn').on('click',function(){
									var num = $('#contents_num').val();
									var startTiming = document.getElementsByClassName('timing_start_point');
									var endTiming = document.getElementsByClassName('timing_end_point');
									var text = 	document.getElementsByClassName('transcriptTextArea mdl-textfield__input');
									var array = new Array();
									var obj = {};
									for(let i = 0 ; i < startTiming.length ; i++){
										obj = {"ts_start" : ""+startTiming[i].innerHTML  , "ts_dur": ""+Number(endTiming[i].innerHTML) - Number(startTiming[i].innerHTML) , "ts_text": ""+text[i].value, "contents_num" : "" + num}
										array.push(obj);
									}
									console.log(JSON.stringify(array));
									
									$.ajaxSettings.traditional = true; //배열 형태로 서버쪽 전송을 위한 설정

									$.ajax({
										url : "insertTs",
										type : "POST",
										dataType : "json",
										contentType : "application/json",
										data :JSON.stringify(array),
										
										success : function(data){
											alert('성공');
											$(location).attr('href','/www');
										},
										error : function(err){
											console.log(err);
										}
									});
									
									 
									
								/* 	
									 $.ajax({
									url : "deleteTs",
									type : "POST",
									data : {
										contents_num : num
									},
									success : function(data){
										var selected 
										if(data){
											var aJsonArray = new Array();
											var aJson = new Object();
											aJson.ts_start = "";
											aJson.ts_dur = "";
											aJson.ts_text = "";
											
											$('.transcriptDiv').each(function{
											console.log($(this).val());
										});
											
										}
									},
									error : function(err){
										console.log(err);
									}
								}); */ 
							}); 
									
									
							});
							
					});
						
					</script>
						
				</div>
			</div>

		</div>

		<div id="playRow" class="mdl-grid" style="display: none">
			<div id="playDiv" class="mdl-cell--12-col">
				<!-- <input id="YouTube-player-progress" type="range" value="0" min="0" max="100" onchange="youTubePlayerCurrentTimeChange(this.value);" oninput="youTubePlayerCurrentTimeSlide();"> -->
				<div id="timeLineMarker"></div>
				<!-- <div class="subtitle">
					<a></a><span>Test</span><a></a>
				</div> -->
				<div id="YouTube-player-progress">
					<canvas id="timeViewer"></canvas>
				</div>
			</div>
		</div>

		<div id="editRow" class="mdl-grid">

			<div class="mdl-cell mdl-cell--4-col">
				<div>
					<c:if test="${tsList != null }">
						<button id="cloneBtn">Clone</button>
					</c:if>
				</div>
				<div id="transcripts">
					<ul class="demo-list-item mdl-list">
						<c:forEach items="${tsList }" var="list">
							<li class="mdl-list__item"><span class="timing_text">${list.ts_text }</span>
								<input type="hidden" class="timing_text_hidden"	value="${list.ts_text }">
								<div id="tt${list.ts_num }" class="material-icons md-18">assignment</div>
								<div class="mdl-tooltip" for="tt${list.ts_num }">
									<span class="timing_start">start : ${list.ts_start }</span><br>
									<span class="timing_dur">duration : ${list.ts_dur }</span> 
									<input class="timing_start_hidden" type="hidden" value="${list.ts_start }">
									<input class="timing_dur_hidden" type="hidden"	value="${list.ts_dur }">
								</div></li>
						</c:forEach>
					</ul>
				</div>
			</div>

			<div class="mdl-cell mdl-cell--5-col">
				<!-- <form action="syncedTranscript"> -->
				<div>편집영역</div>
				<div id="insertCell">
					
						<div id="transcriptPlace">
							<ul class="demo-list-item mdl-list" id="sortableEditList">
							
							</ul>
						</div>
						<div id="genSubtitle">+ New subtitle</div>
				</div>
				<!-- </form> -->
			</div>

			<div id="notes" class="mdl-cell mdl-cell--3-col">
				<div>정보영역</div>
				<input type="hidden" id="contents_num" value="${param.contents_num }">
				<br>
				<div class="framed">
					<div id="YouTube-player-infos"></div>
					<div id="YouTube-player-errors"></div>
					<div id="YouTube-player-fixed-infos"></div>
				</div>

				<div class="framed">
					<div id="YouTube-player-infos"></div>
					<div id="YouTube-player-errors"></div>
					<div id="YouTube-player-fixed-infos"></div>
				</div>
			</div>
		</div>
	</div>

	
<script>

//편집상태 알림
var phase= 0;

//자막 시작&끝 포인트
var startPoint = 0;
var endPoint = 0;
//자막 div 상태 전역변수
var transDivStatus = 0;
//Canvas 그리기 전역변수
var canvasOnce = 0;

//ProgressBar x 좌표 변수
var progressBar = 0;


//DisplayFlag
var displayFlag = 0;

//syncIndex 
var syncIndex = 0;
//syncFlag
var syncFlag = false;


var lengthGeneral = $('.transcriptDiv').length;
$('#genSubtitle').on('click', function(){
	var length = $('.transcriptDiv').length;
	var str = '';
	str += "<li class='transcriptDiv'><i class='material-icons md-18'><span class='dragEditList'>format list numbered</span></i>"
	str += "<input class='transcriptTextArea mdl-textfield__input' type='text' data-num="+lengthGeneral+"></input>";
	str += "<span class='timing_start_point'><c:if test="youTubePlayerActive()">"+console.log(youTubePlayer.getCurrentTime())+"</c:if></span>";
	str += "<input type='hidden' class='timing_start_point_hidden'>"
	str += "<i class='material-icons md-18'><span class='delEditList'>close</span></i>"
	str += "<span class='timing_end_point'></span></li>"
	$('#transcriptPlace ul').append(str);
	delListEdit();
	getValueOfSort();
	sortEditDiv();
	setTextSubtitle();
	}); 
	


/**
 * JavaScript code for the "YouTube API example"
 * http://www.opimedia.be/DS/webdev/YouTube/
 *
 * (c) Olivier Pirson --- 2016 January, 26
 */

/**
 * YT.Player initialized by onYouTubeIframeAPIReady().
 */
var youTubePlayer;

/**
 * Function called by https://www.youtube.com/iframe_api
 * when it is loaded.
 *
 * Initialized YouTube iframe with the value of #YouTube-video-id as videoId
 * and the value of #YouTube-player-volume as volume.
 *
 * Adapted from:
 * https://developers.google.com/youtube/iframe_api_reference
 * https://developers.google.com/youtube/player_parameters?playerVersion=HTML5
 */
function onYouTubeIframeAPIReady() {
    'use strict';

    var inputVideoId = document.getElementById('YouTube-video-id');
    var videoId = inputVideoId.value;
    var suggestedQuality = 'tiny';
    var height = 200;
    var width = 400;
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
        youTubePlayerDisplayFixedInfos();
    }

    function onStateChange(event) {
        var volume = Math.round(event.target.getVolume());
        var volumeItem = document.getElementById(youTubePlayerVolumeItemId);

        if (volumeItem && (Math.round(volumeItem.value) != volume)) {
            volumeItem.value = volume;
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
    
    drawItAgain(youTubePlayer.getDuration(), current);
}


/**
 * Display embed code to #YouTube-player-fixed-infos.
 */
function youTubePlayerDisplayFixedInfos() {
    'use strict';

    if (youTubePlayerActive()) {
        document.getElementById('YouTube-player-fixed-infos').innerHTML = (
            'Embed code: <textarea readonly>' + youTubePlayer.getVideoEmbedCode() + '</textarea>'
        );
    }
}


/**
 * Display
 *   some video informations to #YouTube-player-infos,
 *   errors to #YouTube-player-errors
 *   and set progress bar #YouTube-player-progress.
 */ 
 var progressStatus = 0;
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

        //시간 정보 (현재 백분율 -> 밀리초 단위로 변경 예정)
        
        var current = youTubePlayer.getCurrentTime();
        var duration = youTubePlayer.getDuration();
        var currentPercent = (current && duration
                              ? current*100/duration
                              : 0);
        
       /**
       *	 div 움직이기 
       *
       */ 
        if(state === 1){
        	$("#YouTube-player-progress").animate({left:(progressBar == 0 ? -current*70 : (3800*progressBar)-current*70 )+"px"},5);
        		progressStatus++;
        		//progressBar++;
        		
        		if(progressStatus >= 1100){
        			var canvas = document.getElementById('timeViewer');
        			var c = canvas.getContext('2d');
        			c.fillStyle="gray";
        			c.clearRect(0, 0, canvas.width, canvas.height);
        			$("#YouTube-player-progress").css('left','-1000');
        			drawItAgain(duration, current);
        			console.log(duration+"길이");
        			console.log(current+"현재 위치");
        			progressStatus = 0;
        			progressBar++;
        		}
        
      
        	
        		
        	if(transDivStatus === 1){
        		$(".subtitle").animate({left:startPoint*70 - current*70+"px"},5);	
        	
        	}
        	
        	
        	if(state === 0){
        		progressBar = 0;
        		//자막 시작&끝 포인트
        		startPoint = 0;
        		endPoint = 0;
        		//자막 div 상태 전역변수
        		transDivStatus = 0;
        		//Canvas 그리기 전역변수
        		canvasOnce = 0;
        		//ProgressBar x 좌표 변수
        		progressBar = 0;
        		//DisplayFlag
        		displayFlag = 0;
        		//syncIndex 
        		syncIndex = 0;
        		//syncFlag
        		syncFlag = false;
        	}
        	
        }
       
       
       
       //start, duration
/*       var check = $('.timing_start_hidden');
        var dur =  $('.timing_dur_hidden');
        var text = $('.timing_text_hidden');
        
 
        for(let i = 0 ; i < check.length ; i++){
        	var startPoint = check[i].value;
        	var endPoint = Number(startPoint)+ Number(dur[i].value);
        	
     
        	if(startPoint <= current && endPoint > current ){
        		transcriptDisplayOrNot(true);
        		 $(".transcript-float-video").text(text[i].value);
        		
        		break;
        	}else{
        		transcriptDisplayOrNot(false);	
        	}
        	
        
      	  
      	
        } 
        
       
        
        //캔버스 그리기
        if(canvasOnce < 1){
        	drawIt(duration);
        	console.log(duration);
        	if (duration >0){
        		canvasOnce++;	
        	}
        }
        
        
        //Typing 중인 textarea div 에 옮기기
      /*  var text =  $(".transcriptTextArea").val();
        $(".transcript-float-video").text(text); */
        
        //div로 재생 구간 조정하기
     /*    $('editRow').createElement("button", {
        	
        } */
        
        
        //자막DIV 보이기 안보이기 호출
       
        
      /*   if(timing_start  <= current &&  timing_end >= current ){
        	transcriptDisplayOrNot(true);	
        	displayFlag++;
        	
        }else{
        	transcriptDisplayOrNot(false);
        	
        }
         */
         /**
         *	DIV 만드는 함수 호출
         */
        
        if(syncIndex == $('.transcriptDiv').length-1){
        	
        	var lastIndex  = $('.subtitle').length;
        	var numOfTransDiv = $('.transcriptDiv').length-1;
     
        	if (lastIndex > numOfTransDiv + 5){
        		console.log("리턴");
        		return;	
        	}
        	createSubtitleDiv(numOfTransDiv);	
        }
         
        
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

        document.getElementById('YouTube-player-infos').innerHTML = (
            indicatorDisplay
                + 'URL: <a class="url" href="' + url + '">' + url + '</a><br>'
                + 'Quality: <strong>' + youTubePlayer.getPlaybackQuality() + '</strong>'
                + ' &mdash; Available quality: <strong>' + youTubePlayer.getAvailableQualityLevels() + '</strong><br>'
                + 'State <strong>' + state + '</strong>: <strong>' + youTubePlayerStateValueToDescription(state) + '</strong><br>'
                + 'Loaded: <strong>' + (fraction*100).toFixed(1) + '</strong>%<br>'
                + 'Duration: <strong>' + current.toFixed(2) + '</strong>/<strong>' + duration.toFixed(2) + '</strong>s = <strong>' + currentPercent.toFixed(2) + '</strong>%<br>'
                + 'Volume: <strong>' + volume + '</strong>%'
        );

        document.getElementById('YouTube-player-errors').innerHTML = '<div>Errors: <strong>' + youTubePlayer.personalPlayer.errors + '</strong></div>';
    }
    else {
        document.getElementById('YouTube-player-infos').innerHTML = indicatorDisplay;
    }
}

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
 * Change the volume.
 *
 * :param volume: 0 <= number <= 100
 */
function youTubePlayerVolumeChange(volume) {
    'use strict';

    if (youTubePlayerActive()) {
        youTubePlayer.setVolume(volume);
    }
}
/**
 * div 위에 눈금 표시하기
 * 
 */
	function drawIt(totalTime) {
		var canvas = document.getElementById('timeViewer');
	    var c = canvas.getContext('2d'); 
	   
	    
	    canvas.width = 15000;
	    
	   //시, 분, 초 계산
	    var hour = Math.floor(totalTime / 3600);
	    var minute = Math.floor(totalTime / 60) - (hour * 60);
	    var second =  totalTime - (minute * 60 +  hour * 60*60);
	   
	    //시간 표시를 위한 타임스탬프
	    var timeStamp = 0;
	    var hourStamp = 0;
	    var minuteStamp = 0;
	    var secStamp = 0;
	    
	    while(timeStamp <= totalTime){
			if(secStamp >= 60){
	    		minuteStamp++;
	    		secStamp = 0;
	    	}
			if(minuteStamp >= 60){
	    		hourStamp++;
	    		minuteStamp = 0;
	    	}
			//글자 설정
	    	c.fillStyle="gray";
			c.font= 'bold 30px Consolas';
	    	c.fillText( 
	    			(pad(hourStamp,2) == 00 ? "" : (pad(hourStamp,2)+":")) 
	    			+ (pad(minuteStamp,2) == 00 ? 0+":" : (pad(minuteStamp,2)+":")) 
	    			+ pad(secStamp,2), 150 * timeStamp, 140);
	    	
	    	secStamp++;
	    	timeStamp++;
	    	
	    }
	    
	}
	
	function drawItAgain(totalTime, current) {
		var canvas = document.getElementById('timeViewer');
	    var c = canvas.getContext('2d'); 
	   
	    
	    canvas.width = 15000;
	    
	   //시, 분, 초 계산
	    var hour = Math.floor(totalTime / 3600);
	    var minute = Math.floor(totalTime / 60) - (hour * 60);
	    var second =  totalTime - (minute * 60 +  hour * 60*60);
	    
	    
	    //현재 시간을 시분초로 계산
	    var currentHour = Math.floor(current / 3600);
	    var currentMinute = Math.floor(current / 60) - (hour * 60);
	    var currentSecond =  current - (currentMinute * 60 +  currentHour * 60*60);
	    
	    
	 
	    //시간 표시를 위한 타임스탬프
	    var timeStamp = 0;
	    var hourStamp = currentHour;
	    var minuteStamp = currentMinute;
	    var secStamp = Math.floor(currentSecond);
	    
	    var differentStamp = totalTime - current;
	    
	    //싱크 맞춰주기 위한 Stamp 설정
	    if((secStamp ) < 0){
			secStamp = secStamp + 50 ;
	    	if(--minuteStamp < 0){
	    		minuteStamp = minuteStamp + 50;
	    		--hour;
	    	}
	    }else{
	    	secStamp = secStamp;
	    }   
	    
	    console.log(hourStamp);
	    console.log(minuteStamp);
	    console.log(secStamp);
	   
	    
	    
	    while(timeStamp <= differentStamp){
			if(secStamp >= 60){
	    		minuteStamp++;
	    		secStamp = 0;
	    	}
			if(minuteStamp >= 60){
	    		hourStamp++;
	    		minuteStamp = 0;
	    	}
			//글자 설정
	    
			c.font= 'bold 30px Consolas';
			c.fillStyle="gray";
			c.fillText( 
	    			(pad(hourStamp,2) == 00 ? "" : (pad(hourStamp,2)+":")) 
	    			+ (pad(minuteStamp,2) == 00 ? 0+":" : (pad(minuteStamp,2)+":")) 
	    			+ pad(secStamp,2),  150 * timeStamp, 140);
	    	
	    	secStamp++;
	    	timeStamp++;
	    	
	    }
	    
	}
	
	/*
		캔버스 지우기
	*
	*/
	
	function cleartIt(){
		
	}
	
	
	//숫자 01 로 표현하는 함수 
	function pad(n, width){
		n = n+ '';
		return n.length >= width ? n : new Array(width - n.length + 1 ).join('0')+n;
	}

	//자막 보이기 숨기기
 	function transcriptDisplayOrNot(flag) {
		
		if(flag){
			$('.transcript-float-video').css('display','inline');	
		}else{
			$('.transcript-float-video').css('display','none');
		}
	}
	
	//개별 자막 전송
/* 	function insertATranscript{
		$.ajax({
			type:"post",
			url :"insertATranscript",
			data:{
				this.value;
			},
			success : function(flag){
				$(this).attr('readonly');
			}
		});
	} */
	
	/**
		
			
	*/
	
	/*
	 *	Div 에 편집영역 텍스트 붙여넣기
	 *
	 **/
	/* function setTextSubtitle() {
		
		var transcriptLength = $('input[type="text"]').length;
		let i = 0;
		for (i; i < transcriptLength ; i++){
			var subtitle = $('.subtitle')[i];
			var text = $('input[type="text"]')[i];
			subtitle.childNodes[i == 0 ? 2: 1].innerHTML = text.value;
			
			
			//$('.subtitle')[i].childNodes[i == 0 ? 2 : 1].innerHTML = $('input[type="text"]')[i].val();
		}
		 
		//var text = $('input[type="text"]')[syncIndex];
		
	//	console.log(syncIndex);
		//console.log(text.value);
		
		//var subtitle =$('.subtitle')[syncIndex]; 
		//console.log(subtitle);
		//subtitle.childNodes[syncIndex == 0 ? 2 : 1].innerHTML = text.value;
 		
		
	} */
	
	/**
		왼쪽 불러온 자막을 편집영역으로 복사하기
	*/
	
	$('#cloneBtn').on('click', function(){
		var cloneElements = $('#transcripts').clone();
		// 변수 cloneElements를 만들어 복사된 요소들을 저장
		cloneElements .appendTo('#insertCell');
		// 복사한 요소들을 buttons2 클래스명의 자식요소로 넣기
	});
	
	
	/**
	 *  자막 시작 점 설정
	 *
	 */
	function setStartPoint(current){
		//syncIndex = console.log(getValueOfSort());	
		var spanStart = $('.timing_start_point')[syncIndex];
		spanStart.innerHTML = current.toFixed(2);
		var flag = spanStart.innerHTML;
		var inputStart = $('.timing_start_point')[syncIndex];
		inputStart.value = current;
		

		if(!(flag =="" || flag == null || flag == undefined)){
			syncFlag = true;
			
			return true;
		}
		
	}
	
	/**
	 * 자막 종료 점 설정
	 *
	 */
	function setEndPoint(current, syncFlag){
		if(!syncFlag){
			return false;
		}
		var spanEnd = $('.timing_end_point')[syncIndex];
		spanEnd.innerHTML = current.toFixed(2);
		var flag = spanEnd.innerHTML;
		
		var inputEnd = $('.timing_end_point')[syncIndex];
		inputEnd.value = current;
		
		if(!(flag =="" || flag == null || flag == undefined)){
			syncIndex++;
			syncFlag = false;
			return true;
		}
		
	}	
	
	
	/**
	*	키보드 인풋으로 자막 삽입및 디스플레이 div 시작점 및 종료점 설정해주기
		parameters : currentTime
		returns : duration 
	*/
/* 	function setTranscriptDiv(current,syncIndex, keyEvent){
			
			if(keyEvent == 40){
				if(syncIndex >= 1){
					var syncStartDiv = $(".subtitle")[syncIndex];
					$.each($(".subtitle"), function(index,item){
						if((index) == syncIndex){
							console.log(this);
							$(this).css("left", ''+current);
							$(this).css("margin-left",""+50+"%");
							return;
						}
					});
					//$(".subtitle")[syncIndex].css("left", ''+current);
					 var syncStartDiv = document.getElementsByName("subtitle"); 
					syncStartDiv[syncIndex].style.left = current+"px";
					 
					startPoint = current;
					transDivStatus = 1;
					
				}else{
					$(".subtitle").css( 'left' , ''+current);
					startPoint = current;
					transDivStatus = 1;
				}
				
			}
			
			if(keyEvent == 38){
				if(syncIndex >= 1){
					var syncEndDiv = $(".subtitle");
					console.log(syncEndDiv);
					$.each($(".subtitle"), function(index,item){
						if((index) == syncIndex){
							$(this).css("width", current*70 - startPoint*70 + "px");
						//	$(this).css("margin-left",""+50+"%");
							return;
						}
					});
					
				//	syncEndDiv[syncIndex].style.width = ""+ current*70 - startPoint*70 + "px";
					
				}else{
					$(".subtitle").css('width', '' + current * 70 - startPoint*70+'px' );	
				}
				
			}
			return ;
		} */
	
	
	
	/*
	*  Div 시작위치점 잡는 함수  + duration 에 맞게 길이 설정 해주는 함수
	*/
	
/* 	function setFloatDiv(startTime, duration, count){
		
		if(count == 1){
		
			$(".subtitle").css('left', startTime * 70 + 'px');
			$(".subtitle").css('width',duration * 70+'px');
		}else{
			$(".subtitle").each(function(index, item){
				let startPnt = $('input[type=text]').childNodes;
				$('.subtitle').eq(index).css('left', startPnt * 70 + 'px');
				$('.subtitle').eq(index).css('width', dur * 70+'px');
			});
		}
		
	}
	 */
	
	/**
		selected 된 Text 값 리턴
	*/
	function getSelectedText() {
	    if (window.getSelection) {
	    	console.log(''+window.getSelection().toString());
	        return window.getSelection().toString();
	    } else if (document.selection) {
	        return document.selection.createRange().text;
	    }
	    return '';
	}
	
	/**
	*	선택영역 <li>삭제 이벤트 연결 함수
	*/
	function delListEdit(){
		$('.delEditList').on('click', function(){
			$(this).closest("li").remove();
		});
		
	}
	
	/**
	*	transcriptDiv 밸류값 indexing 하기
	*/
	function getValueOfSort(){
		$('.transcriptDiv').click(function(){
			var index = $('.transcriptDiv').index(this);
			return index;
		});
		
	}
	
	/**
	*	편집영역 div 순서 바꾸기
	*/
	
	function sortEditDiv(){
	
		$("#sortableEditList").sortable();
		$("#sortableEditList").disableSelection();
		
	}
	
	/*
	 *	DIV 생성하기
	 *
	 **/
	function createSubtitleDiv(numOfTranscript){
		var lastIndex  = $('.subtitle').length;
		
		console.log(lastIndex);
		
		/* if(numOfTranscript < lastIndex){
			break;
		}  */
		
		if(lastIndex == 2){
			var str = '';
			str = '<div class="subtitle" name = "subtitle" style="width:400px; float:left; left:200px; display:none;">';
			str += '<a></a><span></span><a></a></div>';
			$('.subtitle').after(str);
		
		}else {
			let i = lastIndex + 1;
			var str = '';
			str = '<div class="subtitle" name = "subtitle" style="width:400px; float:left; left:200px; display:none;">';
			str += '<a></a><span></span><a></a></div>';
			
				for(i ; i < lastIndex+5 ; i++ ){
					$('.subtitle').last().after(str);
				}
				
			}
	}
	
	/*
	 *	 subtitle DIV 다음 자막 위치에 대기 시키기
	 */
	
	/*  function setPositionNextSub(){
		var nextSub = $('.subtitle')[syncIndex+1];
		nextSub.style.display = 'inline';
		nextSub.style.marginLeft = "1500px";
	} */
	 
	
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
    	
       // setFloatDiv($('.timing_start_point')[0].val(),$('.timing_end_point')[0].val());
        
        //Draggable
     //   $('.transportable').draggable();
        
     /*    $('.timing_text').ondrag(function() {
		    var text=getSelectedText();
		    if (text!='') alert(text);
		});

		function getSelectedText() {
		    if (window.getSelection) {
		        return window.getSelection().toString();
		    } else if (document.selection) {
		        return document.selection.createRange().text;
		    }
		    return '';
		} */
        
	
        // Set timer to display infos
        setInterval(youTubePlayerDisplayInfos, 50);
    }
    if (window.addEventListener) {
        window.addEventListener('load', init);
    } else if (window.attachEvent) {
        window.attachEvent('onload', init);
    }
}());

	/**
	 * 키보드 동작 매칭
	 *
	 */
	$(document).keydown(function(e) {
	
		  if(e.which == 26) {
		    // enter pressed
			  $("#playDiv").animate({left:"+=5"},"fast");
		    console.log(e.which);
		    	
		  }
		  if(e.which == 39){
			  //right Arrow pressed
			  $("#playDiv").animate({right:"+=5"},"fast");
			  console.log(e.which);
	  }
		  if(e.which == 9){
			  if(youTubePlayer.getPlayerState() === 1){
				  youTubePlayerPause(); 
				  $(document).focus();
			  }else {
				  youTubePlayerPlay();
				  $(document).focus();
			  }
			  
		  }
		  
		  if(e.which == 40){
			  console.log(e.which);
			//  setTranscriptDiv(youTubePlayer.getCurrentTime(),syncIndex ,e.which);
			  setStartPoint(youTubePlayer.getCurrentTime());
		//	  setPositionNextSub();
		  }
		  
		  if(e.which == 38){
			  console.log(e.which);
			//  setTranscriptDiv(youTubePlayer.getCurrentTime(),syncIndex ,e.which);
			  setEndPoint(youTubePlayer.getCurrentTime(), syncFlag);
		  }
		  
		  if(e.which == 13){
			  console.log(e.which);
			  
				var length = $('.transcriptDiv').length;
				var str = '';
				str += "<li class='transcriptDiv'><i class='material-icons md-18'><span class='dragEditList'>format list numbered</span></i>"
				str += "<input class='transcriptTextArea mdl-textfield__input' type='text' data-num="+length+"></input>";
				str += "<input type='hidden' value="+ length +" />";
				str += "<span class='timing_start_point'><c:if test="youTubePlayerActive()">"+console.log(youTubePlayer.getCurrentTime())+"</c:if></span>";
				str += "<input type='hidden' class='timing_start_point_hidden'>"
				str += "<i class='material-icons md-18'><span class='delEditList'>close</span></i>";
				str += "<span class='timing_end_point'></span><input type='hidden' class='timing_end_point_hidden'></li>";
				$('#transcriptPlace ul').append(str);
				$('input[data-num='+length+']').focus();
				delListEdit();
				getValueOfSort();
			    sortEditDiv();
			   // setTextSubtitle();
		  }
		  
		  if(e.which == 88 & e.ctrlKey){

			  //로직
			var text = getSelectedText();
			text = text.replace(/ /gi,"");
			if(text == ''){
				return false;
			} 
			  
			var length = $('.transcriptDiv').length;
			var str = '';
			str += "<li class='transcriptDiv'><i class='material-icons md-18'><span class='dragEditList'>format list numbered</span></i>"
			str += "<input class='transcriptTextArea mdl-textfield__input' type='text' data-num="+length+" value="+text+"></input>";
			str += "<input type='hidden' value="+ length +" />";
			str += "<span class='timing_start_point'><c:if test="youTubePlayerActive()">"+console.log(youTubePlayer.getCurrentTime())+"</c:if> </span>";
			str += "<input type='hidden' class='timing_start_point_hidden'>"
			str += "<i class='material-icons md-18'><span class='delEditList'>close</span></i>"
			str += "<span class='timing_end_point'><input type='hidden' class='timing_end_point_hidden'></span></li>";
			$('#transcriptPlace ul').append(str);
			$('input[data-num='+length+']').focus();
			  
			delListEdit();
			getValueOfSort();
			sortEditDiv();
		//	setTextSubtitle();
			  return false;
			  
		  }
		  
		  
	});

	
	$('#transcriptDiv').on('click',function(){
		
	});
</script>



</body>
</html>