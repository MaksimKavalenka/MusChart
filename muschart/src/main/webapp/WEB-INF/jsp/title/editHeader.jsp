<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="/muschart/css/styles.css"/>
	</head>
	<body>
		<c:import url="../title/header.jsp"/>
		<div class="navbar navbar-success">
			<ul class="navigation">
				<li><a href="/muschart/track/add">Add track</a>
				<li><a href="/muschart/artist/add">Add artist</a>
				<li><a href="/muschart/genre/add">Add genre</a>
				<li><a href="/muschart/track_artists/add">Add track's artists</a>
				<li><a href="/muschart/track_genres/add">Add track's genres</a>
				<li><a href="/muschart/artist_genres/add">Add artist's genres</a>
			</ul>
		</div>
		<div class="navbar navbar-danger">
			<ul class="navigation">
				<li><a href="/muschart/track/delete">Delete track</a>
				<li><a href="/muschart/artist/delete">Delete artist</a>
				<li><a href="/muschart/genre/delete">Delete genre</a>
				<li><a href="/muschart/track_artists/delete">Delete track's artists</a>
				<li><a href="/muschart/track_genres/delete">Delete track's genres</a>
				<li><a href="/muschart/artist_genres/delete">Delete artist's genres</a>
			</ul>
		</div>
	</body>
</html>