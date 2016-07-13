<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>Registration</title>
		<link rel="SHORTCUT ICON" href="/muschart/image/other/icon.png" type="image/x-icon">
		<link rel="stylesheet" type="text/css" href="/muschart/css/style.css"/>
	</head>
	<body>
		<c:import url="../title/header.jsp"/>
		<form method="POST" action="/muschart/registration">
			<fieldset>
				<legend>Registration</legend>
				<p class="text-danger">${error}
				<div>
					<label for="login">Login</label>
					<input type="text" id="login" name="login" placeholder="Enter the login" maxlength="255"/>
				</div>
				<div>
					<label for="password">Password</label>
					<input type="password" id="password" name="password" placeholder="Enter the password" maxlength="255"/>
				</div>
				<div>
					<label for="checkPassword">Password</label>
					<input type="password" id="checkPassword" name="checkPassword" placeholder="Enter the password again" maxlength="255"/>
				</div>
				<c:import url="../other/submit.jsp"/>
			</fieldset>
		</form>
	</body>
</html>