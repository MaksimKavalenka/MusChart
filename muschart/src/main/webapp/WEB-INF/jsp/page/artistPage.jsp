<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>Artists</title>
		<link rel="SHORTCUT ICON" href="/muschart/image/other/icon.png" type="image/x-icon">
		<link rel="stylesheet" type="text/css" href="/muschart/css/style.css"/>
	</head>

	<body>
		<table class="main">
			<tr class="title">
				<td>
					<c:import url="../title/header.jsp"/>
				</td>
			</tr>
			<tr class="body">
				<td>
					<c:import url="../body/artistBody.jsp"/>
				</td>
			</tr>
			<tr class="title">
				<td>
					<c:import url="../title/footer.jsp"/>
				</td>
			</tr>
		</table>
	</body>
</html>