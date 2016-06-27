<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>Delete Track</title>
		<link rel="SHORTCUT ICON" href="/muschart/image/other/icon.png" type="image/x-icon">
		<link rel="stylesheet" type="text/css" href="/muschart/css/style.css"/>
	</head>

	<body>
		<c:import url="../title/editTitle.jsp"/>
		<hr color="FFD700">

		<form method="POST" name="deleteTrackForm" action="/web/edit">
			<input type=hidden name="action" value="DELETE_TRACK">
			<table class="form">
				<tr>
					<td></td>
					<td>Choose the track you want to delete</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<b class="error">${error}</b>
					</td>
				</tr>
				<tr>
					<td class="name">Track</td>
					<td>
						<select name="Name">
							<option disabled>Choose the track</option>
							<c:forEach var="track" items="${Track}">
								<option value="${track.name}">${track.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>

			<input class="agree" type="submit" value="Delete">
			<c:import url="../other/buttons.jsp"/>
		</form>
	</body>
</html>