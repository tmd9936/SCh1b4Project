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
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:300,400,500,700" type="text/css">
 
 
<!-- Tile icon for Win8 (144x144 + tile color) -->
<meta name="msapplication-TileImage"
  content="images/touch/ms-touch-icon-144x144-precomposed.png">
<meta name="msapplication-TileColor" content="#3372DF">
 
<link rel="shortcut icon" href="images/favicon.png">
 
<!-- SEO: If your mobile URL is different from the desktop URL, add a canonical link to the desktop page https://developers.google.com/webmasters/smartphone-sites/feature-phones -->
<!-- 
    <link rel="canonical" href="http://www.example.com/"> 
    -->
<link rel="stylesheet" href="<c:url value="/resources/css/layout.css" />"><link rel="stylesheet" href="<c:url value="/resources/css/swiper.css" />">
<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
<link rel="stylesheet"
  href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en">
<link rel="stylesheet"
  href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet"
  href="https://code.getmdl.io/1.3.0/material.cyan-light_blue.min.css">
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:300,400,500,700" type="text/css">
<link rel="stylesheet"
  href="<c:url value="/resources/css/styles.css"></c:url>">

<script type="text/javascript" src="<c:url value="/resources/js/jquery-3.2.1.js" />"></script>   
 


<style>
.material-icons.md-18 { font-size: 18px; }
.material-icons.md-24 { font-size: 24px; }
.material-icons.md-36 { font-size: 36px; }
.material-icons.md-48 { font-size: 48px; }
.demo-card-square.mdl-card {
  width: 320px;
  height: 260px;
  margin: 5px 14px;
}
.demo-card-square>.mdl-card__title {
  color: #fff;
  
}
.demo-card-image.mdl-card {
  width: 256px;
  height: 256px;
  background: url('../assets/demos/image_card.jpg') center / cover;
}
.demo-card-image > .mdl-card__actions {
  height: 52px;
  padding: 16px;
  background: rgba(0, 0, 0, 0.2);
}
.demo-card-image__filename {
  color: #fff;
  font-size: 14px;
  font-weight: 500;
}
.mdl-chip--contact{
  margin: 5px 15px;
}
.mdl-chip{
      cursor: pointer;
}
.mdl-textfield--floating-label{
  width: 750px;
}
.mdl-textfield__label{
  font-size: 10px;
}
.ytd_btn{
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  padding: 20px 60px; 
  outline: none;
  border: none;
  color: white;
  font-size: 18px;
  background: #2196F3;
  box-shadow: 0 1px 9px rgba(0,0,0,.66);
}
.article-image{
  width: 100%;
  height: 100%;
}
a {
text-decoration: none;
}
table {
  width: 100%;
   text-align: center;
}
header i{
margin-left: -24px;
    position: absolute
    }
 
 material-icons{
 color: white;
 }
 .arrow{
 	margin: 0px auto;
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
        <a href="../"><img src="<c:url value="/resources/images/2_Flat_logo_on_transparent_248x68.png" />" name="logo" class="logo"></a>
        <div class="mdl-layout-spacer"></div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable">
          <label class="mdl-button mdl-js-button mdl-button--icon" id="searchBtn" for="search">
            <i class="material-icons">search</i>
          </label>
          <div class="mdl-textfield__expandable-holder">
            <!-- 검색할 텍스트 입력 -->  
            <input class="mdl-textfield__input" type="text" id="search">
            <label class="mdl-textfield__label" for="search">
            <!-- 입력란 텍스트를 처리할 알고리즘 -->
                        <script type="text/javascript">
               $(function(){
                
                $('#searchBtn').on('click',function(){
                  if($('#search').val() == ''){
                    return;
                  }  
                  else{
                    /* alert($('#search').val()); */
                    var searchtext = $('#search').val();
                    location.href = 'searchtText?searchtext=' + searchtext;
                  }
                });
               });
            </script>
            
            </label>
            
          </div>
        </div>
      </div>
    </header>
    
     <div class="demo-drawer mdl-layout__drawer mdl-color--blue-grey-900 mdl-color-text--blue-grey-50">
      <header class="demo-drawer-header">
      
      <!-- Login -->
      <div>
      <c:if test="${sessionScope.loginId != null }">
        <div id="profile" style="float: left; width: 40%; margin: 5px 5px;" >
          <img src="<c:url value="/resources/images/user.jpg" />" class="demo-avatar">
        </div>
      
        <div id="inform" style="float: left;width: 30%; margin: 5px 5px;">  
          <span>${sessionScope.loginName}</span><br>
          <span>${sessionScope.point}</span>
        </div>
        <div class="demo-avatar-dropdown" style="float: left;width: 10%">
          <span> </span>
          <div class="mdl-layout-spacer"></div>
          
          <button id="accbtn"
            class="mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--icon">
            
            <i class="material-icons md-light arrow" role="presentation">arrow_drop_down</i>
            <span class="visuallyhidden">Accounts</span>
          </button>
           <ul class="mdl-menu mdl-menu--bottom-right mdl-js-menu mdl-js-ripple-effect" for="accbtn">
                  <li class="mdl-menu__item"><a href="../member/updateForm">정보수정</a></li>
                  <li class="mdl-menu__item"><a href="../member/logout"> 로그아웃</a></li>
                </ul>
        </div>
        </c:if>
      
      <c:if test="${sessionScope.loginId == null }">  
        <div id="inform" style="float: left;width: 100%; margin: 5px 5px;">  
          <table>
          <tr>
            <td><span> <a href="../member/loginForm"><i class="material-icons md-18">input</i>  로그인</a></span></td>
              <td><span><a href="../member/joinForm"><i class="material-icons md-18">face</i>  회원가입</a></span></td>
          </tr>
          </table>
        </div>
      </c:if>  
      </div>
      
      
          </header>
      <nav class="demo-navigation mdl-navigation mdl-color--blue-grey-800">
        <a class="mdl-navigation__link" href="../">
          <i class="mdl-color-text--blue-grey-400 material-icons"
          role="presentation">home</i>Home</a>
        <a class="mdl-navigation__link" href="../contents/ytDownPage">
          <i class="mdl-color-text--blue-grey-400 material-icons"  role="presentation">playlist_add</i>영상추가</a>
        <a class="mdl-navigation__link"  href="../contents/BookMark">
          <i class="mdl-color-text--blue-grey-400 material-icons"  role="presentation">bookmark</i>Bookmark</a>
        <a class="mdl-navigation__link"  href="../contents/publishList">
          <i class="mdl-color-text--blue-grey-400 material-icons"  role="presentation">flag</i>Edit</a>
        <a class="mdl-navigation__link"  href="">
          <i class="mdl-color-text--blue-grey-400 material-icons"  role="presentation">forum</i>Forums</a>
        
        
        
        <div class="mdl-layout-spacer"></div>
        <a class="mdl-navigation__link" href=""><i
          class="mdl-color-text--blue-grey-400 material-icons"
          role="presentation">help_outline</i><span class="visuallyhidden">Help</span></a>
      </nav>
    </div>