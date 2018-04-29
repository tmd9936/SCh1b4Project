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
    
    $(function(){
    	var dialog = document.querySelector('#progressDialog');
    	 $('.pushBtn').on('click',function(){
    		 	
    		 dialog.showModal();
    	    });
    	    
    	    $('.close').on('click',function(){
    	    	dialog.close();
    	    });
    		
    });
   
	</script>
    <style>
		#progressDialog{
			position: absolute;
			top: 80px;
			width: 500px;
		}
	</style>
    
    
  	<main class="mdl-layout__content mdl-color--grey-100">
        <div class="mdl-grid demo-content">
        	<div class="mdl-color--white mdl-shadow--2dp mdl-cell mdl-cell--12-col mdl-grid">
        		<dialog class="mdl-dialog" id="progressDialog">
    				<h2 class="mdl-dialog__title">잠시만 기다려 주세요!</h2>
    				<div class="mdl-dialog__content">
    						<p>자막이 있으면 최대 30초, 없으면 동영상의 길이에 따라 최대 10분 입니다.</p>
    				<div id="p2" class="mdl-progress mdl-js-progress mdl-progress__indeterminate"></div>
    				</div>
    				
    			</dialog>
	      		<div>
	      		<h2> </h2>
	      		</div>
	      		<div class="ytd_main">
	      		<form action="../ytDown" method="get" onsubmit="ytd_text">
				<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				    <input class="mdl-textfield__input" type="text" name="youtube" id="youtube">
				    <label class="mdl-textfield__label" for="sample">example)https://www.youtube.com/watch?v=?????</label>
			  	</div>
				<button class="pushBtn mdl-button mdl-js-button mdl-button--primary">
					Push
				</button>
				</form>
				</div>
				
      		</div>   
  	 	</div>
    </main>
    <!-- 여기까지가 페이지의 코드 -->
    <jsp:include page="../navi_side_bar_bot.jsp"></jsp:include>