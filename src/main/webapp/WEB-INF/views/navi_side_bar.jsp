<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<!-- 
  Material Design Lite 
  Copyright 2015 Google Inc. All rights reserved. 
  Licensed under the Apache License, Version 2.0 (the "License"); 
  you may not use this file except in compliance with the License. 
  You may obtain a copy of the License at 
      https://www.apache.org/licenses/LICENSE-2.0 
  Unless required by applicable law or agreed to in writing, software 
  distributed under the License is distributed on an "AS IS" BASIS, 
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
  See the License for the specific language governing permissions and 
  limitations under the License 
-->
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description"
	content="A front-end template that helps you build fast, modern mobile web apps.">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
<title>日本語 Edu Center</title>

<!-- Add to homescreen for Chrome on Android -->
<meta name="mobile-web-app-capable" content="yes">
<link rel="icon" sizes="192x192" href="images/android-desktop.png">

<!-- Tile icon for Win8 (144x144 + tile color) -->
<meta name="msapplication-TileImage"
	content="images/touch/ms-touch-icon-144x144-precomposed.png">
<meta name="msapplication-TileColor" content="#3372DF">

<link rel="shortcut icon" href="images/favicon.png">

<!-- SEO: If your mobile URL is different from the desktop URL, add a canonical link to the desktop page https://developers.google.com/webmasters/smartphone-sites/feature-phones -->
<!-- 
    <link rel="canonical" href="http://www.example.com/"> 
    -->

<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet"
	href="https://code.getmdl.io/1.3.0/material.cyan-light_blue.min.css">
<link rel="stylesheet"
	href="<c:url value="/resources/css/styles.css"></c:url>">
<script type="text/javascript" src="<c:url value="/resources/js/jquery-3.2.1.js" />"></script> 	

<!-- Square card -->
<style>
.demo-card-square.mdl-card {
	width: 310px;
	height: 260px;
	margin: 5px 14px;
}

.demo-card-square>.mdl-card__title {
	color: #fff;
	
}
</style>
</head>
<body>
	<div class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header">
		<header
			class="demo-header mdl-layout__header mdl-color--grey-100 mdl-color-text--grey-600">
			<div class="mdl-layout__header-row">
				<span class="mdl-layout-title">
					<br><br><br>
					<br><br><br>
				</span>
				<img src="<c:url value="/resources/images/2_Flat_logo_on_transparent_248x68.png" />">
				<div class="mdl-layout-spacer"></div>
				<div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable">
					<label class="mdl-button mdl-js-button mdl-button--icon"
						for="search"> <i class="material-icons">search</i>
					</label>
					<div class="mdl-textfield__expandable-holder">
						<input class="mdl-textfield__input" type="text" id="search">
						<label class="mdl-textfield__label" for="search">Enter
							your query...</label>
					</div>
				</div>
			</div>
		</header>
		
		<div
			class="demo-drawer mdl-layout__drawer mdl-color--blue-grey-900 mdl-color-text--blue-grey-50">
			<header class="demo-drawer-header">
			
			<!-- Login -->
			<div>
				<div id="profile" style="float: left;width: 40%; margin: 5px 5px;" >
					<img src="<c:url value="/resources/images/user.jpg" />" class="demo-avatar">
				</div>
			<c:if test="${sessionScope.loginId != null }">
				<div id="inform" style="float: left;width: 30%; margin: 5px 5px;">	
					<span>${sessionScope.loginName}</span><br>
					<span>${sessionScope.point}</span>
				</div>
				<div class="demo-avatar-dropdown" style="float: left;width: 10%">
					<span> </span>
					<div class="mdl-layout-spacer"></div>
					
					<button id="accbtn"
						class="mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--icon">
						<i class="material-icons" role="presentation">arrow_drop_down</i>
						<span class="visuallyhidden">Accounts</span>
					</button>
					 <ul class="mdl-menu mdl-menu--bottom-right mdl-js-menu mdl-js-ripple-effect" for="accbtn">
		              <li class="mdl-menu__item"><a href="../member/updateForm">정보수정</a></li>
		              <li class="mdl-menu__item"><a href="../member/logout"> 로그아웃</a></li>
		            </ul>
				</div>
			</c:if>
			
			<c:if test="${sessionScope.loginId == null }">	
				<div id="inform" style="float: left;width: 30%; margin: 5px 5px;">	
					<span> <a href="../member/loginForm">로그인</a></span><br>
					<span><a href="../member/joinForm">회원가입</a></span>
				</div>
			</c:if>	
			</div>
			
				
			</header>
			<nav class="demo-navigation mdl-navigation mdl-color--blue-grey-800">
				<a class="mdl-navigation__link" href=""><i
					class="mdl-color-text--blue-grey-400 material-icons"
					role="presentation">home</i>Home</a> <a class="mdl-navigation__link"
					href=""><i class="mdl-color-text--blue-grey-400 material-icons"
					role="presentation">inbox</i>Inbox</a> <a class="mdl-navigation__link"
					href=""><i class="mdl-color-text--blue-grey-400 material-icons"
					role="presentation">delete</i>Trash</a> <a class="mdl-navigation__link"
					href=""><i class="mdl-color-text--blue-grey-400 material-icons"
					role="presentation">report</i>Spam</a> <a class="mdl-navigation__link"
					href=""><i class="mdl-color-text--blue-grey-400 material-icons"
					role="presentation">forum</i>Forums</a> <a class="mdl-navigation__link"
					href=""><i class="mdl-color-text--blue-grey-400 material-icons"
					role="presentation">flag</i>Updates</a> <a class="mdl-navigation__link"
					href=""><i class="mdl-color-text--blue-grey-400 material-icons"
					role="presentation">local_offer</i>Promos</a> <a
					class="mdl-navigation__link" href=""><i
					class="mdl-color-text--blue-grey-400 material-icons"
					role="presentation">shopping_cart</i>Purchases</a> <a
					class="mdl-navigation__link" href=""><i
					class="mdl-color-text--blue-grey-400 material-icons"
					role="presentation">people</i>Social</a>
				<div class="mdl-layout-spacer"></div>
				<a class="mdl-navigation__link" href=""><i
					class="mdl-color-text--blue-grey-400 material-icons"
					role="presentation">help_outline</i><span class="visuallyhidden">Help</span></a>
			</nav>
		</div>