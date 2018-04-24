<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<jsp:include page="../navi_side_bar.jsp"></jsp:include>
	
	<script type="text/javascript">
	
	
	$(function(){
			

	});
	
	</script>	
	
    <!-- 여기부터 시작 -->
  	<main class="mdl-layout__content mdl-color--grey-100">
        <div class="mdl-grid demo-content">
        	<c:if test="${sessionScope.loginId == null }">	
				<h1>로그인 이후 사용해 주세요</h1>        	
        	</c:if>
        	
            <c:forEach var="bookmark" items="${bookmarklist }" varStatus="status">
        	<div class="mdl-grid mdl-cell mdl-cell--12-col mdl-cell--4-col-tablet mdl-card mdl-shadow--4dp">
                    <div class="mdl-card__media mdl-cell mdl-cell--10-col-tablet">
                        <img class="article-image" src=" ${bookmark.thumbnail} " border="0" onclick="show_video">
                    </div>
                    
                    <div class="mdl-cell mdl-cell--8-col">
                        <h2 class="mdl-card__title-text">${bookmark.contents_title }</h2>
                        <div class="mdl-card__supporting-text padding-top"></div>
                        <div class="mdl-card__supporting-text no-left-padding">
                            <span>Category : ${bookmark.category }</span>
                        </div>
                    </div>
                    <input type="button" value="미리보기" class="miribogi" url="${ytIdList[status.index] }" onclick="miribogi"> 
                </div>
                <div class="miriDiv">
                </div>
     		</c:forEach>
  	 	</div>
    </main>
    <!-- 여기까지가 페이지의 코드 -->
    <jsp:include page="../navi_side_bar_bot.jsp"></jsp:include>
    