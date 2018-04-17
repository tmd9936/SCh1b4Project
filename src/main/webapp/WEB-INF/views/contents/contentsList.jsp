<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	

	 <jsp:include page="../navi_side_bar.jsp"></jsp:include>
	 <!-- 여기부터 시작 -->
      <main class="mdl-layout__content mdl-color--grey-100">
        <div class="mdl-grid demo-content">
			<div class="mdl-color--white mdl-shadow--2dp mdl-cell mdl-cell--12-col mdl-grid">
		      	<div class="categorys">
		      		<c:forEach var="category" items="${categoryList }">
		      			<p class="category" >${category.category_kr }</p>
		      		</c:forEach>
		      	</div>
      		</div>        
        </div>
       </main> 	
	<!-- 여기까지가 페이지의 코드 -->
    <jsp:include page="../navi_side_bar_bot.jsp"></jsp:include>