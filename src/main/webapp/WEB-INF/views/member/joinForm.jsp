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
<script type="text/javascript">
	$(function(){
		var idcheck = false;
		var passcheck = false;
		var namecheck = false;
		
		$('#sub').on('click',function(){
			if(idcheck == true && passcheck == true && namecheck == true){
				$('#joinForm').submit();
			}
			else{
				alert(idcheck + " : "+passcheck + " : "+namecheck);
				$('#joinDiv').html('<h5>회원가입 실패</h5>');
			}
		});
		
		$('#password1').on('focusout', function(){
			var pass1 = $('#password1').val();
			var pass2 = $('#password2').val();
			
			if(pass1.length == 0 || pass2.length == 0){
				passcheck = false;
				$('#passwordText2').html('<p>패스워드를 입력하세요 !</p>');
				return;
			}
			
			if(!/^[a-zA-Z0-9]{6,15}$/.test(pass1)){
				passcheck = false;
				$('#passwordText2').html('<p>비밀번호는 영문과 숫자 조합으로 6~15자리 입니다.</p>');
				return;
			}
			
			if(pass1 != pass2){
				passcheck = false;
				$('#passwordText2').html('<p>패스워드가 일치하지 않습니다!</p>');
			}else{
				passcheck = true;
				$('#passwordText2').html('<p>패스워드가 일치합니다.</p>');		
			}
		});
		
		$('#password2').on('focusout', function(){
			var pass1 = $('#password1').val();
			var pass2 = $('#password2').val();
			
			if(pass1.length == 0 || pass2.length == 0){
				$('#passwordText2').html('<p>패스워드를 입력하세요 !</p>');
				return;
			}
			
			if(!/^[a-zA-Z0-9]{6,15}$/.test(pass1)){
				passcheck = false;
				$('#passwordText2').html('<p>비밀번호는 영문과 숫자 조합으로 6~15자리 입니다.</p>');
				return;
			}
			
			if(pass1 != pass2){
				passcheck = false;
				$('#passwordText2').html('<p>패스워드가 일치하지 않습니다!</p>');
			}else{
				passcheck = true;
				$('#passwordText2').html('<p>패스워드가 일치합니다.</p>');		
			}
		});
		
		
		$('#name').on('focusout',function(){
			var name = $('#name').val();
			if(name.length == 0){
				namecheck = false;
				$('#nameText').html('<p>이름을 입력하세요</p>');
			}else{
				namecheck = true;
				$('#nameText').html('<p>좋은 이름이네요!</p>');
			}
		});
		
		$('#id').on('keypress',function(){
			$('#idText').html('');
		});
		
		$('#id').on('focusout',function(){
			var id = $('#id').val();
			var regType1 = /^[A-Za-z0-9+]{4,12}$/; 
			if(id.length == 0){
				idcheck = false;
				$('#idText').html('<p>아이디를 입력하세요!</p>');
				return;
			}
			
			if(!regType1.test(id)){
				idcheck = false;
				$('#idText').html('<p>4~12자의 숫자와 영문만 입력하세요!</p>');
				return;
			}
			
			$.ajax({
				url : 'idCheck',
				type : 'post',
				dataType : "text",
				data : {
					member_id : id
				},
				success : function(str){
					if(str == "yes"){
						idcheck = true;
						$('#idText').html('<p>환영합니다.</p>');
					}else{
						idcheck = false;
						$('#idText').html('<p>아이디 중복입니다.</p>');
					}
				},
				error : function(err){
					console.log(err);
				}
			});	
		});
		
	});
	//아이디
	//이름(닉네임)
	//지역
</script>
</head>

<header>
	<br><br><br>
	<img src="<c:url value="/resources/images/2_Flat_logo_on_transparent_248x68.png" />" class="demo-avatar">
	<br><br><br>
</header>

<body>
	<div class="main">
		<form action="join" method="post" id="joinForm">
			<div class="mdl-textfield mdl-js-textfield">
				<input class="mdl-textfield__input" type="text" id="id" name="member_id"> <label
					class="mdl-textfield__label" for="id">아이디</label>
				<div id="idText"></div>
			</div>
			<br>
			<div class="mdl-textfield mdl-js-textfield">
				<input class="mdl-textfield__input" type="password" id="password1" name="member_password">
				<label class="mdl-textfield__label" for="password1">비밀번호</label>
			</div>
			<br>
			<div class="mdl-textfield mdl-js-textfield">
				<input class="mdl-textfield__input" type="password" id="password2">
				<label class="mdl-textfield__label" for="password2">비밀번호 확인</label>
				<div id="passwordText2"></div>
			</div>
			
			<br> 
			<div class="mdl-textfield mdl-js-textfield">
				<input class="mdl-textfield__input" type="text" id="name" name="member_name">
				<label class="mdl-textfield__label" for="name">이름(닉네임)</label>
				<div id="nameText"></div>
			</div>
			<br>
			<div class="locationDiv">
				<select name="nation" id="locations" class="nation" >
	      			<option selected="selected" >한국</option>
	      			<option>日本</option>
	      			<option>America</option>
	      			<option>Doichland</option>
	      			<option>中国</option>
	      			<option>Singapura</option>
	      			<option>England</option>
	      			<option>Nederland</option>
	      			<option>française</option>
	      			<option>Российская Федерация</option>
	    		</select>
			</div>
			<input type="button" id="sub" value="회원가입" class="mdl-button mdl-js-button">
			<div id="joinDiv"></div>
		</form>

	</div>
</body>	
</html>
	