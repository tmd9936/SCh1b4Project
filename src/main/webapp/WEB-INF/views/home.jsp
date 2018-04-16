<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<script type="text/javascript" src="<c:url value="/resources/jquery/jquery-3.1.1.js"></c:url>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js">
	$(function(){
		$("#subtitle").on("click",function(){
			$.ajax({
		        type: "POST",
		        url: "https://video.google.com/timedtext?lang=ja&v=ZF3y-kmSnTI"
		    }).done(function (response) {
		        console.log(response);
		    }).fail(function (response) {
		        console.log();
		    });
		});
	});
	

</script>
<script type="text/javascript">
function goSpeechTest() {
	location.href = "webSpeechTest";
}

</script>
</head>
<body>
	<h1>하이</h1>
	<form action="ytDown" method="get">
		주소 : <input type="text" name="youtube" id="youtube">
		<input type="submit" value="완료">
	</form>
	 <p>ㅎㅎ</p>
	<input type="button" id="subtitle" value="자막얻기">
	
	<input type="button" value="goVoiceRecording" onclick="goSpeechTest()">
	
	<a href="transcript/goEditSpace">editSpace</a> 
</body>
</html>

