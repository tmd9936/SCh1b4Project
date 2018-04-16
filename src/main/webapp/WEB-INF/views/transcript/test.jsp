<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html >
<html>
<head>
<script src="<c:url value="/resources/jquery-3.3.1.js"/>"> </script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>[ Transcript Word ]</title>
</head>
<body>

		<c:forEach items="${tslist }" var="ts" varStatus="status">
		
		<div id="outputDiv${ts.ts_num }" onclick="javascript:answer('${ts.ts_num }','${ts.ts_text}')">
		</div>
		<div id="list${ts.ts_num }"></div>
		<br>
		</c:forEach>



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
	<script type="text/javascript">
	function answer(num, text){
		var x = document.createElement("INPUT");
		x.setAttribute("type", "text");
		
		
		//div를 만들고
		    var div = document.createElement('div');
		//div 내용은 다른데서 가져오고
		    div.innerHTML = text+"<br>";
		//div를 붙여준다.		
		    document.getElementById('outputDiv'+num).appendChild(div);
		    document.getElementById('outputDiv'+num).appendChild(x);
		document.getElementById('outputDiv'+num).onclick="disabled";
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
	$(function(){
		<c:forEach items="${tslist }" var="ts" >
			test("${ts.ts_num}","${ts.ts_text }");
		</c:forEach>
	})
		function test(num,text){
			var object;
			var compare=[];
			$(document).ready(function(){
			$.ajax({
					type : "POST",
					url : "https://api.apigw.smt.docomo.ne.jp/gooLanguageAnalysis/v1/morph?APIKEY=6c7352564c78664e796348556c437466386776722e4e43305a6a6a4854594b764f656958484741372f6844",
					ContentType : "application/json; charset=utf-8",
					dataType : 'json',
					data : {
						 "request_id": num,
						 "sentence": text,
						 "info_filter" : "form|pos|read",
						 //"pos_filter" : "名詞|連用詞"
						},
					success : function(obj){
						var list = '';
						var str = obj.word_list[0];
						$.each(str,function(index,item){
							//list += '인덱스: '+index+'<br>';
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
								list += '<br>';
						});
						$('#list'+num).text();
						console.log(list);
						for (var i = 0; i < compare.length; i++) {
							//console.log(compare[i]);
							 $('#outputDiv'+num).html(text);
							 $('#outputDiv'+num).html(text.replace(compare[i], '[[		]]'));
						}
						
						
						/*
						부적절한 단어 걸러내는 ajax
						$.ajax({
							type : "POST",
							url : "https://api.apigw.smt.docomo.ne.jp/truetext/v1/sensitivecheck?APIKEY=483567313073493142416249757669777545574a5575626e2f755145677a5a4c2f63394d69364757646532",
							ContentType : "application/x-www-form-urlencoded",
							dataType : 'json',
							data : "text=\xEF\xBC\x93\xEF\xBC\xA4\xE3\x83\x97\xE3\x83\xAA\xE3\x83\xB3\xE3\x82\xBF\xE3\x81\xA7\xE9\x8A\x83\xE3\x81\xAE\xE8\xA8\xAD\xE8\xA8\x88\xE5\x9B\xB3\xE3\x82\x92\xE6\x9C\x9F\xE9\x96\x93\xE9\x99\x90\xE5\xAE\x9A\xE5\x85\xAC\xE9\x96\x8B\xE4\xB8\xAD\xE3\x80\x81\xE8\x84\xB1\xE6\xB3\x95\xE3\x83\x8F\xE3\x83\xBC\xE3\x83\x96\xE3\x81\xAF\xE3\x81\x93\xE3\x81\xA1\xE3\x82\x89",
							success : function(analysis){
								console.log(analysis);
							},
							error : function(){
								alert("에러");
							}
						});
						*/
						return true;
					},
					error : function(){
						alert("실패");
						return false;
					}
					
				});
			})
		}
	
</script>
</body>
</html>