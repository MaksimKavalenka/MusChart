<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<link rel="stylesheet" type="text/css" href="/muschart/css/style.css"/>
		<script type="text/javascript" src="/muschart/js/swfobject.js"></script>
		<script type="text/javascript" src="/muschart/js/player.js"></script>
		<script type="text/javascript" src="/muschart/js/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="/muschart/js/scroll.js"></script>
		<script>
			$(document).ready(function() {
				$('.likebutton').click(function() {
					var id = $(this).attr('data-id');
					var trackName = $(this).attr('data-track');

					$.ajax({
						type: "POST",
						url: "edit?Name="+trackName+"&action=USER_TRACK",
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
		<form method="GET" action="/muschart/tracks">
			<input type="hidden" name="info" value="${info}">
			Page: <input type="number" name="page" size="2" maxlength="2">
		</form>

		<div class="head">
			${data}
		</div>

		<c:choose>
			<c:when test="${empty track}">
				<b class="error">
					Unfortunately, data are not found :(
				</b>
			</c:when>

			<c:otherwise>
				<table class="body">
					<tr>
						<td></td><td></td><td></td><td></td><td></td>
					</tr>

					<c:forEach var="track" items="${track}" varStatus="counter">
						<td class="window">
							<div class="head">
								<c:if test="${not empty user}">
									<div id="button-like">
										<span class="likebutton" data-id="${counter.count}" data-track="${track.name}">
											<img id="add" src="/muschart/image/other/add.png" style="background-color:#FFFFFF" width="100%">
										</span>
									</div>
								</c:if>

								<div id="scroll${counter.count}" class="scroll">
									${track.name}
								</div>
								<iframe onLoad="setScroll(${counter.count})" style="display: none"></iframe>

								<div id="rating${counter.count}" class="rating">
									${track.rating}
								</div>
							</div>

							<div id="track${counter.count}Icon" class="trackIcon" onclick="GetPlayer(${counter.count},'${track.name}','${track.song}')">
    							<img src="${track.cover}" width="100%">
							</div>
						</td>

						<c:if test="${counter.count % 5 == 0}">
							<tr>
						</c:if>
     				</c:forEach>
				</table>

				<div id="track" class="footer">
					<div id="trackPlayer"></div>
				</div>
			</c:otherwise>
		</c:choose>
	</body>
</html>