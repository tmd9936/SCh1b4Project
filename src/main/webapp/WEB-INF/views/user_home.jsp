<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	

	 <jsp:include page="navi_side_bar.jsp"></jsp:include>
	 <style type="text/css">
		.mdl-card--expand{
			cursor: pointer;
		}
	 </style>
	 <script type="text/javascript">
		$(function(){
			$(".category").on('click',function(){
				var str = $(this).text();
				location.href = 'categoryList?str=' + str;

				/* document.id.action="/contents/contentsList.jsp";

				document.id.submit(); */

			});
			
			$('.contentsCard').on('click',function(){
				var num = $(this).attr('value');
				location.href = 'studySpace?num='+num;
				
			});
		});
		
		
	 </script>
	 
	 
      
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
        	
        <a>최신순</a>
          <div class="mdl-color--white mdl-shadow--2dp mdl-cell mdl-cell--12-col mdl-grid">
				<c:forEach var="list" items="${result }" end="5">
				
				<div class="contentsCard" value="${list.contents_num }">
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
				</div>
				</c:forEach>					
			</div>
        
        <a>랜덤순</a>
          <div class="mdl-color--white mdl-shadow--2dp mdl-cell mdl-cell--12-col mdl-grid">
				<c:forEach var="rnd" items="${rnd }" >
				<div  class="contentsCard" value="${rnd.contents_num }">
					<div class="demo-card-square mdl-card mdl-shadow--2dp">
						<div class="mdl-card__title mdl-card--expand">
						<h2 class="mdl-card__title-text"><img src="${rnd.thumbnail }" width="100%" height="100%"> </h2>
						</div>
						<div class="mdl-card__supporting-text">
						${rnd.contents_title }
						</div>
						<div class="mdl-card__actions mdl-card--border">
						<a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
						View Videos
						</a>
						</div>
					</div>
				</div>
				</c:forEach>					
			</div>	
        	
        	
        	
        	<!-- 카테골별 3개씩 select 실패 -->
	        <%-- <c:forEach var="category" items="${categoryList }">
	        	
				<p>${category.category_kr }</p>
				<div class="mdl-color--white mdl-shadow--2dp mdl-cell mdl-cell--12-col mdl-grid">
				
					<!-- 카테고리 맞는 영상이 있는 만큼 생성
						 조건은  category.category == list.category
						 최신 등록 영상 3개
					-->
					
					<div class="mdl-color--white mdl-shadow--2dp mdl-cell mdl-cell--12-col mdl-grid">
					
					<c:forEach var="list" items="${result }">
					
					<c:if test="${list.category == category.category}" >	
					<div class="demo-card-square mdl-card mdl-shadow--2dp">
						<div class="mdl-card__title mdl-card--expand">
						<h2 class="mdl-card__title-text"><img src="${list.thumbnail }" width="100%" height="100%"> </h2>
						</div>
						<div class="mdl-card__supporting-text">
						Lorem ipsum dolor sit amet, consectetur adipiscing elit.
						Aenan convallis.
						</div>
						<div class="mdl-card__actions mdl-card--border">
						<a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
						View Videos
						</a>
						</div>
					</div>
					</c:if>
					
					</c:forEach>					
					</div>
					
				</div>
			</c:forEach>
		 --%>
		
			
         <!--  
          <a>Category</a>
          <div class="mdl-color--white mdl-shadow--2dp mdl-cell mdl-cell--12-col mdl-grid">
	            1
	            <div class="demo-card-square mdl-card mdl-shadow--2dp">
				  <div class="mdl-card__title mdl-card--expand">
				    <h2 class="mdl-card__title-text">Update</h2>
				  </div>
				  <div class="mdl-card__supporting-text">
				    Lorem ipsum dolor sit amet, consectetur adipiscing elit.
				    Aenan convallis.
				  </div>
				  <div class="mdl-card__actions mdl-card--border">
				    <a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
				      View Videos
				    </a>
				  </div>
				</div>
				2
	            <div class="demo-card-square mdl-card mdl-shadow--2dp">
				  <div class="mdl-card__title mdl-card--expand">
				    <h2 class="mdl-card__title-text">
				    	Update</h2>
				  </div>
				  <div class="mdl-card__supporting-text">
				    Lorem ipsum dolor sit amet, consectetur adipiscing elit.
				    Aenan convallis.
				  </div>
				  <div class="mdl-card__actions mdl-card--border">
				    <a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
				      View Videos
				    </a>
				  </div>
				</div>
				3
	            <div class="demo-card-square mdl-card mdl-shadow--2dp">
				  <div class="mdl-card__title mdl-card--expand">
				    <h2 class="mdl-card__title-text">Update</h2>
				  </div>
				  <div class="mdl-card__supporting-text">
				    Lorem ipsum dolor sit amet, consectetur adipiscing elit.
				    Aenan convallis.
				  </div>
				  <div class="mdl-card__actions mdl-card--border">
				    <a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
				      View Videos
				    </a>
				  </div>
				</div>
          </div>
           -->
         </div>
      </main>
    <!-- 여기까지가 페이지의 코드 -->
    <jsp:include page="navi_side_bar_bot.jsp"></jsp:include>