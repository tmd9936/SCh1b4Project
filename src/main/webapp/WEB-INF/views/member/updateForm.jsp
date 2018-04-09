<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet"
	href="https://code.getmdl.io/1.3.0/material.cyan-blue.min.css" />
<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.js"></c:url>"></script>


<title>[ 회원가입 ]</title>
<style>
	
	#main {
		margin: 5% auto;
		width: 50%;
		background-color: aqua; 
	}
	
	#t {
		background-color: white;
		margin: 5% auto;
		
	}
	body{
		background-size : contain;
		background-color: aqua;
	}

</style>
<script type="text/javascript">
function formCheck() {
	//submit 버튼 클릭시
	var password = document.getElementById("password");
	var password2 = document.getElementById("password2");
	
	if(password.value ==''){
		alert("패스워드를 입력해주세요");
		password.focus();
		return false;

	}else if(password.value.length < 3 || password.value.length > 10){
		alert("패스워드는 3~10글자로 입력해 주세요.");
		password.value='';
		password.focus();
		return false;
	}
	
	if(password2.value == ''){
		alert("비밀번호 확인을 입력해 주세요.");
		password2.focus();
		return false;
		
	}else if(password.value != password2.value){
		alert("동일한 비밀번호를 입력해주세요.");
		password2.value = '';
		password2.focus();
		return false;
	
	}

	return true;
	
}
</script>
</head>
<body>

<jsp:include page="../navi_side_bar.jsp"></jsp:include>
<div id="main">
<form action="update" method="post" onsubmit="return formCheck()">
	<table id="t">
		
		<tr>
			<th>비밀번호</th>	
			<td>
				<input type="password" name="member_password" id="password"><br>
				<input type="password" id="password2">
				<!--name 안만드는이유? 두개의 비번이 같다는 처리를 id로 해주면 되기때문 -->
			</td>
		</tr>
		
	</table>
	<div align="center">
		<input type="submit" value="수정">
		<input type="submit" value="다시쓰기">
	</div>
	</form>
</div>
	
	
<jsp:include page="../navi_side_bar_bot.jsp"></jsp:include>
	