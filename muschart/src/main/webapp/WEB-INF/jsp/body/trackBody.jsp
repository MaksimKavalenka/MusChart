<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<link rel="stylesheet" type="text/css" href="/muschart/css/flaticon.css"/>
		<link rel="stylesheet" type="text/css" href="/muschart/css/styles.css"/>
		<script type="text/javascript" src="/muschart/js/swfobject.js"></script>
		<script type="text/javascript" src="/muschart/js/player.js"></script>
		<script type="text/javascript" src="/muschart/js/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="/muschart/js/scroll.js"></script>
		<script>
			$(document).ready(function() {
				$(".flaticon-like").click(function() {
					var id = $(this).attr("data-id");

					$.ajax({
						type: "POST",
						url: "edit?id="+id+"&action=USER_TRACK",
						dataType: "html",
						success: function(response) {
							$("#rating"+id).html(response);
				        }
					});
				});
			});
		</script>
	</head>

	<body>
		<form method="GET" id="form" action="/muschart/tracks">
			<input type="hidden" name="info" value="${info}">
			<c:import url="../other/pagination.jsp"/>
		</form>
		<p class="text-danger text-bold">${error}
		<ul class="content">
			<c:forEach var="track" items="${track}" varStatus="counter">
				<li>
					<div class="title">
						<div id="scroll${counter.count}" class="scroll">
							${track.name}
						</div>
						<iframe onLoad="setScroll(${counter.count})" style="display: none"></iframe>
					</div>

					<div class="body" id="track${counter.count}Icon" onclick="GetPlayer(${counter.count},'${track.name}','${track.song}')">
						<img src="${track.cover}">
					</div>
					<div class="actions">
						<div class="flaticon-sound"></div>
						<div class="flaticon-repeat"></div>
						<c:if test="${not empty user}">
							<div class="flaticon-download"></div>
							<div class="flaticon-video"></div>
							<div class="flaticon-like" id="rating${track.id}" data-id="${track.id}">${track.rating}</div>
						</c:if>
					</div>
			</c:forEach>
		</ul>

		<div id="track" class="footer">
			<div id="trackPlayer"></div>
		</div>

	</body>
</html>