<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="/muschart/css/bootstrap.css"/>
	</head>

	<body>
		<nav class="navbar navbar-default">
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><a href="/muschart/tracks">Tracks</a>
					<li><a href="/muschart/artists">Artists</a>
					<li><a href="/muschart/genres">Genres</a>
				</ul>
				<form class="navbar-form navbar-left" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
				<ul class="nav navbar-nav navbar-right">
					<c:if test="${empty user}">
						<li><a href="/muschart/login">Login</a>
						<li><a href="/muschart/registration">Registration</a>
					</c:if>
				</ul>
			</div>

			<c:if test="${not empty user}">
				<form method="POST" name="logoutForm" action="/muschart/edit">
					<input type="hidden" name="action" value="logout">
				</form>
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li><a href="/muschart/user_tracks">My tracks</a>
						<li><a href="/muschart/user_artists">My artists</a>
						<li><a href="/muschart/user_genres">My genres</a>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<c:if test="${user.role.toString() eq 'admin'}">
							<li><a href="/muschart/track/add">Edit</a>
						</c:if>
						<li><a onclick="logoutForm.submit()">Logout</a>
					</ul>
				</div>
			</c:if>
		</nav>
	</body>
</html>