<!DOCTYPE html>
<html
	class="js consumer build-stable chrome win win7 win64 win64-capable twisty-js"
	lang="en" id="win64">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
	(function(e, p) {
		var m = location.href.match(/platform=(win8|win|mac|linux|cros)/);
		e.id = (m && m[1])
				|| (p.indexOf('Windows NT 6.2') > -1 ? 'win8' : p
						.indexOf('Windows') > -1 ? 'win'
						: p.indexOf('Mac') > -1 ? 'mac'
								: p.indexOf('CrOS') > -1 ? 'cros' : 'linux');
		e.className = e.className.replace(/\bno-js\b/, 'js');
	})(document.documentElement, window.navigator.userAgent)
</script>

<meta content="initial-scale=1, minimum-scale=1, width=device-width"
	name="viewport">
<meta
	content="Google Chrome is a browser that combines a minimal design with sophisticated technology to make the web faster, safer, and easier."
	name="description">
<link href="https://plus.google.com/100585555255542998765"
	rel="publisher">
<link href="https://www.google.com/images/icons/product/chrome-32.png"
	rel="icon" type="image/ico">

<style>
#info {
	font-size: 20px;
}

#div_start {
	float: right;
}

#headline {
	text-decoration: none
}

#results {
	font-size: 14px;
	font-weight: bold;
	border: 1px solid #ddd;
	padding: 15px;
	text-align: left;
	min-height: 150px;
}

#start_button {
	border: 0;
	background-color: transparent;
	padding: 0;
}

.interim {
	color: gray;
}

.final {
	color: black;
	padding-right: 3px;
}

.button {
	display: none;
}

#buttons {
	margin: 10px 0;
	position: relative;
	top: -50px;
}

</style>
<style>
a.c1 {
	font-weight: normal;
}
</style>
</head>
<body class="" id="grid" style="">
	<div class="browser-landing" id="main">
		<div class="compact marquee">
			<div id="info" style="visibility: hidden;">
				<p id="info_start" style="display: none;">Click on the
					microphone icon and begin speaking for as long as you like.</p>
				<p id="info_speak_now" style="display: inline;">Speak now.</p>
				<p id="info_no_speech" style="display: none">
					No speech was detected. You may need to adjust your <a
						href="https://support.google.com/chrome/bin/answer.py?hl=en&amp;answer=1407892">microphone
						settings</a>.
				</p>
				<p id="info_no_microphone" style="display: none">
					No microphone was found. Ensure that a microphone is installed and
					that <a
						href="https://support.google.com/chrome/bin/answer.py?hl=en&amp;answer=1407892">
						microphone settings</a> are configured correctly.
				</p>
				<p id="info_allow" style="display: none;">Click the "Allow"
					button above to enable your microphone.</p>
				<p id="info_denied" style="display: none">Permission to use
					microphone was denied.</p>
				<p id="info_blocked" style="display: none">Permission to use
					microphone is blocked. To change, go to
					chrome://settings/contentExceptions#media-stream</p>
				<p id="info_upgrade" style="display: none">
					Web Speech API is not supported by this browser. Upgrade to <a
						href="https://www.google.com/chrome">Chrome</a> version 25 or
					later.
				</p>
			</div>
			<div id="div_start">
				<button id="start_button" onclick="startButton(event)"
					style="display: inline-block;">
					<img alt="Start" id="start_img">
				</button>
			</div>
			<div id="results">
				<span class="final" id="final_span"></span> <span class="interim"
					id="interim_span"></span>
			</div>
		</div>
	</div>
	
	<!-- 구글 webSTT스크립트 끝 -->
</body>
</html>