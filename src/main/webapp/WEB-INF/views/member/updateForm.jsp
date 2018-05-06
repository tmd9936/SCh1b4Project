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


<title>[ 会員登録 ]</title>
<style>
body {
	text-align: center;
}
.location{
	width: 300px;
	height: 50px;
}
.locationDiv{
	height: 100px;
}
#sub{
	width: 300px;
	height: 50px;
	font-size: 20px;
	font-weight: bold;
	
}
</style>
<script type="text/javascript">
function formCheck() {
	//submit 버튼 클릭시
	var password = document.getElementById("password");
	var password2 = document.getElementById("password2");
	
	if(password.value ==''){
		alert("パスワードを入力");
		password.focus();
		return false;

	}else if(password.value.length < 3 || password.value.length > 10){
		alert("パスワードは 3~10文字以内となります。");
		password.value='';
		password.focus();
		return false;
	}
	
	if(password2.value == ''){
		alert("パスワード確認を入力");
		password2.focus();
		return false;
		
	}else if(password.value != password2.value){
		alert("同じパスワードを入力");
		password2.value = '';
		password2.focus();
		return false;
	
	}

	return true;
	
}
</script>
</head>

<header>
	<br><br><br>
	<a href="../"><img src="<c:url value="/resources/images/2_Flat_logo_on_transparent_248x68.png" />" class="demo-avatar"></a>
	<br><br><br>
</header>

<body>

<div id="main">
	<form action="update" method="post" onsubmit="return formCheck()">
		<div class="mdl-textfield mdl-js-textfield">
			<br>
				<div class="mdl-textfield mdl-js-textfield">
					<input class="mdl-textfield__input" type="password" id="password1" name="member_password">
					<label class="mdl-textfield__label" for="password1">パスワード</label>
				</div>
				<br>
				<div class="mdl-textfield mdl-js-textfield">
					<input class="mdl-textfield__input" type="password" id="password2">
					<label class="mdl-textfield__label" for="password2">パスワード確認</label>
					<div id="passwordText2"></div>
				</div>
			<div align="center">
				<input type="submit" value="修正">
				<input type="submit" value="書き直し">
			</div>
		</div>
	</form>
</div>
</body>
</html>	
	
	