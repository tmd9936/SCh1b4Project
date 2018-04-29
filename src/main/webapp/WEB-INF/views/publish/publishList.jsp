<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<jsp:include page="../navi_side_bar.jsp"></jsp:include>
	<style type="text/css">
		.mdl-card__media{
			cursor: pointer;
		}
		.mdl-card__title-text{
			cursor: pointer;
		}
	 </style>
	<script type="text/javascript">
	$(function(){
		$('.article-image').on('click',function(){
			var contents_num = $(this).attr('value');
			location.href = '../transcript/goEditSpace?contents_num='+contents_num;
			
		});
		$('.mdl-card__title-text').on('click',function(){
			var contents_num = $(this).attr('value');
			location.href = '../transcript/goEditSpace?contents_num='+contents_num;
			
		});
	});
	
	</script>	
	
    <!-- 여기부터 시작 -->
  	<main class="mdl-layout__content mdl-color--grey-100">
        <div class="mdl-grid demo-content">
        	
		<c:choose>
        	
        	<c:when test="${sessionScope.loginId == null }">	
				<h1>로그인 이후 사용해 주세요</h1>        	
        	</c:when>
        	
        	<c:otherwise>
        	<h4>자막 편집</h4>
            <c:forEach var="list" items="${pubList }" varStatus="status">
        	<div class="mdl-grid mdl-cell mdl-cell--12-col mdl-cell--4-col-tablet mdl-card mdl-shadow--4dp">
                    <div class="mdl-card__media mdl-cell mdl-cell--10-col-tablet">
                        <img class="article-image" src=" ${list.thumbnail} " border="0" value="${list.contents_num}">
                    </div>
                    
                    <div class="mdl-cell mdl-cell--8-col">
                        <h2 class="mdl-card__title-text" value="${list.contents_num}">${list.contents_title }</h2>
                        <div class="mdl-card__supporting-text padding-top"></div>
                        <div class="mdl-card__supporting-text no-left-padding">
                            <span>Category : ${list.category }</span>
                        </div>
                    </div>
                </div>
     		</c:forEach>
   			</c:otherwise>
    	</c:choose>
  	 	</div>
    </main>
    <!-- 여기까지가 페이지의 코드 -->
    <jsp:include page="../navi_side_bar_bot.jsp"></jsp:include>
    