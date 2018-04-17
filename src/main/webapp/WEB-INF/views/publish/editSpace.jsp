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
	padding: 0;
 }
 .genSubtitle{
 
 }

#playerSection player{
	z-index: -1;
}




.transcript-float-video{
	z-index : 999;
	background-color: gray;
	opacity: 10;
	position: absolute;
	color: white;
	text-align: center;
	margin-left: 10%;
	margin-top: 250px;
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
	background-color: GRAY;
	height: 65px;
	
	
}

#timeViewer{
	height: 65px;
	position : static;
}

.transcriptTextArea{

	padding: 6px 10px 2px 30px;
}
#insertCell{
	overflow: auto;
	height: 252px;
}

.subtitle{
	height: 20px;
	width: 400px;
	background-color: white;
	opacity : 90;
	float: left;
	position: relative;
	top: 30%;
	margin-left: 51%;
}

</style>
</head>
<body>
<div class="container"> </div>
	<div class="mdl-grid" id="infoRow">
		<div class="mdl-cell mdl-cell--4-col">
		
		<b>Keyboard controls</b> 
		
		    <p> Tab : 재생/정지 </p>
		     <p> Shift + Tab : 재생/정지 (구현예정) </p>
		
		
		</div>
		<div class="mdl-cell mdl-cell--4-col" id="playerSection">
			<div class="transcript-float-video">
					자막 삽입 구간
			</div>
			
			<div id="YouTube-player">
			</div>
			
			
	        <span class="nowrap margin-left-m margin-right-m">
	          <input id="YouTube-video-id" type="hidden" value="xGbxsiBZGPI" size="12" pattern="[_\-0-9A-Za-z]{11}" onchange="youTubePlayerChangeVideoId();">
				       
	        </span>
			
	     <!--    <span class="nowrap">
	          <button onclick="youTubePlayerPlay();">Play</button>
	          <button onclick="youTubePlayerPause();">Pause</button>
	          <button onclick="youTubePlayerStop();">Stop</button>
	        </span> -->
	        
	
		</div>
		<div class="mdl-cell mdl-cell--4-col">
			 <div>
		        <input id="YouTube-player-volume" type="range" value="50" min="0" max="100" onchange="youTubePlayerVolumeChange(this.value);">
		        <label for="YouTube-player-volume">volume</label>
		        
		        <button class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored">Publish!</button>
	    	  </div>
    	</div>

  
		
      </div>

	<div id="playRow" class="mdl-grid">
	 	<div id="playDiv" class="mdl-cell--12-col">
			<!-- <input id="YouTube-player-progress" type="range" value="0" min="0" max="100" onchange="youTubePlayerCurrentTimeChange(this.value);" oninput="youTubePlayerCurrentTimeSlide();"> -->	
			<div id="timeLineMarker"></div>
			<div class="subtitle"><a></a><span>Test</span><a></a></div>
			<div id="YouTube-player-progress">
				<canvas id="timeViewer"></canvas>
			</div>
		</div>
	</div>
	
	<div id="editRow" class="mdl-grid">
		<div id="transcripts" class="mdl-cell mdl-cell--4-col">
			<ul>
				<c:forEach items="${tsList }" var="list">
					<li>${list.ts_start }</li>			
					<li>${list.ts_text }</li>
					<li>${list.ts_dur }</li>
				</c:forEach>
			</ul>
		</div>
		
		<div id="insertCell" class="mdl-cell mdl-cell--4-col">
			<form action="syncedTranscript">
				<div id="transcriptPlace"></div>
				<div id="genSubtitle"> + New subtitle </div>
			</form>
		</div>
		
		<div id="notes" class="mdl-cell mdl-cell--4-col">
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
	

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"> </script>
<script>

var startPoint = 0;

$('#genSubtitle').on('click', function(){
	var button = "<button class='mdl-button mdl-js-button mdl-button--fab mdl-button--mini-fab'><i class='material-icons'>add</i> </button>";
	var str = '';
	
	str += "<div class='transcriptDiv'><textarea class='transcriptTextArea'></textarea></div>";
	
	
	$('#transcriptPlace').prepend(str)
	
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
    var height = 300;
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
        //div 움직이기
        if(state === 1){
        	$("#YouTube-player-progress").animate({left:-current*50+"px"},5);
        	$(".subtitle").animate({left:startPoint - current*50+"px"},5);
        	
        }
        
        //div로 재생 구간 조정하기
     /*    $('editRow').createElement("button", {
        	
        } */
        
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
	   
	    //글자 설정
	    canvas.font= 'italic 20pt';
	    canvas.width = 100 * totalTime;
	    
	   //시, 분, 초 계산
	    var hour = totalTime % 3600;
	    var minute = totalTime % 60 - (hour * 60);
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
	    	
	    	}else if(minuteStamp >= 60){
	    		hourStamp++;
	    		minuteStamp = 0;
	    	}
	    
	    	c.strokeText( 
	    			(pad(hourStamp,2) == 00 ? "" : (pad(hourStamp,2)+":")) 
	    			+ (pad(minuteStamp,2) == 00 ? 0 : pad(minuteStamp,2)) 
	    			+":"+pad(secStamp,2), 100 * timeStamp, 140);
		
	    	timeStamp++;
	    	secStamp++;
	    }
	}
	
	//숫자 01 로 표현하는 거 
	function pad(n, width){
		n = n+ '';
		return n.length >= width ? n : new Array(width - n.length + 1 ).join('0')+n;
	}

	//자막 보이기 숨기기
 	function transcriptDisplayOrNot() {
		
		
		
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
	
	//화면위에 div 출력 해주기
	
	
	
	
	/**
	*	키보드 인풋으로 자막 삽입및 디스플레이 div 시작점 및 종료점 설정해주기
		parameters : currentTime
		returns : duration 
	*/
	function setTranscriptDiv(current, keyEvent){
			
			
			if(keyEvent == 40){
				
				$(".subtitle").animate( {left : current});
				startPoint += current;
			}
			
			if(keyEvent == 38){
				
				$(".subtitle").css('width', '' + current * 50 - startPoint+'px' );
		
			}
			
			return ;
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
        setInterval(youTubePlayerDisplayInfos, 50);
    }
    if (window.addEventListener) {
        window.addEventListener('load', init);
    } else if (window.attachEvent) {
        window.attachEvent('onload', init);
    }
}());

	$(document).keydown(function(e) {
	
		  if(e.which == 26) {
		    // enter pressed
			  $("#playDiv").animate({left:"+=5"},"fast");
		    console.log(e.which);
		    	
		  }
		  if(e.which == 39){
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
			  setTranscriptDiv(youTubePlayer.getCurrentTime(),e.which)
		  }
		  
		  if(e.which == 38){
			  console.log(e.which);
			  setTranscriptDiv(youTubePlayer.getCurrentTime(),e.which)
		  }
		  
	});

</script>



</body>
</html>