<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	 <jsp:include page="../navi_side_bar.jsp"></jsp:include>
	 <!-- 여기부터 시작 -->
	
      <main class="mdl-layout__content mdl-color--grey-100">
        <div class="mdl-grid demo-content">
			<div class="mdl-color--white mdl-shadow--2dp mdl-cell mdl-cell--12-col mdl-grid">
				<c:forEach var="list" items="${category }" >
				
				<div class="demo-card-square mdl-card mdl-shadow--2dp">
					<div class="mdl-card__title mdl-card--expand">
					<h2 class="mdl-card__title-text"><img src="${list.thumbnail }" width="100%" height="100%"> </h2>
					</div>
					<div class="mdl-card__supporting-text">
					${list.contents_title }
					</div>
					<div class="mdl-card__actions mdl-card--border">
					<a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
					View Videos
					</a>
					</div>
				</div>
				
				</c:forEach>					
			</div>
        </div>
       </main>
        	
	<!-- 여기까지가 페이지의 코드 -->
    <jsp:include page="../navi_side_bar_bot.jsp"></jsp:include>