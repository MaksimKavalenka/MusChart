<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>Login</title>
		<link rel="shortcut icon" href="/muschart/image/icon.png" type="image/x-icon">
	</head>
	<body>
		<c:import url="../title/header.jsp"/>
		<form method="POST" class="form-submit form-validation" name="form" action="/muschart/login">
			<fieldset>
				<legend>Login</legend>
				<p class="text-danger text-bold">${error}
				<div class="field">
					<label class="required" for="login">Login</label>
					<input type="text" id="login" name="login" placeholder="Your login" maxlength="255" ng-model="user.login" required/>
				</div>
				<div class="field">
					<label class="required" for="password">Password</label>
					<div>
						<input type="password" id="password" name="password" placeholder="Enter the password" maxlength="255" ng-model="user.password" ng-minlength="3" required/>
						<span ng-show="form.password.$error.required || form.password.$error.minlength">Minimal count of symbols is 3</span>
					</div>
				</div>
				<c:import url="../other/submit.jsp"/>
			</fieldset>
		</form>
	</body>
</html>