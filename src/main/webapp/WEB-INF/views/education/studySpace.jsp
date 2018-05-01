<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/study.css"></c:url>"> 
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">
<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>

<script type="text/javascript" async src="http://www.google-analytics.com/ga.js"></script>


<!--소켓 -->
<script src="https://rtcmulticonnection.herokuapp.com/dist/RTCMultiConnection.min.js"></script>
<script src="https://rtcmulticonnection.herokuapp.com/socket.io/socket.io.js"></script>

<script type="text/javascript" src="<c:url value="../resources/js/jquery-3.1.1.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="../resources/js/sockjs.js"/>"></script>

<!--소켓 시작  -->
<script type="text/javascript">

<!--소켓 끝  -->
	</script>


<meta content="initial-scale=1, minimum-scale=1, width=device-width"
	name="viewport">
<meta
	content="Google Chrome is a browser that combines a minimal design with sophisticated technology to make the web faster, safer, and easier."
	name="description">
<link href="https://plus.google.com/100585555255542998765"
	rel="publisher">
	
<!-- jquery -->
<script type="text/javascript" src="<c:url value="/resources/js/jquery-3.2.1.js" />"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>studySpace</title>
<style type="text/css">
	.script-bar{
		cursor: pointer;
	}
	.script-bar:hover {
		background-color: green;
	}
</style>


<!-- tsList가져오기 -->
<script type="text/javascript">
	var allTime = parseInt('${allTime}');
	var tsList = new Array();
	var tslist = new Array();
	var contents_num = '${contents.contents_num}';
	$(function(){		
		
		
		//<div class="script-bar" style="left:40%;width: 30%;" width="30" start="30" dur="6"></div>
		console.log(tsList);
		
		
		
		<c:forEach items="${tsList }" var="ts">
			var json = new Object();
			json.contents_num = "${ts.contents_num}";
			json.ts_start = '${ts.ts_start}';
			json.ts_num = "${ts.ts_num}";
			json.ts_dur= "${ts.ts_dur}";
			json.ts_text = "${ts.ts_text}";
			tsList.push(json);
		</c:forEach>
		

		<c:forEach items="${tslist }" var="ts">
			var json = new Object();
			json.contents_num = "${ts.contents_num}";
			json.ts_start = '${ts.ts_start}';
			json.ts_num = "${ts.ts_num}";
			json.ts_dur= "${ts.ts_dur}";
			json.ts_text = "${ts.ts_text}";
			tslist.push(json);
		</c:forEach>
		console.log("10문제용 tslist확인:"+tslist);
		var str = "";
		var container;
		var left = 0;
		var width = 0;
		
		$.each(tsList,function(index,ts){
			left = (parseFloat(ts.ts_start)/allTime)*100;
			width = (parseFloat(ts.ts_dur)/allTime)*100;
			str = $('.seek-bar-container').html();
			str += "<div class ='script-bar' style='left:"+left+"%;width:"+width+"%;' start ='"+ts.ts_start+"' num = '"+ts.ts_num+"' dur='"+ts.ts_dur+"' left='"+left+"' text='"+ts.ts_text+"'></div>";
			$('.seek-bar-container').html(str);
		});
	});
	
</script>

<!-- js 적용  -->
<script language=JavaScript src="<c:url value="/resources/javascript/studySpace.js" ></c:url>" charset='UTF-8'></script>
<script type="text/javascript" src="<c:url value="/resources/javascript/studySpace.js" ></c:url>" charset='UTF-8'></script>

<!-- css 적용 -->
<link href ="<c:url value="/resources/css/studySpace.css"/>" type="text/css" rel="stylesheet">
</head>
<body>


<input type="hidden" id="filename" class="filename" value="${filename}">
<input type="hidden" name="contents_num" value="${contents.contents_num }" id="contents_num">
<input type="hidden" name="start" value="12" id="start">
<input type="hidden" name="dur" value="4" id="dur">  

	<ul id="recordingslist"></ul>

	<pre id="log"></pre>
<!-- <button id="startBtn" onclick="startRecording(this);">시작</button>
	<button id="endBtn" onclick="stopRecording(this);" disabled>종료</button>
	<div id="ytPitch" style="height: 250px; width: 40%;" class="pitchContainer"></div>
	
	<div id="memPitch" style="height: 250px; width: 40%;" class="pitchContainer"></div> -->



		<dialog class="mdl-dialog" id="percentDialog">
			<h4 class="mdl-dialog__title">결과</h4>
			<div class="mdl-dialog__content">
     		 	<table class="mdl-data-table mdl-js-data-table">
     		 		<tr>
     		 			<th>Text</th>
     		 			<td class="textPercent"> </td>
     		 		</tr>
     		 		<tr>
     		 			<th>Pitch</th>
     		 			<td class="pitchPercent"> </td>
     		 		</tr>
     		 	</table>
     		 	<div id="ytPitch" style="height: 250px; width: 90%;" class="pitchContainer"></div>
						
				<div id="memPitch" style="height: 250px; width: 90%;" class="pitchContainer"></div>
    		</div>
    		<div class="mdl-dialog__actions">
      			<button type="button" class="mdl-button close" id="closeButton">Close</button>
    		</div>
		</dialog>


		<!--동영상 부분, 관계 영상 리스트, 댓글 부분 전체를 묶는 div태그  -->
		
		<main class="mdl-layout__content mdl-color--grey-100">
        	<div class="mdl-grid demo-content"  style="max-width: 1500px;margin-right: 0px;margin-left: 0px;">
				<div class="centralView" style="margin: auto;" align="center">
			
		  
	    <!--오른쪽 리스트 클릭시 새롭게 생성되는 부분   -->
	    <div id="divNewGSTL" class="secondView">

	    	<div class="divNewView" id="divNewView" >

	    	</div>
	    	
			<div class="speachView">
				<div class="speachText">
				
				</div>
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
				<div class="browser-landing" id="main">
					<div class="compact marquee">
						<div class="startEndBtn" style="visibility: hidden;">
							<button id="startBtn" onclick="startRecording(this);" class="mdl-button mdl-js-button">시작</button>
							<button id="endBtn" onclick="stopRecording(this);" disabled class="mdl-button mdl-js-button">종료</button>
						</div>
						
						<div id="results" >
							<span class="final" id="final_span"></span> <span class="interim"
								id="interim_span"></span>
						</div>
					
						<ul id="recordingslist"></ul>
					
						<pre id="log"></pre>
						
						<input type="hidden" id="streamVoice" name="voice">
						
						<!-- 피치 컨테이너 -->
						
						<!-- 피치 컨테이너 -->
						<div class="perContainer"></div>
						
						
					</div>
				</div>
			</div>
		
		</div>
		
		
		<div class="RightList">	
	<!--교육 메뉴  -->	

	  <div class="LearningList1" id="LearningList1" style="color: white; background-color: blue;" align="center"  

	  onclick="javascript:WatchTheVideo()">
	  
	  <span class="Watch_the_video"><!--이미지 넣을 곳   --></span>
	  								<!-- <img src="<c:url value="/resources/images/if_icon-play.jpg" />" > -->
	  Watch the video
	  </div>
	  
	  <div class="LearningList2" id="LearningList2" style="color: white; background-color: red;" align="center"
	  onclick ="javascript:LearnTheWords(tslist)">Learn the words</div>
	  

	  
	  <div class="LearningList3"  id="LearningList3" style="color: white; background-color: rgb(0, 166, 140);" align="center" 
	  onclick="javascript:GoSpeakTheLine()">Speak the lines</div>
	  
	  <div class="LearningList4"  id="LearningList4" style="color: white; background-color: maroon;" align="center"  
	  onclick="javascript:GoLive()">GoLive!</div>
	  

	  <div class="Voca&Plan"> 
	  
	  <div class="VocabQuiz"  id="VocabQuiz">VocabQuiz</div>
	  
	   <div class="LessonPlan" id="LessonPlan">LessonPlan</div>
	  </div>
	   
	  			
	</div>
	
	
		<!--동영상, 교육메뉴, 관련 영상 리스트 시작  -->
	    <div class="center" >
    
	<!--동영상 재생 부분  -->
	<div  class ="YouTube-player" id="YouTube-player"> 
      
     </div>	
		
		
		
	
	<!-- 왼쪽 리스트  시작-->
	<div class="LeftList">
	<div class="seek-bar-container">
	 		<div class="seek-crub-container">
	 			<div class="seek-crub"></div>
	 		</div>	
	</div>
	
	
	
	
	<div class="Likes&faceBook">
	<!-- seekbar로 현재 동영상 위치 나타내기 -->
	 	
	<!-- What Time is?
	</div>
	<div>
		<div>
        <input  id="YouTube-player-progress" type="range" value="0" min="0" max="100" onchange="youTubePlayerCurrentTimeChange(this.value);" oninput="youTubePlayerCurrentTimeSlide();" >
        <label for="YouTube-player-progress">dur</label>
    </div>	
	</div> -->
	
	
	<!--좋아요 하트 표시  -->
	<img class="thumbsUp" id="thumbsUp" src="/www/resources/icon/star_before.svg">
									 
	
	<div class ="recommendCountDiv" id="recommendCountDiv">
	
	</div>
	<!-- 북마크 -->
	

	<img class="bookMark" id="bookMark" src="/www/resources/icon/bookmark_border_black.svg">
	
	

	
	</div>
	<!-- 왼쪽 리스트  끝-->
	
      </div>
      <!-- 동영상, 교육메뉴, 관련 영상 리스트 종료  -->
	
		
	

		  <!--관련된 동영상 리스트  시작-->  	
	  
	<div class="KindOfStudy" id="KindOfStudy">
	 

	 	<div class="KindOfStudy_List">
	
	 <!-- 	
        <span class="nowrap">
          <button onclick="document.getElementById('YouTube-video-id').value='dNXcT_LsZoE'; youTubePlayerChangeVideoId();" title="In the summertime (Mungo Jerry)">1</button>
          <button onclick="document.getElementById('YouTube-video-id').value='F2Ug3VkFuDw'; youTubePlayerChangeVideoId();" title="Peter Popoff pris la main dans le sac par James Randi">2</button>
          <button onclick="document.getElementById('YouTube-video-id').value='1X9RvuSuU_Y'; youTubePlayerChangeVideoId();" title="The Steven Seagal Show #002">3</button>
          <button onclick="document.getElementById('YouTube-video-id').value='LHdsMXEwLNE'; youTubePlayerChangeVideoId();" title="The Man from Earth (Richard Schenkman)">4</button>
        </span>
	 	 -->

	 	<div class="youtubeFrame">
		 	 <span class="nowrap margin-left-m margin-right-m">
	          <label for="YouTube-video-id"></label>
	          <input id="YouTube-video-id" type="hidden" value="${ytName}" size="12" pattern="[_\-0-9A-Za-z]{11}" onchange="youTubePlayerChangeVideoId();">
	        </span>
	 	</div>
	 	

      <!--   <span class="nowrap">
          <button onclick="youTubePlayerPlay();">Play</button>
          <button onclick="youTubePlayerPause();">Pause</button>
          <button onclick="youTubePlayerStop();">Stop</button>
        </span> -->
	 
	 
	 	</div>
	 	
	 	
	 		
	 </div> 
	
	 
	 
	<!--관련된 동영상 리스트 종료  -->		
		
		
		

</div>
<!-- 동영상 부분, 관계 영상 리스트, 댓글 부분 전체를 묶는 div태그 종료 -->



</div>



<!-- 댓글 시작  -->
		<div class="ShowReplyList">
		
		
		
		<div class="ShowReplyLeft">
		
<div class="insertReply">
	
		
		<form id="insertContentsReply" >
			<input type="hidden" id="member_id" value="${sessionScope.userId }"> 
			<input type="hidden" id="contents_num" value="">
			
			<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
		<input class="mdl-textfield__input" type="text" id="reply_text">
		<label class="mdl-textfield__label" for="text">Text...</label>
		</div>
		<input type="button" id="formButton" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored" 
		value="댓글달기">
		</form> 
		
		 </div>
			
			
		<div class="replyList" id ="replyDiv">
	 			
	 			
	 			
	 			
		</div>	
		
		
		</div>
		
		
		<div class="ShowReplyRight">
		
		
		</div>
		
		
	    

</div>

</div>

</main>

<!-- 가운데 정렬  -->



<script type="text/javascript" src="<c:url value="/resources/javascript/webSpeech.js"></c:url>"></script>

<script src="<c:url value="/resources/js/recorder.js" />"></script>


<!-- 여기까지가 페이지의 코드 -->
</body>
</html>