<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<style type="text/css">
body{
	font-family:Microsoft YaHei;
}
.main{
	text-align:center;
	position:absolute;
	top:50%;
	left:50%;
	transform:translate(-50%,-50%);
	box-sizing:border-box;
}
.main .fa{
	font-size:5em;
}
.msg-container{
	border:1px solid black;
}
.msg-container h2{
	margin:10px 0;
	width:100%;
}
.msg-container p{
	border-top:1px solid black;
	padding:10px;
}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>[에러 페이지 ]</title>
</head>
<body>
	
	
	<div class="main">
	<i class="fa fa-exclamation-triangle"></i>
	<h1>Oops!We got an error</h1>
	<div class="msg-container">
		<h2>message</h2>
		<p><a href="../">홈페이지</a></p>
	</div>
	
</div>


	
	
</body>
</html>