<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">
<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>


<!-- ajax -->
<script type="text/javascript" src="<c:url value="/resources/js/jquery-3.2.1.js" />"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>studySpace</title>

<!-- js 적용  -->
<script type="text/javascript" src="<c:url value="/resources/javascript/studySpace.js"></c:url>"></script>

<!-- css 적용 -->
<link href ="<c:url value="/resources/css/studySpace.css"/>" type="text/css" rel="stylesheet">



</head>
<body>

  
  <div class ="a">



		<!--동영상 부분, 관계 영상 리스트, 댓글 부분 전체를 묶는 div태그  -->
		
		<div class="centralView">
		
		
		
		  
	    <!--오른쪽 리스트 클릭시 새롭게 생성되는 부분   -->
	    <div id="divNewGSTL" class="secondView">
		
		
		</div>
		
		
		<div class="RightList">	
	<!--교육 메뉴  -->	
	  <div class="LearningList1" id="LearningList1" 
	  
	  
	  
	  onclick="javascript:WatchTheVideo()">
	  
	  <span class="Watch_the_video"><!--이미지 넣을 곳   --></span>
	  								<!-- <img src="<c:url value="/resources/images/if_icon-play.jpg" />" > -->
	  
	  Watch the video
	  </div>
	  
	  <div class="LearningList2" id="LearningList2" onclick ="javascript:LearnTheWords()">Learn the words</div>
	  
	  
	  <div class="LearningList3"  id="LearningList3" onclick="javascript:GoSpeakTheLine()">Speak the lines</div>
	  
	  <div class="LearningList4"  id="LearningList4" onclick="javascript:GoLive()">GoLive!</div>
	  
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
	
	<!--좋아요 하트 표시  -->
	<div class="ViewLikes">
	What Time is?
	</div>
	
	<!-- 소셜미디어 링크  --> 
	<div class="ToFaceBook">
	공유하기
	</div>
	
	</div>
	<!-- 왼쪽 리스트  끝-->
	
      </div>
      <!-- 동영상, 교육메뉴, 관련 영상 리스트 종료  -->
	
		
	

		  <!--관련된 동영상 리스트  시작-->  	
	 <div class="KindOfStudy" id="KindOfStudy">
	 
	 <h5>연관 비디오</h5>
	 			
	 	<div class="KindOfStudy_List">
	 	
        <span class="nowrap">
          <button onclick="document.getElementById('YouTube-video-id').value='dNXcT_LsZoE'; youTubePlayerChangeVideoId();" title="In the summertime (Mungo Jerry)">1</button>
          <button onclick="document.getElementById('YouTube-video-id').value='F2Ug3VkFuDw'; youTubePlayerChangeVideoId();" title="Peter Popoff pris la main dans le sac par James Randi">2</button>
          <button onclick="document.getElementById('YouTube-video-id').value='1X9RvuSuU_Y'; youTubePlayerChangeVideoId();" title="The Steven Seagal Show #002">3</button>
          <button onclick="document.getElementById('YouTube-video-id').value='LHdsMXEwLNE'; youTubePlayerChangeVideoId();" title="The Man from Earth (Richard Schenkman)">4</button>
        </span>
	 	
	 	
	 	 <span class="nowrap margin-left-m margin-right-m">
          <label for="YouTube-video-id"></label>
          <input id="YouTube-video-id" type="hidden" value="dNXcT_LsZoE" size="12" pattern="[_\-0-9A-Za-z]{11}" onchange="youTubePlayerChangeVideoId();">
        </span>

      <!--   <span class="nowrap">
          <button onclick="youTubePlayerPlay();">Play</button>
          <button onclick="youTubePlayerPause();">Pause</button>
          <button onclick="youTubePlayerStop();">Stop</button>
        </span> -->
	 
	 
	 	</div>
	 
	 </div>
	<!--관련된 동영상 리스트 종료  -->		
		
		
		
		<!-- 댓글 시작  -->
		<div class="ShowReplyList">
		
		
		<!-- 댓글 개수 -->
		<div class="ShowReplyLeft">
		<h5>댓글 ~개 </h5>
		</div>
		
		<div class="ShowReplyRight">
		<h5>검색 <select>
		<option id="search" value="member_id">아이디</option>
		<option id="search" value="reply_text">내용</option>
		<option id="search" value="inputdate">일자</option>
		</select>
		</h5> 
		
		
		
		</div>
		
		
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
			
			
		<div class="replyList" id ="replyList">
	 
		</div>	
	    

</div>

</div>
<!-- 동영상 부분, 관계 영상 리스트, 댓글 부분 전체를 묶는 div태그 종료 -->

</div>

<!-- 가운데 정렬  -->

  
  
  





</body>
</html>