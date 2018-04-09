<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<script type="text/javascript"
	src="<c:url value="/resources/jquery/jquery-3.1.1.js"></c:url>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
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
</head>
<body>
	<h1>하이</h1>
	<p><a href="member/user_home_test">홈페이지]</a>  </p>
	
	
</body>
</html>

