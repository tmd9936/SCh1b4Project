<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	 <jsp:include page="navi_side_bar.jsp"></jsp:include>
	 <style type="text/css">
		.mdl-card--expand{
			cursor: pointer;
		}
		.contentsCard{
		}
		
	 </style>
	 
	<script src="<c:url value="/resources/js/swiper.js" />"></script><script>
	window.onload = function(){
		var swiper = new Swiper('.swiper-container', {
					 pagination: '.swiper-pagination',
					 paginationType: 'progress',
					 slidesPerView: 'auto',
					 paginationClickable: true,
					 spaceBetween: 0,
					 freeMode: true,
					 nextButton: '.next',
					 prevButton: '.back'
		 });
	};
	</script></head>
	 
	 
	 <script type="text/javascript">
		$(function(){
			$(".category").on('click',function(){
				var str = $(this).text();
				location.href = 'categoryList?str=' + str;

				/* document.id.action="/contents/contentsList.jsp";

				document.id.submit(); */

			});
			
		
			
			$('.contentsCard').on('click',function(){
				var contents_num = $(this).attr('value');
				location.href = 'studySpace?contents_num='+contents_num;
				
			});
			
			
			
			$(function(){
				$(".category").on('click',function(){
					var str = $(this).text();
					/* alert(str); */
					location.href = 'categoryList?category=' + str;
			
					/* document.id.action="/contents/contentsList.jsp";
			
					document.id.submit(); */
			
				});
			});
			
			
		});
		
		
	 </script>
	 <script type="text/javascript" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
	
      <!-- 여기부터 시작 -->
      <main class="mdl-layout__content mdl-color--grey-100">
    	<section class="feature">
    	<br>
			<div class="inWrap">
				<div class="fInner swiper-container swiper-container-horizontal swiper-container-free-mode">
					<ul class="swiper-wrapper" style="transform: translate3d(-2019px, 0px, 0px); transition-duration: 0ms;">
						<c:forEach var="category" items="${categoryList }" varStatus="status">
							<c:choose>
								<c:when test="${status.count == 1 }">
									<li class="swiper-slide swiper-slide-active category"><a href="#"><span>${category.category_kr }</span></a></li>
								</c:when>
								<c:when test="${status.count == 2 }">
									<li class="swiper-slide swiper-slide-next category"><a href="#"><span>${category.category_kr }</span></a></li>
								</c:when>
								<c:otherwise>
									<li class="swiper-slide category"><a href="#"><span>${category.category_kr }</span></a></li>
								</c:otherwise>
							</c:choose>
							
		      				
		      			</c:forEach>
						
					
					</ul>
					<!-- Add Pagination -->
					<div class="swiper-pagination swiper-pagination-progress"><span class="swiper-pagination-progressbar" style="transform: translate3d(0px, 0px, 0px) scaleX(1) scaleY(1); transition-duration: 300ms;"></span></div>
				</div>

				<div class="button">
					<div class="back"><a href="#"><span class="hidden">back</span></a></div>
					<div class="next swiper-button-disabled"><a href="#"><span class="hidden">next</span></a></div>
				</div>
			</div>
		</section>	
    
          
       	 <a>최신순</a>
          <div class="mdl-color--white mdl-shadow--2dp mdl-cell mdl-cell--12-col mdl-grid">
				<c:forEach var="list" items="${result }" end="7">
				
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
						View Video
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
						View Video
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