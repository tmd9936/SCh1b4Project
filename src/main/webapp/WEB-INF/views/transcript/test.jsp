<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html >
<html>
<head>
<!-- js 적용  -->
<script type="text/javascript" src="<c:url value="/resources/javascript/question.js"></c:url>"></script>
<script src="<c:url value="/resources/jquery-3.3.1.js"/>"> </script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>[ Transcript Word ]</title>
</head>
<body>
 
    <c:forEach items="${tslist }" var="ts" varStatus="status">
      
    <div id="outputDiv${ts.ts_num }" onclick="javascript:answer('${ts.ts_num }','${ts.ts_text}')" style="cursor:pointer">
    </div>
    <br>
    <div id="list${ts.ts_num }"></div>
    <br>
    </c:forEach>
     
<script type="text/javascript">
$(document).ready(function(){
  
  
})  
 
 function answer(num, text){
    var x = document.createElement("INPUT");
    x.setAttribute("type", "text");
    //div를 만들고
        var div = document.createElement('div');
    //div 내용은 다른데서 가져오고
        div.innerHTML = text+"<br>";
    //div를 붙여준다.    
        //document.getElementById('outputDiv'+num).appendChild(div);
        //document.getElementById('outputDiv'+num).appendChild(x);
    document.getElementById('outputDiv'+num).onclick="disabled";
    document.getElementById('outputDiv'+num).style.cursor="auto";
    }
  /*
    function gotoqPage() {
  
    var sentences=[];
    var names = document.getElementsByName('num');
    for (var i = 0; i < names.length; i++) {
    //체크한 것만 담아서 문제풀기 페이지로
      if(names[i].checked){
        sentences.push(names[i].value);
      }
    }
    var ts_num = document.getElementById('ts_num');
    ts_num.value = sentences;
    console.log(ts_num.value);
    var contents_num = document.getElementById('contents_num');
    console.log(contents_num.value);
    var qPage = document.getElementById('qPage');
    qPage.submit();
  }
  */
  function checkanswer(num,answer){
	    
	    var uanswer = document.getElementById('uanswer'+num);
	    if(answer==uanswer.value){
	      alert('정-답');
	      uanswer.disabled=true;
	    }else{
	      alert('새창 띄우고 단어 + 품사 + 설명 + 뜻');
	          window.open("wordDetail", "wordDetail", "width=578, height=215, toolbar=no, menubar=no, scrollbars=no, location=no, status=no, resizable=no" );  
	    }
	  }
	  $(document).ready(function(){
	    <c:forEach items="${tslist }" var="ts">
	    test("${ts.ts_num}","${ts.ts_text }");
	    </c:forEach>
	    function test(num,text){
	      var object;
	      var compare=[];
	      $(document).ready(function(){
	          $.ajax({
	              type : "POST",
	              url : "https://api.apigw.smt.docomo.ne.jp/gooLanguageAnalysis/v1/morph?APIKEY=6b574a385741326e7041464e6b4b636d57556144426b4b6a746d5470796b465461597a6263482f41664e41",
	              ContentType : "application/json; charset=utf-8",
	              dataType : 'json',
	              data : {
	                 "request_id": num,
	                 "sentence": text,
	                 "info_filter" : "form|pos|read",
	                 "pos_filter" : "名詞|連用詞|格助詞"
	                },
	              success : function(obj){
	                var list = '';
	                var str = obj.word_list[0];
	                console.log("str : "+str);
	                $.each(str,function(index,item){
	                  //list += '인덱스: '+index+'<br>';
	                        console.log("item : "+item);
              var c=0;
              $.each(item,function(index2,item2){
                switch(c){
                case 0:
                  list += '원문: '+item2;
                  //list += item2;
                  compare.push(item2);
                  c++;
                  break;
                case 1:
                  list += ' 품사: '+item2;
                  //list += item2;
                  c++;
                  break;
                case 2:
                  list += ' 읽기: '+item2;
                  //list += item2;
                  c=0;
                  break;
                }
              });
              list += '  ';
          });
          $('#list'+num).text(list);
          console.log(list);
          for (var i = 0; i < compare.length; i++) {
            console.log(compare.length);
             $('#outputDiv'+num).html(text);
             var temp = "'"+compare[i]+"'";
             $('#outputDiv'+num).html(text.replace(compare[i], '<input type="text" id="uanswer'+num+'" onkeypress="if(event.keyCode==13) {checkanswer('+num+','+temp+');}">'));
          }
          return true;
        },
        error : function(){
          alert("실패");
          return false;
        }
        
      });
	          
	      })
	    }
	  })
	</script>
	 
	<!--  
	  <c:forEach items="${tslist }" var="ts" varStatus="status">
	  <input type="checkbox" name="num" value="${ts.ts_num}" style="float: left;">&nbsp;&nbsp;&nbsp;
	  <div style="float: left;">${status.index }. </div>
	  <div style="float: left;">${ts.ts_text}</div> <br><br>
	   <div id="outputDiv${ts.ts_num }" >
	   </div>
	   <input type="button" value="문제 생성해보기" onclick='javascript:test("${ts.ts_num}","${ts.ts_text }")'>
	   <br>
	    </c:forEach>
  <div id="test" ></div><br>
  <input type="text" id="input"> <input type="button" onclick="javascript:check()">
  
  <input type="button" value="문제풀기로" onclick="gotoqPage()">
  
  <form action="qPage" method="post" onsubmit="" id="qPage">
  <input type="hidden" value="${contents_num }" id="contents_num" name="contents_num">
  <input type="hidden" value="" id="ts_num" name="ts_num">
  </form>
  
  -->
  
</body>
</html>
 