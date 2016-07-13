<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<link rel="SHORTCUT ICON" href="/muschart/image/icon.png" type="image/x-icon">
		<link rel="stylesheet" type="text/css" href="/muschart/css/amplitude.css"/>
		<link rel="stylesheet" type="text/css" href="/muschart/css/content.css"/>
		<link rel="stylesheet" type="text/css" href="/muschart/css/flaticon.css"/>
		<link rel="stylesheet" type="text/css" href="/muschart/css/styles.css"/>
		<script type="text/javascript" src="/muschart/js/other/amplitude.js"></script>
		<script type="text/javascript" src="/muschart/js/other/angular.js"></script>
		<script type="text/javascript" src="/muschart/js/other/scroll.js"></script>
		<script type="text/javascript" src="/muschart/js/app.js"></script>
		<script type="text/javascript" src="/muschart/js/controller/track_controller.js"></script>
		<script type="text/javascript" src="/muschart/js/service/track_service.js"></script>
	</head>

	<body ng-app="MusChart">
		<c:import url="title/header.jsp"/>
		<form method="GET" name="form">

		</form>
		<p class="text-danger text-bold">${error}
		<div ng-controller="TrackController as track_ctrl">
			<ul class="content">
				<li ng-repeat="track in track_ctrl.tracks">
					<div class="title">
						<div id="scroll{{track.id}}" class="scroll">{{track.name}}</div>
						<iframe onload="setScroll({{track.id}})" style="display: none"></iframe>
					</div>
					<div class="body" id="track{{track.id}}Icon" ng-click="GetPlayer({{track.id}},'{{track.name}}','{{track.song}}')">
						<img src="{{track.cover}}">
					</div>
					<div class="actions">
						<div class="flaticon-sound"></div>
						<div class="flaticon-repeat"></div>
						<c:if test="${not empty user}">
							<div class="flaticon-download"></div>
							<div class="flaticon-video"></div>
							<div class="flaticon-like" id="rating{{track.id}}" data-id="{{track.id}}">{{track.rating}}</div>
						</c:if>
					</div>
			</ul>
		</div>
		<div id="player-container">
				<div id="amplitude-album-art">
					<img src="images/wtmj620.png"/>
				</div>
				<div id="player">
					<div id="song-information">
						<span id="amplitude-now-playing-name">WTMJ 620 AM</span><br>
						<span id="amplitude-now-playing-album">Milwaukee, WI</span>
					</div>
					<div id="controls">
						<div id="amplitude-play-pause" class="amplitude-paused"></div><br>
						<span id="amplitude-current-time">0:00</span>
					</div>
					<input class="bar" type="range" id="amplitude-volume-slider" value="0"/>
				</div>
			</div>
<script type="text/javascript">
    Amplitude.init({
        "songs": [
            {
                "name": "Song Name 1",
                "artist": "Artist Name",
                "album": "Album Name",
                "url": "/muschart/audio/Lil%20Jon%20feat.%20the%20East%20Side%20Boyz%20song%20-%20Get%20Low.mp3",
                "cover_art_url": "/muschart/image/track/Lil Jon & the East Side Boyz song - Get Low.jpg"
            }
        ],
    });
</script>
	</body>
</html>