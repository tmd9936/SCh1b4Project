<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
	<style type="text/css">
		
/* youtube플레이어 화면에 꽉차게 하는 css */
#youTubePlayer1 {position:relative;width:100%;padding-bottom:56.25%;}
#youTubePlayer1 iframe {position:absolute;width:100%;height:100%;}
	</style>
	<title>Home</title>
</head>
<script type="text/javascript">
//youtube API 불러옴
var tag = document.createElement('script');
tag.src = "https://www.youtube.com/AIzaSyCVV_JM7vaf6ZtfxQ2w0gQuhlayEdqTd5w";
var firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
 
// 플레이어변수 설정
var youTubePlayer1;
 
function onYouTubeIframeAPIReady() {
    youTubePlayer1 = new YT.Player('youTubePlayer1', {
        width: '1000',
        height: '563',
        videoId: '0Ui6uGHmiks',
        playerVars: {rel: 0},//추천영상 안보여주게 설정
        events: {
          'onReady': onPlayerReady, //로딩할때 이벤트 실행
          'onStateChange': onPlayerStateChange //플레이어 상태 변화시 이벤트실행
        }
    });//youTubePlayer1셋팅
}
 
function onPlayerReady(event) {
    event.target.playVideo();//자동재생
    //로딩할때 실행될 동작을 작성한다.
}
 
function onPlayerStateChange(event) {
    if (event.data == YT.PlayerState.PLAYING) {
        //플레이어가 재생중일때 작성한 동작이 실행된다.
    }
  }
 
$(document).ready(function () {
    $(".btn_play").on("click", function () {
        youTubePlayer1.playVideo();//재생
    });
    $(".btn_stop").on("click", function () {
        youTubePlayer1.stopVideo();//정지
    });
    $(".btn_pause").on("click", function () {
        youTubePlayer1.pauseVideo();//일시정지
    });
});
</script>
<body>
<h1>
	Hello world!  
</h1>

<div id="youTubePlayer1"></div><!-- 플레이어를 불러올 영역-->

<div class="btn_play">시작</div><!-- 시작버튼-->
<div class="btn_stop">정지</div><!-- 정지버튼-->
<div class="btn_pause">일시정지</div> <!-- 일시정지버튼-->
</body>
</html>
