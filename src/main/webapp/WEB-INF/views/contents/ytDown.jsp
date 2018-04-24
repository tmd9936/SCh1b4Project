<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<jsp:include page="../navi_side_bar.jsp"></jsp:include>
	
    <!-- 여기부터 시작 -->
    <script type="text/javascript">
    function ytd_text() {
		if(youtube,value == ''){
			alert("주소를 입력해 주세요");
			return false;
		}
    	  
			return true;
    	}

	</script>
    
  	<main class="mdl-layout__content mdl-color--grey-100">
        <div class="mdl-grid demo-content">
        	<div class="mdl-color--white mdl-shadow--2dp mdl-cell mdl-cell--12-col mdl-grid">
	      		<div>
	      		<h2>youtube의 영상을 교육용 자료로 바꾸어 드립니다</h2>
	      		</div>
	      		<div class="ytd_main">
	      		<form action="../ytDown" method="get" onsubmit="ytd_text">
				<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				    <input class="mdl-textfield__input" type="text" name="youtube" id="youtube">
				    <label class="mdl-textfield__label" for="sample">example)https://www.youtube.com/watch?v=?????</label>
			  	</div>
				<button class="mdl-button mdl-js-button mdl-button--primary">
					Push
				</button>
				</form>
				</div>
      		</div>   
  	 	</div>
    </main>
    <!-- 여기까지가 페이지의 코드 -->
    <jsp:include page="../navi_side_bar_bot.jsp"></jsp:include>