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
	<c:choose>
	<c:when test="${sessionScope.loginId == null }">
	<p>Guest 환영합니다.</p>
		<form action="ytDown" method="get">
			주소 : <input type="text" name="youtube" id="youtube">
		<input type="submit" value="완료">
		</form>
		<input type="button" id="subtitle" value="자막얻기">
		
		<ul>
			<li><a href="member/joinForm">회원가입 이동</a></li>
			<li><a href="member/loginForm">로그인</a></li>
		</ul>
	</c:when>
	<c:otherwise>
		<p>${sessionScope.loginName }(${sessionScope.loginId })님 환영합니다.</p>
		<p>포인트 : ${sessionScope.point }</p>
			<ul>
				
			</ul>
		</c:otherwise>
	</c:choose>
	
	
</body>
</html>

