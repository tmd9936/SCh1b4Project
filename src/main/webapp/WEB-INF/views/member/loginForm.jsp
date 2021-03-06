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
<style type="text/css">
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

</head>

<header>
	<br><br><br>
	<a href="../"><img src="<c:url value="/resources/images/2_Flat_logo_on_transparent_248x68.png" />" class="demo-avatar"></a>
	<br><br><br>
</header>

<body>


	<div id = "main">
		<form action="login", method="post" onsubmit="return formCheck()" >
		<div class="mdl-textfield mdl-js-textfield">
			<input class="mdl-textfield__input" type="text" id="id" name="member_id"> <label
				class="mdl-textfield__label" for="id">ID</label>
			<div id="idText"></div>
		</div>
		<br>
		<div class="mdl-textfield mdl-js-textfield">
			<input class="mdl-textfield__input" type="password" id="password1" name="member_password">
			<label class="mdl-textfield__label" for="password1">パスワード</label>
		</div>
		<br>
		
		<input type="submit" id="sub" value="ログイン" class="mdl-button mdl-js-button">
		<div id="joinDiv"></div>
		</form>
		<p>${msg}</p>
	</div>
</body>	
</html>
	