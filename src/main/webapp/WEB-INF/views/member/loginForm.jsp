<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
#head{
	text-align: center;
	}
	#main {
		
		margin: 5% auto;
		width: 50%
	}
	
	#t {
		margin: 5% auto;
		background-color: #E7DCEF;
	}
	body{
	
	}
</style>
</head>

<body>


<jsp:include page="../navi_side_bar.jsp"></jsp:include>


<div id = "main">
<form action="login", method="post" onsubmit="return formCheck()">
<table border="1" id="t">
	<tr>
		<th>ID</th>
		<td>
			<input type="text" name="member_id">
		</td>
	</tr>
	<tr>
		<th>password</th>
		<td>
			<input type="password" name="member_password">
		</td>
	</tr>
</table>
<div align="center">
	<input type="submit" value="로그인">
</div>
</form>
<p>${msg}</p>
</div>

<jsp:include page="../navi_side_bar_bot.jsp"></jsp:include>