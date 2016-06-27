<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<link rel="stylesheet" type="text/css" href="/muschart/css/style.css"/>
		<script type="text/javascript" src="/muschart/js/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="/muschart/js/scroll.js"></script>
		<script type="text/javascript">
			function setTag(tag) {
				document.tagForm.info.value = tag;
				document.tagForm.submit();
			}
		</script>
		<script>
			$(document).ready(function() {
				$('.likebutton').click(function() {
					var id = $(this).attr('data-id');
					var tagName = $(this).attr('data-genre');

					$.ajax({
						type: "POST",
						url: "edit?Name="+tagName+"&action=USER_GENRE",
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
		<div class="head">
			${data}
		</div>

		<c:choose>
			<c:when test="${empty tag}">
				<b class="error">
					Unfortunately, data are not found :(
				</b>
			</c:when>

			<c:otherwise>
				<form method="GET" name="tagForm" action="/web/tags">
					<input type="hidden" name="info" value="no">

					<p><b>What option would you like to search by?</b><br>
						<input checked type="radio" name="action" value="TRACK_TAG">Tracks
						<input type="radio" name="action" value="ACTOR_TAG">Actors<br>
					</p>

					<table class="body">
						<tr>
							<td></td><td></td><td></td><td></td><td></td>
						</tr>

						<c:forEach var="tag" items="${tag}" varStatus="counter">
							<td class="window">
								<div class="head">
									<c:if test="${not empty user}">
										<div id="button-like">
											<span class="likebutton" data-id="${counter.count}" data-tag="${tag.name}">
												<img src="/muschart/image/other/add.png" style="background-color:#FFFFFF" width="100%">
											</span>
										</div>
									</c:if>

									<a class="title" href="JavaScript:setTag('${tag.name}')">${tag.name}</a>

									<div id="rating${counter.count}" class="rating">
										${tag.rating}
									</div>
								</div>
							</td>

							<c:if test="${counter.count % 5 == 0}">
								<tr>
							</c:if>
						</c:forEach>
					</table>
				</form>
			</c:otherwise>
		</c:choose>
	</body>
</html>