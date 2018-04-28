/**
 * 
 */





var youTubePlayer;
var done = true;
var dur = 3000;

var ts_num = '';
var spTime = (100/allTime)/4; //seekPointTime 0.25초에 crub이 가는 시간 
var intervals = new Array();
var speakSpace = false;
var speakState = false;
var speakText = '';


$(function(){
	$('.script-bar').on('click',function(){
		speakState = true;
		$('#start').val($(this).attr('start'));
		$('#dur').val($(this).attr('dur'));
		speakText = $(this).attr('text');
		
		if(speakState){
			$('.Notice').text(speakText);
		}
		
		if(intervals.length >0){
			$.each(intervals,function(index,inter){
				clearInterval(inter);
			});
		}
		$('.script-bar').attr('click','false');
		$(this).attr('click','true');
		
		//crub위치 바꾸기
		var left = $(this).attr('left');
		$('.seek-crub').css('left',left+'%');
		ts_num = $(this).attr('num');
		num = ts_num;
		
		var crubsp = parseFloat(left);
		var endTime = ((parseFloat($(this).attr('start'))+parseFloat($(this).attr('dur')))/allTime*100);
		var ss = setInterval(function(){
			$('.seek-crub').css('left',crubsp+'%');
			//alert((parsefloat($('.seek-crub').css('left').replace('%',''))+spTime));
			crubsp += spTime;
			console.log(intervals.length);
			if(num != ts_num||crubsp>endTime){
				clearInterval(ss);
			}
			
		},250);
		
		intervals.push(ss);
		
		dur = parseInt(($(this).attr('dur')))*1090;
		done = false;
		
		var start = $(this).attr('start'); //유튜브 시작 초
		youTubePlayer.seekTo(start,true);// 유튜브 시작위치
		youTubePlayer.playVideo(); //유튜브 재생
		
		setTimeout(changeSeekerColor,dur,this);
		
	});
});



function changeSeekerColor(bar){
	if($(bar).attr('click')=='true')
		$(bar).css('background-color', 'red');
}

function moveCrub(num){
	
}


function pauseVideo(num){
	if(ts_num == num)
		youTubePlayer.pauseVideo();
}

function stopVideo() {
	youTubePlayer.stopVideo();
 }


function onYouTubeIframeAPIReady() {
    'use strict';

    var inputVideoId = document.getElementById('YouTube-video-id');
    var videoId = inputVideoId.value;
    var suggestedQuality = 'tiny';
    var height = 360;
    var width = 640;
    var youTubePlayerVolumeItemId = 'YouTube-player-volume';


    function onError(event) {
        youTubePlayer.personalPlayer.errors.push(event.data);
    }


    function onReady(event) {
        var player = event.target;

        player.loadVideoById({suggestedQuality: suggestedQuality,
                              videoId: videoId
                             });
        player.pauseVideo();
        //youTubePlayerDisplayFixedInfos();
    }


    function onStateChange(event) {
        var volume = Math.round(event.target.getVolume());
        var volumeItem = document.getElementById(youTubePlayerVolumeItemId);

        if (volumeItem && (Math.round(volumeItem.value) != volume)) {
            volumeItem.value = volume;
        }
        if (event.data == YT.PlayerState.PLAYING && !done) {
        	done = true;
            setTimeout(pauseVideo, dur,ts_num);
          }
    }


    youTubePlayer = new YT.Player('YouTube-player',
                                  {videoId: videoId,
                                   height: height,
                                   width: width,
                                   playerVars: {
                                	   			'wmode' : "transparent",
                                	   			'autohide': 0,
                                                'cc_load_policy': 0,
                                                'controls': 2,
                                                'disablekb': 1,
                                                'iv_load_policy': 3,
                                                'modestbranding': 1,
                                                'rel': 0,
                                                'controls' : 0,
                                                'showinfo': 0,
                                                'start': 3
                                               },
                                   events: {'onError': onError,
                                            'onReady': onReady,
                                            'onStateChange': onStateChange
                                           }
                                  });

    // Add private data to the YouTube object
    youTubePlayer.personalPlayer = {'currentTimeSliding': false,
                                    'errors': []};

    
}


/**
 * :return: true if the player is active, else false
 */
function youTubePlayerActive() {
    'use strict';

    
    
    
    return youTubePlayer && youTubePlayer.hasOwnProperty('getPlayerState');
}


/**
 * Get videoId from the #YouTube-video-id HTML item value,
 * load this video, pause it
 * and show new infos.
 */
function youTubePlayerChangeVideoId() {
    'use strict';

    var inputVideoId = document.getElementById('YouTube-video-id');
    var videoId = inputVideoId.value;

    youTubePlayer.cueVideoById({suggestedQuality: 'tiny',
                                videoId: videoId
                               });
    youTubePlayer.pauseVideo();
    youTubePlayerDisplayFixedInfos();
}






/**
 * Display
 *   some video informations to #YouTube-player-infos,
 *   errors to #YouTube-player-errors
 *   and set progress bar #YouTube-player-progress.
 */
/* 
 function youTubePlayerDisplayInfos() {
    'use strict';

    if ((this.nbCalls === undefined) || (this.nbCalls >= 3)) {
        this.nbCalls = 0;
    }
    else {
        ++this.nbCalls;
    }

    var indicatorDisplay = '<span id="indicator-display" title="timing of informations refreshing">' + ['|', '/', String.fromCharCode(8212), '\\'][this.nbCalls] + '</span>';

    if (youTubePlayerActive()) {
        var state = youTubePlayer.getPlayerState();
		
        var current = youTubePlayer.getCurrentTime();
        var duration = youTubePlayer.getDuration();
        var currentPercent = (current && duration
                              ? current*100/duration
                              : 0);

        var fraction = (youTubePlayer.hasOwnProperty('getVideoLoadedFraction')
                        ? youTubePlayer.getVideoLoadedFraction()
                        : 0);

        var url = youTubePlayer.getVideoUrl();

        if (!current) {
            current = 0;
        }
        if (!duration) {
            duration = 0;
        }

        var volume = youTubePlayer.getVolume();

        if (!youTubePlayer.personalPlayer.currentTimeSliding) {
            document.getElementById('YouTube-player-progress').value = currentPercent;
        }

    

    }
    else {
    }
} 

 */
 
/**
 * Pause.
 */
function youTubePlayerPause() {
    'use strict';

    if (youTubePlayerActive()) {
        youTubePlayer.pauseVideo();
    }
}


/**
 * Play.
 */
function youTubePlayerPlay() {
    'use strict';

    if (youTubePlayerActive()) {
        youTubePlayer.playVideo();
    }
}


/**
 * Return the state decription corresponding of the state value.
 * If this value is incorrect, then return unknow.
 *
 * See values:
 * https://developers.google.com/youtube/iframe_api_reference#Playback_status
 *
 * :param number: any
 * :param unknow: any
 *
 * :return: 'unstarted', 'ended', 'playing', 'paused', 'buffering', 'video cued' or unknow
 */
function youTubePlayerStateValueToDescription(state, unknow) {
    'use strict';

    var STATES = {'-1': 'unstarted',   // YT.PlayerState.
                  '0': 'ended',        // YT.PlayerState.ENDED
                  '1': 'playing',      // YT.PlayerState.PLAYING
                  '2': 'paused',       // YT.PlayerState.PAUSED
                  '3': 'buffering',    // YT.PlayerState.BUFFERING
                  '5': 'video cued'};  // YT.PlayerState.CUED

    return (state in STATES
            ? STATES[state]
            : unknow);
}


/**
 * Stop.
 */
function youTubePlayerStop() {
    'use strict';

    if (youTubePlayerActive()) {
        youTubePlayer.stopVideo();
        youTubePlayer.clearVideo();
    }
}




/**
 * Main
 */
(function () {
    'use strict';

    function init() {
        // Load YouTube library
        var tag = document.createElement('script');

        tag.src = 'https://www.youtube.com/iframe_api';

        var first_script_tag = document.getElementsByTagName('script')[0];

        first_script_tag.parentNode.insertBefore(tag, first_script_tag);
        

        // Set timer to display infos
       // setInterval(youTubePlayerDisplayInfos, 1000);
    }


    if (window.addEventListener) {
        window.addEventListener('load', init);
    } else if (window.attachEvent) {
        window.attachEvent('onload', init);
    }
}());


/* 화면 클릭시 Div 생성  */


function GoSpeakTheLine(){
	
	if(!speakSpace){
		
		var str = '<div class="Notice">';  
			str +=  "비디오에서 SPEAK1할 문장을 선택하세요:";
			str += '</div>';
			/*str +=' <button id="start_button">시작</button>';
			str += '<button id="endBtn" onclick="stopRecording(this);" disabled>종료</button>';
			str += '<div class="browser-landing" id="main"><div class="compact marquee"><div id="results">'
			str	+= '<span class="final" id="final_span"></span> <span class="interim" id="interim_span"></span>'
			str += '</div></div></div>';
			str + '<div id="ytPitch" style="height: 250px; width: 40%;" class="pitchContainer"></div>';
			str + '<div id="memPitch" style="height: 250px; width: 40%;" class="pitchContainer"></div>';*/
			
			str += '<style type="text/css">';
			str += '.secondView{'
			str += 'width: 598px;';
			str += 'height: 700px;';
			str += 'border:1px solid;';
			str +=	'}';
			str	+= '</style>';
			
			
			
		$('.divNewView').html(str);
		$('.speachView').css('visibility','visible');
		speakSpace = true;
	}else{
		$('.divNewView').html('');
		$('.speachView').css('visibility','hidden');
		speakSpace = false;
	}
	
}


function LearnTheWords(){
	
	var div = document.getElementById("divNewGSTL");
	
	var str = '<div class="Notice">';
	
	str +=  "비디오에서 SPEAK2할 문장을 선택하세요:";
	str += '</div>';
	str += '<style type="text/css">';
	str += '.secondView{'
	str += 'width: 298px;';
	str += 'height: 298px;';
	str += 'border:1px solid;';
	str +=	'}';
	str	+= '</style>';
	
	div.innerHTML = str;	
}


function GoLive(){
	
	var div = document.getElementById("divNewGSTL");
	var teacher_name = document.getElementById("teacher_name");
	
	var str ='<div id="container"></div>';
	str += '<input type="button" id="sendMessage" value="전송"  />';
	str +='<input type="text" id="message" placeholder="메시지 내용"/>';
	str +='<input type="hidden" id="to" />';
	str +='<div id="chatMessage" style="overFlow: auto; max-height: 500px;"></div>';
	
	
	str +='<style type="text/css">'; 
	str +='.secondView{'
	str +='width: 640px;';
	str +='height: 800px;';
	str +='border:1px solid;';
	str +='}';
	str	+='</style>';
	
	div.innerHTML = str;
	
	console.log(str);
	
	
	var sock = null;
	var message = {};
	
		sock = new SockJS("/www/echo");
		
	  	console.log(sock);
	  	
	    sock.onopen = function() {
	    	
	        message={};
	        message.message = "반갑습니다.";
	        message.type = "all";
	        message.to = "all";
	        sock.send(JSON.stringify(message)); 
	        
	        }
	     
	    sock.onmessage = function(evt) {
	        console.log(evt);
	    	$("#chatMessage").append(evt.data);
	        $("#chatMessage").append("<br />");
	        $("#chatMessage").scrollTop(99999999);
	    };
	     
	    sock.onclose = function() {
	    	
	        // sock.send("채팅을 종료합니다.");
	    }
	     
	     $("#message").keydown(function (key) {
	         if (key.keyCode == 13) {
	            $("#sendMessage").click();
	         }
	      });
	     
	    $("#sendMessage").click(function() {
	        if( $("#message").val() != "") {
	             
	            message={};
	            message.message = $("#message").val();
	            message.type = "all";
	            message.to = "all";
	             
	            var to = $("#to").val();
	            if ( to != "") {
	                message.type = "one";
	                message.to = to;
	            }
	             
	            sock.send(JSON.stringify(message));
	            $("#chatMessage").append("나 : "+	 " -> " + $("#message").val() + "<br/>");
	            $("#chatMessage").scrollTop(99999999);
	            $("#message").val("");
	        }
	    });
	    
	    
	    
	    /* 테스트 시작*/
	    var connection = new RTCMultiConnection();

	 // this line is VERY_important
	 connection.socketURL = 'https://rtcmulticonnection.herokuapp.com:443/';

	 // if you want audio+video conferencing
	 connection.session = {
	     audio: true,
	     video: true
	 };

	// connection.openOrJoin('your-room-id');
		connection.openOrJoin( 'room-id', function(isRoomExists, roomid) {
			  alert(isRoomExists ? 'You joined room' : 'You created room');
			});
				
		connection.onstream = function(event) {
		    var video = event.mediaElement;

		    // append to <body>
		   // document.body.appendChild(video);
		    //document.getElementById("container").appenChild(video);
		    $('#container').html(video);
		};
		            
		/* 테스트 종료 */
	    
	
}





/**
 * Seek the video to the currentTime.
 * (And mark that the HTML slider *don't* move.)
 *
 * :param currentTime: 0 <= number <= 100
 */
function youTubePlayerCurrentTimeChange(currentTime) {
    'use strict';

    youTubePlayer.personalPlayer.currentTimeSliding = false;
    if (youTubePlayerActive()) {
        youTubePlayer.seekTo(currentTime*youTubePlayer.getDuration()/100, true);
    }
}


/**
 * Mark that the HTML slider move.
 */
function youTubePlayerCurrentTimeSlide() {
    'use strict';

    youTubePlayer.personalPlayer.currentTimeSliding = true;
}

function WatchTheVideo(){
	
	alert(youTubePlayer.getPlayerState());
	
	 if(youTubePlayer.getPlayerState() == 1){
			alert("정지")	;	 
		 //youTubePlayer.stopVideo();
			 youTubePlayer.pauseVideo();
			}
	
	else if(youTubePlayer.getPlayerState() == 2 || youTubePlayer.getPlayerState() == 5){
		alert("재생");
		youTubePlayerPlay();
	}
	 
	else if(youTubePlayer.getPlayerState() == 3){
		
		alert("버퍼링");
		youTubePlayerPlay();
	}
	
	
}




$(function(){
	/*댓글 리스트 출력*/
	init2();	 
	
	
	
	
	$('#formButton').on('click', function(){
		
		
		
		var member_id = $('#member_id').val();
		var contents_num = $('#contents_num').val();
		var reply_text = $('#reply_text').val();
		
		
		
		if(reply_text.length == 0){
			alert("댓글 내용을 입력해주세요~");
			return;
			
		}
		
		$.ajax({
		
			url : "contentsReplyInsert",
			type : "POST",
			contentType : "application/json; charset=utf-8",
			data : JSON.stringify({
				
				member_id : member_id,
				contents_num : contents_num,
				reply_text : reply_text
				
			}),
			success : function(){
				
				alert("댓글 등록 완료");
				
				$('#reply_text').val('');
				/*댓글 리스트 출력*/
				init2();
				
			},
			error : function(err){
				console.log(err)
			}
			
			
		});
		
		
	});
	
	/*좋아요 입력*/
	$('#thumbsUp').on('click',function(){
		var contents_num = $('#contents_num').val();
		$.ajax({
			url:"selectRecommendOrNot",
			type:"GET",
			data:{
				contents_num : contents_num
			},
			success: function(data){
				
				if(data){
					
					$.ajax({
						url:"insertRecommend",
						type: "POST",
						data:{
							contents_num : contents_num	
						},
						success: function(){
							var contents_num = $('#contents_num').val();
							
							$.ajax({
								url:"selectRecommendCount",
								type: "GET",
								data:{
									contents_num : contents_num
								},
								success: function(data){
									if(data){
										$('#thumbsUp').attr('src','/www/resources/icon/star_after.svg');
										var str = '<h4>'+data+'</h4>';
										$('#recommendCountDiv').html(str);
									}
									
								},
								error:function(err){
									console.log(err);
								}
							});//내부의내부함수
						},
						error: function(err){
							console.log(err);
						}
						
						
					});//THUMBSUP 함수 내부 1 AJAX
		
				}else{
					alert("추천한 게시물은 다시 추천 불가 !");
					/*$.ajax({
						url : "deleteRecommend",
						type: "POST",
						data:{
							contents_num : contents_num	
						},
						success: function(){
							var contents_num = $('#contents_num').val();
							alert('추천 삭제');
							
							$.ajax({
								url:"selectRecommendCount",
								type: "GET",
								data:{
									contents_num : contents_num
									
								},
								success: function(data){
									if(data){
										$('#thumbsUp').attr('src','/www/resources/icon/thumbs-up.svg');
										var str = '<h4>'+data+'</h4>';
										$('#recommendCountDiv').html(str);
									}
									
								},
								error:function(err){
									console.log(err);
								}
							});//내부의내부함수
							
						},
						error:function(err){
							console.log(err);
						}
						
						
					});
					
					*/
				}
				
				
			},
			error: function(err){
				console.log(err);
			}
		});
		
		
	});
	
	
	$('#thumbsUp').mouseover(function(){
		$('#thumbsUp').css('cursor','pointer')
	})
	
	
	/*북마크 입력*/
	$('#bookMark').on('click',function(){
		var contents_num = $('#contents_num').val();
		$.ajax({
			url:"selectBookmarkOrNot",
			type:"GET",
			data:{
				contents_num : contents_num
			},
			success: function(data){
				
				if(data){
					
					$.ajax({
						url:"bookmarkInsert",
						type: "POST",
						data:{
							contents_num : contents_num	
						},
						success: function(){
							var contents_num = $('#contents_num').val();
						$('#bookMark').attr('src','/www/resources/icon/bookmark_black.svg');
						alert("북마크 등록");
						
						},
						error: function(err){
							console.log(err);
						}
							
					});
		
				}else{
			
						$.ajax({
							
							url : "bookmarkDelete",
							type : "POST",
							data :{
								
								contents_num : contents_num
							},
							success :function(){
								var contents_num = $('#contents_num').val();
								$('#bookMark').attr('src','/www/resources/icon/bookmark_border_black.svg');
								alert("북마크 삭제");
							},
							error : function(err){
								
								console.log(err);
							}
							
							
							
							
						});
						
						
						
					
				}
				
				
			},
			error: function(err){
				console.log(err);
			}
		});
		
		
	});
	
	
	$('#bookMark').mouseover(function(){
		$('#bookMark').css('cursor','pointer')
	})
	
	
});//레디함수 종료 


function init2(){
		
	var contents_num = $('#contents_num').val();
	var page = 1;
	var loading = true;
	
	
	$.ajax({
		
		url : "selectAllContentsReply",
		type : "POST",
		data : {
			
			contents_num : contents_num,
		    		 page : page
		
	},
	dataType : "json",
	success : function(obj){
		//console.log("첫 페이지 리스트 ");
		//console.log(obj.navi);
		//console.log(obj.contentsReply);
		
		var str = '';
		str += '<tr>';
		str += '<th class="mdl-data-table__cell--non-numeric">번호</th>';
	    str += '<th>아이디</th>';
	    str += '<th>내용</th>';
	    str += '<th>날짜</th>';
	    str += '<th>삭제</th>';
	    str += '<th>수정</th>';
		str += '<th></th>';
		str += '</tr>';
		
		$.each(obj.contentsReply, function(index, item){
			
				console.log(index);
			
			
			str += '<tr id="page1">';
			str += '<td >'+item.reply_num+'</td>';
			str += '<td >'+item.member_id+'</td>';
			str += '<td >'+item.reply_text+'</td>';
			str += '<td >'+item.inputdate+'</td>';
			str += '<td><input type="button" value="삭제" class="deleteContentsReply mdl-button mdl-js-button mdl-button--raised mdl-button--colored" data_num="'+item.reply_num+'"></td>'; 
			str += '<td><input type="button" value="수정" class= "updateContentsReplyForm mdl-button mdl-js-button mdl-button--raised mdl-button--colored" data_num="'+item.reply_num+'" data_text="'+item.reply_text+'" data_index="'+index+'"></td>';
			str += '<td><div class="updateContentsReplyFormDiv'+index+'"></div></td>';
			str += '</tr>';
			
			
		})
		
		 $('#replyDiv').html(str);
		
		
		/*삭제 시작*/
		$('.deleteContentsReply').on('click', function(){
			
			var reply_num = $(this).attr('data_num');
			console.log(reply_num);
			alert(reply_num);
			$.ajax({
				
				url : "contentsReplyDelete",
				type : "POST",
				contentType : "application/json; charset=UTF-8",
				data : JSON.stringify({
					
					reply_num : reply_num
			
				}),
				success : function(){
					
					
					init2();
				},
				
				error : function(error){
					
					console.log(error);
				}
				
			});
			
		});
		
		
		/*수정 시작*/
		$('.updateContentsReplyForm').on('click', function(){
			
			var reply_num = $(this).attr('data_num');
			var reply_text = $(this).attr('data_text');
			var index = $(this).attr('data_index');
			
			var str = '';
			
			console.log(reply_num);
			console.log(reply_text);
			console.log(index);
			
			str = '<input type="hidden" id ="reply_num"  value="'+reply_num+'">';
            str += '<input type="text" id ="reply_text2"  value="'+reply_text+'" >';
            str += '<input type="button" value="수정완료" id="updateContentsReply" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored">';
			
			
            $('.updateContentsReplyFormDiv'+index).html(str);
            console.log(str);
            
            
            $('#updateContentsReply').on('click', function(){
            	
            	var reply_num = $('#reply_num').val();
            	var reply_text = $('#reply_text2').val();
            	console.log(reply_num);
            	console.log(reply_text);
            	
            	$.ajax({
            	
            		url : "contentsReplyUpdate",
            		type : "POST",
            		contentType : "application/json; charset=utf-8",
            		data : JSON.stringify({
            		
            			reply_num : reply_num,
            			reply_text : reply_text
            			
            		}),
            		
            		success : function(){
            			
            		
            			init2();
            			
            			
            		},
            		
            		error : function(error){
            			
            			console.log(error);
            			
            		}
            		
            	});
            	
            });
		});
		
		$(document).scroll(function(){
			
			var maxHeight = $(document).height();
		    var currentScroll = $(window).scrollTop() + $(window).height();
		   // var flag = false;
			
		    
		   
		    if (maxHeight <= currentScroll + 100) {
		    	
		    /*	if(flag == true)return;
			    	setTimeout(() => {
						flag = true;
					}, 2000);
					*/
		    	
		    	$('html').scrollTop(0);
		    	
		    	if(loading){
		    		page = page+1;
		    		
		    		loading = false;
		    		
		    		$.ajax({
		    			
		    			url : "selectAllContentsReply",
		    			type : "POST",
		    			data : {
		    				
		    				contents_num : contents_num,
		    			    		 page : page
		    			},
		    			
		    			dataType : "json",
		    			success : function(obj){
		    				console.log("추가 페이지 리스트 ");
		    					console.log(obj);
		    				
		    				var str = '';
		    				
		    					$.each(obj.contentsReply, function(index, item){
		    					   
		    						if(index == 0){
			    							
			    					str +=	'<tr id="page'+page+'" >';
			    					
			    					}else{
			    						
			    						str += '<tr>';
			    						
			    					}
		    					
		    					 str += '<td class="mdl-data-table__cell--non-numeric">'+item.reply_num+'</td>';
		    					 str += '<td>'+item.member_id+'</td>';
		    					 str += '<td>'+item.reply_text+'</td>';
		    					 str += '<td>'+item.inputdate+'</td>';
		    					 str += '<td><input type="button" value="삭제" class="deleteContentsReply mdl-button mdl-js-button mdl-button--raised mdl-button--colored" data_num="'+item.reply_num+'"></td>'; 
		    					 str += '<td><input type="button" value="수정" class= "updateContentsReplyForm mdl-button mdl-js-button mdl-button--raised mdl-button--colored" data_num="'+item.reply_num+'" data_text="'+item.reply_text+'" data_index="'+index+'"></td>';
		    					 str += '<td><div class="updateContentsReplyFormDiv'+index+'"></div></td>';
		    					 str += '</tr>';
		    					  
		    					  
		    					  if(page <= obj.navi.currentPage){
		    						 $('#loading').addClass('mdl-spinner mdl-spinner--single-color mdl-js-spinner is-active');
		    						 loading = true;
		    						 
		    					  }else{
		    						  
		    						  $('#loading').removeClass('mdl-spinner mdl-spinner--single-color mdl-js-spinner is-active');
		    						  str ='';
		    						  loading = false;
		    						  //alert("끝 페이지 ");
		    						  $('html').scrollTop(0);
		    						
			    					
		    					  }
		    					 
		    					 
		    				});
		    					
		    					
		    				$('#page'+(page-1)).after(str);
 
		    		        /*   삭제 시작*/
		    		   		$('.deleteContentsReply').on('click', function(){
		    		   			
		    		   			var reply_num = $(this).attr('data_num');
		    		   			console.log(reply_num);
		    		   			alert(reply_num);
		    		   			$.ajax({
		    		   				
		    		   				url : "contentsReplyDelete",
		    		   				type : "POST",
		    		   				contentType : "application/json; charset=UTF-8",
		    		   				data : JSON.stringify({
		    		   					
		    		   					reply_num : reply_num
		    		   			
		    		   				}),
		    		   				success : function(){
		    		   					
		    		   					
		    		   					init2();
		    		   				},
		    		   				
		    		   				error : function(error){
		    		   					
		    		   					console.log(error);
		    		   				}
		    		   				
		    		   			});
		    		   			
		    		   		});
		    		   		
		    		   		
		    		   		/*수정 시작*/
		    		   		$('.updateContentsReplyForm').on('click', function(){
		    		   			
		    		   			var reply_num = $(this).attr('data_num');
		    		   			var reply_text = $(this).attr('data_text');
		    		   			var index = $(this).attr('data_index');
		    		   			
		    		   			var str = '';
		    		   			
		    		   			console.log(reply_num);
		    		   			console.log(reply_text);
		    		   			console.log(index);
		    		   			
		    		   			str = '<input type="hidden" id ="reply_num"  value="'+reply_num+'">';
		    		               str += '<input type="text" id ="reply_text2"  value="'+reply_text+'" >';
		    		               str += '<input type="button" value="수정완료" id="updateContentsReply" class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored">';
		    		   			
		    		   			
		    		               $('.updateContentsReplyFormDiv'+index).html(str);
		    		               console.log(str);
		    		               
		    		               
		    		               $('#updateContentsReply').on('click', function(){
		    		               	
		    		               	var reply_num = $('#reply_num').val();
		    		               	var reply_text = $('#reply_text2').val();
		    		               	console.log(reply_num);
		    		               	console.log(reply_text);
		    		               	
		    		               	$.ajax({
		    		               	
		    		               		url : "contentsReplyUpdate",
		    		               		type : "POST",
		    		               		contentType : "application/json; charset=utf-8",
		    		               		data : JSON.stringify({
		    		               		
		    		               			reply_num : reply_num,
		    		               			reply_text : reply_text
		    		               			
		    		               		}),
		    		               		
		    		               		success : function(){
		    		               			
		    		               			
		    		               			init2();
		    		               			//init3()
		    		               			
		    		               		},     		
		    		               		error : function(error){	    		               			
		    		               			console.log(error);
              			
		    		               		}     		
		    		               	});               	
		    		               });
		    		   		});       	
		    			},
		    			
		    			error : function(err){
		    				
		    				console.log(err);
		    				
		    			}
		    		});
		}    	
		    }
		});
	},
	error : function(error){
		
		console.log(error);
	}
	});
	
	/*좋아요 */
	$.ajax({
		url:"selectRecommendOrNot",
		type:"GET",
		data:{
			contents_num : contents_num
		},
		success: function(data){
			console.log(!data);
			if(!data){
				$('#thumbsUp').attr('src','/www/resources/icon/star_after.svg');
			}
			
		},
		error: function(err){
			console.log(err);
		}
	}); //2번 ajax
	
	$.ajax({
		url:"selectRecommendCount",
		type: "GET",
		data:{
			contents_num : contents_num
		},
		success: function(data){
			var str = '<h4>'+data+'</h4>';
			$('#recommendCountDiv').html(str);
		},
		error:function(err){
			console.log(err);
		}
	});//3번 ajax
	

	$.ajax({
		url : "selectBookmarkOrNot",
		type : "GET",
		data :{
			
			contents_num : contents_num
		},
		success : function(data){
			if(!data){
				$('#bookMark').attr('src','/www/resources/icon/bookmark_black.svg');
			}
			},
		error : function(err){
			console.log(err);
			
		}	
			
		});


}
//init2()함수 종료





