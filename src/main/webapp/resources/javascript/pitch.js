/**
 * 
 */
$(function(){
	

		function __log(e, data) {
			log.innerHTML += e + " " + (data || '') + '\n';
		}

		function Pitch(x,y){
			this.x = x;
			this.y = y;
		}
		
		
		function draw(arr,container,title){
			var pitchArr = [];
			
			for(var i = 0; i<arr.length; i++){
				pitchArr.push({x : i, y: arr[i]});
			}
			
			
			var chart = new CanvasJS.Chart(container,{
				animationEnabled : true,
			title : {
				title
			},
			axisY: {
				title : "",
				valueFormatString :"#0.",
				suffix:"pi",
				prefix : ""
			},
			data: [{
				type : "splineArea",
				color : "rgba(54,158,173,.7)",
				xValueFormatString: "##0",
				yValueFormatString: "$#,##0.##",
				makerSize : 5,
				dataPoints: pitchArr
			}]
			});
			
			chart.render();
		}

		var audio_context;
		var recorder;

		function startUserMedia(stream) {
			var input = audio_context.createMediaStreamSource(stream);
			__log('Media stream created.');

			recorder = new Recorder(input);
			__log('Recorder initialised.');
		}

		function startRecording(button) {
			recorder && recorder.record();
			button.disabled = true;
			button.nextElementSibling.disabled = false;
			__log('Recording...');
		}

		function stopRecording(button) {
			recorder && recorder.stop();
			button.disabled = true;
			button.previousElementSibling.disabled = false;
			__log('Stopped recording.');

			createDownloadLink();
			// var final_span_val = $('#final_span').val();
			alert(interim_transcript);
			recorder.clear();
		}

		function createDownloadLink() {
			recorder && recorder.exportWAV(function(blob) {
				// var contents_num = 223;
				var contents_num = $('#contents_num').val();
				var start = $('#start').val();
				var dur = $('#dur').val();
				
				// alert(contents_num + " "+ start +" "+dur);
				
				var reader = new FileReader();
				var base64data;
				
				reader.readAsDataURL(blob);
				reader.onloadend = function(fileLoadedEvent) {
					base64data = reader.result;
					// alert(reader.result);
					var fd = new FormData();
					fd.append('file', base64data);
					fd.append("contents_num",contents_num);
					fd.append("start",start);
					fd.append("dur",dur);
					
					$.ajax({			
						type : 'POST',
						url : 'transcript/streamOnMic',
						data : fd,
						processData : false,
						contentType : false,
						success:function(data){
							console.log(data);
							draw(data.ytArr,ytPitch,'youtube');
							draw(data.memArr,memPitch,'member');
							
						},
						error: function(e){			
							console.log(e);
						}
					})/*
						 * .done(function(data) { console.log(data); });
						 */
					
				}

			});
		}

		window.onload = function init() {
			try {
				window.AudioContext = window.AudioContext
						|| window.webkitAudioContext;
				navigator.getUserMedia = navigator.getUserMedia
						|| navigator.webkitGetUserMedia;
				window.URL = window.URL || window.webkitURL;

				audio_context = new AudioContext;
				__log('Audio context set up.');
				__log('navigator.getUserMedia '
						+ (navigator.getUserMedia ? 'available.'
								: 'not present!'));
			} catch (e) {
				alert('No web audio support in this browser!');
			}

			navigator.getUserMedia({
				audio : true
			}, startUserMedia, function(e) {
				__log('No live audio input: ' + e);
			});
		};

		var recLength = 0, recBuffers = [], sampleRate = undefined, numChannels = undefined;

		self.onmessage = function(e) {
			switch (e.data.command) {
			case 'init':
				init(e.data.config);
				break;
			case 'record':
				record(e.data.buffer);
				break;
			case 'exportWAV':
				exportWAV(e.data.type);
				break;
			case 'getBuffer':
				getBuffer();
				break;
			case 'clear':
				clear();
				break;
			}
		};
});
