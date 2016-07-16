<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>Registration</title>
		<link rel="shortcut icon" href="/muschart/image/icon.png" type="image/x-icon">
	</head>
	<body>
		<c:import url="../title/header.jsp"/>
		<form method="POST" class="form-submit form-validation" name="form" ng-submit="form.$valid" action="/muschart/registration">
			<fieldset>
				<legend>Registration</legend>
				<p class="text-danger text-bold">${error}
				<div class="field">
					<label class="required" for="login">Login</label>
					<div>
						<input type="text" id="login" ng-model="user.login" name="login" placeholder="Enter the login" maxlength="255" required checklogin/>
						<span ng-show="form.login.$pending.checklogin">Checking if this login is available...</span>
						<span ng-show="form.login.$error.checklogin">This login is already taken</span>
					</div>
				</div>
				<div class="field">
					<label class="required" for="password">Password</label>
					<div>
						<input type="password" id="password" ng-model="user.password" name="password" placeholder="Enter the password" maxlength="255" ng-minlength="3" required/>
						<span ng-show="form.password.$error.required || form.password.$error.minlength">Minimal count of symbols is 3</span>
					</div>
				</div>
				<div class="field">
					<label class="required" for="check_password">Repeat password</label>
					<div>
						<input type="password" id="check_password" ng-model="user.check_password" name="check_password" placeholder="Enter the password again" maxlength="255" ng-minlength="3" required/>
						<span ng-show="form.check_password.$error.required || form.check_password.$error.minlength">Minimal count of symbols is 3</span>
					</div>
				</div>
				<c:import url="../other/submit.jsp"/>
			</fieldset>
		</form>
	</body>
</html>