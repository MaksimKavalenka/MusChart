<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="/muschart/css/styles.css"/>
	</head>
	<body>
		<div class="navbar navbar-default">
			<ul class="navigation">
				<li><a href="/muschart/tracks">Tracks</a>
				<li><a href="/muschart/artists">Artists</a>
				<li><a href="/muschart/genres">Genres</a>
			</ul>
			<form class="form-search" role="search">
				<input type="text" placeholder="Search">
				<button type="submit" class="btn btn-default">Submit</button>
			</form>
			<ul class="navigation to-right">
				<c:if test="${empty user}">
					<li><a href="/muschart/login">Login</a>
					<li><a href="/muschart/registration">Registration</a>
				</c:if>
			</ul>
		</div>
		<div class="navbar navbar-primary">
			<c:if test="${not empty user}">
				<form method="POST" name="logoutForm" action="/muschart/edit">
					<input type="hidden" name="action" value="logout">
				</form>
				<ul class="navigation">
					<li><a href="/muschart/user_tracks">My tracks</a>
					<li><a href="/muschart/user_artists">My artists</a>
					<li><a href="/muschart/user_genres">My genres</a>
				</ul>
				<ul class="navigation to-right">
					<c:if test="${user.role.toString() eq 'admin'}">
						<li><a href="/muschart/track/add">Edit</a>
					</c:if>
					<li><a href="#" onclick="logoutForm.submit()">Logout</a>
				</ul>
			</c:if>
		</div>
	</body>
</html>