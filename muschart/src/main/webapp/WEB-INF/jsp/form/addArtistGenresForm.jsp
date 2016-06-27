<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>Add Artist-Tag</title>
		<link rel="SHORTCUT ICON" href="/muschart/image/other/icon.png" type="image/x-icon">
		<link rel="stylesheet" type="text/css" href="/muschart/css/style.css"/>
	</head>

	<body>
		<c:import url="../title/editTitle.jsp"/>
		<hr color="FFD700">

		<form method="POST" name="addArtistTagForm" action="/web/edit">
			<input type=hidden name="action" value="ARTIST_TAG">
			<table class="form">
				<tr>
					<td></td>
					<td>Choose the artist and its tag</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<b class="error">${error}</b>
					</td>
				</tr>
				<tr>
					<td class="name">Artist</td>
					<td>
						<select name="Name">
							<option disabled>Choose the artist</option>
							<c:forEach var="artist" items="${Artist}">
								<option value="${artist.name}">${artist.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="name">Tag</td>
					<td>
						<c:forEach var="tag" items="${Tag}">
							<input type=checkbox name="${tag.name}">
							${tag.name}<br>
						</c:forEach>
					</td>
				</tr>
			</table>

			<input class="agree" type="submit" value="Add">
			<c:import url="../other/buttons.jsp"/>
		</form>
	</body>
</html>