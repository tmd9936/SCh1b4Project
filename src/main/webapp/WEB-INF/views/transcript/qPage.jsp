<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<script src="<c:url value="/resources/jquery-3.3.1.js"/>"> </script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>[ qPage ]</title>
</head>
<body>

 

<c:forEach items="${tsList }" var="ts" varStatus="status">

	<div style="float: left;">${status.index }. </div>
	<div style="float: left;">${ts.ts_text}</div> <br><br>
	 <div id="outputDiv${ts.ts_num }" >
	 </div>
	 <input type="button" value="문제 생성해보기" onclick='javascript:test("${ts.ts_num}","${ts.ts_text }")'>
	 <input type="text" id="mondai${ts.ts_num }" value="${ts.ts_num }" name="qNum">
	 <br>
	</c:forEach>
	
<script type="text/javascript">
/* 
function makeq(){
	var qNum = document.getElementsByName('qNum');
	var 
	for (var i = 0; i < qNum.length; i++) {
		
	}
}
*/
function test(num,text) {
	var object;
	var compare=[];
	$(document).ready(function(){
		$.ajax({
				type : "POST",
				url : "https://api.apigw.smt.docomo.ne.jp/gooLanguageAnalysis/v1/morph?APIKEY=4a594b3469593866475969786f4b436873576a4173566c723274585070315332476d6e5a524e4f56745735",
				ContentType : "application/json; charset=utf-8",
				dataType : 'json',
				data : {
					 "request_id": num,
					 "sentence": text,
					 "info_filter" : "form|pos|read",
					 "pos_filter" : "名詞|連用詞"
					},
				success : function(obj){
					var list = '';
					console.log(obj);
					var str = obj.word_list[0];
					console.log(str);
					$.each(str,function(index,item){
						//list += '인덱스: '+index+'<br>';
						var c=0;
						$.each(item,function(index2,item2){
							switch(c){
							case 0:
								//list += '원문: '+item2;
								list += item2;
								compare.push(item2);
								c++;
								break;
							case 1:
								//list += ' 품사: '+item2;
								//list += item2;
								c++;
								break;
							case 2:
								//list += ' 읽기: '+item2;
								//list += item2;
								c=0;
								break;
							}
						});
						list += '<br>';
					});
					$('#outputDiv'+num).html(text);
					for (var i = 0; i < compare.length; i++) {
						console.log(compare[i]);
						 text = $('#outputDiv'+num).html();
						 $('#outputDiv'+num).html(text.replace(compare[i], '[___]'));
					}
				},
				error : function(){
					alert("실패");
				}
			});
		})
		function check(){
		var eTest = document.getElementById('test');
		var eInput = document.getElementById('input');
		if(eTest.value == eInput.value){
			alert('오올');
		}else{
			alert('안맞음');
		}
	}
}
</script>
</body>
</html>