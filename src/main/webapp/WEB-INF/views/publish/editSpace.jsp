<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
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
 	width: 6000px;
 	background-color: orange;
 	height:100px; 
 	position : absolute;
 	
 }
 .playRow {
 	width : 100%;
 	height : 260px;
	position : absolute;
 	overflow : hidden;
 }
 .genSubtitle{
 
 }

#playerSection {
	position : rel
}


.video-stream html5-main-video {
	z-index: 0;
	position: absolute;
	
}

.transcript-float-video{
	z-index : 2;
	background-color: gray;
	opacity: 50;
}


</style>
</head>
<body>
<div class="container"> </div>
	<div class="mdl-grid" id="infoRow">
		<div class="mdl-cell mdl-cell--4-col">
		
		<b>Keyboard controls</b> 
		
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
		<div class="mdl-cell mdl-cell--4-col" id="playerSection">
			
			
			<div id="YouTube-player">
			<div class="transcript-float-video">
					test
			</div>
			
			</div>
			
	        <span class="nowrap margin-left-m margin-right-m">
	          <label for="YouTube-video-id">videoId</label>:
	          <input id="YouTube-video-id" type="text" value="i0p1bmr0EmE" size="12" pattern="[_\-0-9A-Za-z]{11}" onchange="youTubePlayerChangeVideoId();">
	        </span>
			
	        <span class="nowrap">
	          <button onclick="youTubePlayerPlay();">Play</button>
	          <button onclick="youTubePlayerPause();">Pause</button>
	          <button onclick="youTubePlayerStop();">Stop</button>
	        </span>
	        
	
		</div>
		<div class="mdl-cell mdl-cell--4-col">
			 <div>
		        <input id="YouTube-player-volume" type="range" value="50" min="0" max="100" onchange="youTubePlayerVolumeChange(this.value);">
		        <label for="YouTube-player-volume">volume</label>
	    	  </div>
    	</div>

  
		
      </div>

	<div id="playRow" class="mdl-grid">
	 
        <label for="YouTube-player-progress">duration</label>
	
		<div id="playDiv">
			<input id="YouTube-player-progress" type="range" value="0" min="0" max="100" onchange="youTubePlayerCurrentTimeChange(this.value);" oninput="youTubePlayerCurrentTimeSlide();">	
		</div>
	</div>
	<div id="editRow" class="mdl-grid">
		<div id="transcripts" class="mdl-cell mdl-cell--4-col">
		
		</div>
		<div id="insert" class="mdl-cell mdl-cell--4-col">
			<div id="genSubtitle"> + New subtitle </div>
		</div>
		<div id="notes" class="mdl-cell mdl-cell--4-col">
		
		</div>
	</div>

<script type="text/javascript">

$('#genSubtitle').on('click', function(){
	
	$.ajax({
		url:"replyList",
		type : "GET",
		data : {
			boardnum : boardnum
		},
		dataType : "json", 
		success : function(list){
		
			var str = '';
			$.each(list, function(index, item){ 
				str += '<div class="row">';
				str += '<div class="mdl-cell mdl-cell--4-col"></div>';
				str += '<div class="col-md-8 text-left"><b>'+this.id+'</b></div><div class="col-md-2"></div>';
				str += '</div>';
				str += '<div class="row">';
				str += '<div class="col-md-2"></div>';
				str += '<div class="col-md-6">';
				str += '<textarea class="form-control" readonly="readonly" id="textArea'+this.replynum +'">'+ this.text +'</textarea></div>';
				
			});
				
			
			$('#replyDiv').html(str);
	
	
	
	
});


</script>


<script type="text/javascript">
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
                                   playerVars: { 'wmode' : "transparent",
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
    
    
    $('iframe').each(function(){
        var url = $(this).attr("src");
        var char = "?";
        if(url.indexOf("?") != -1){
                var char = "&";
         }
       
        $(this).attr("src",url+char+"wmode=transparent");
  });
 
    
    $('#player').css("z-index","0");
    
    var str = '';
	
		str += '<div class="mdl-cell" style="{z-index : 2; position : relative}">';
		str += '안녕하세요'
		str += '</div>';

	
	
	$('#YouTube-player').html(str);

    
    
    
    
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
        setInterval(youTubePlayerDisplayInfos, 100);
    }


    if (window.addEventListener) {
        window.addEventListener('load', init);
    } else if (window.attachEvent) {
        window.attachEvent('onload', init);
    }
}());




</script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"> </script>
<script type="text/javascript">

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
	});


	/* $(function(){
	//	var e = $.Event("keydown",{keyCode: 64});
		
		var code = e.keyCode || e.which;
		 if(code == 37) { //Enter keycode
			 $("#playDiv").animate({"left":"+=10px"},"slow");
		 }else if(code == 39 ){
			 $("#playDiv").animate({"right":"+=10px"},"slow");
		 }
		
		
		
		
	});
	
	
	
	
	
	
	
	function keyEvent(el, type, keyCode) {
		if('createEvent' in document) {
			var e = document.createEvent('HTMLEvents');
			e.keyCode = keyCode;
			e.initEvent(type, false, true);
			el.dispatchEvent(e);
			
		}else {
			var e = document.createEventObject();
			e.keyCode = keyCode;
			e.eventType = type;
			el.fireEvent('on'+e.eventType, e);
		}
		
	} */
</script>



</body>
</html>