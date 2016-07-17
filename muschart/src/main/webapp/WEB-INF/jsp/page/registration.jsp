<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>Registration</title>
		<link rel="shortcut icon" href="/muschart/image/icon.png" type="image/x-icon">
	</head>
	<body>
		<c:import url="../title/header.jsp"/>
		<form method="POST" class="form-submit form-validation" name="form" action="/muschart/registration">
			<fieldset>
				<legend>Registration</legend>
				<p class="text-danger text-bold">${error}
				<div class="field">
					<label class="required" for="login">Login</label>
					<div>
						<input type="text" id="login" name="login" placeholder="Enter the login" maxlength="255" ng-model="user.login" ng-exist required/>
						<span ng-show="form.login.$pending.ngExist">Checking if this login is available...</span>
						<span ng-show="form.login.$error.ngExist">This login is already taken</span>
					</div>
				</div>
				<div class="field">
					<label class="required" for="password">Password</label>
					<div>
						<input type="password" id="password" name="password" placeholder="Enter the password" maxlength="255" ng-model="user.password" ng-minlength="3" required/>
						<span ng-show="form.password.$error.required || form.password.$error.minlength">Minimal count of symbols is 3</span>
					</div>
				</div>
				<div class="field">
					<label class="required" for="confirm_password">Confirm password</label>
					<div>
						<input type="password" id="confirm_password" name="confirm_password" placeholder="Confirm the password" maxlength="255" ng-model="user.confirm_password" ng-match="user.password" ng-minlength="3" required/>
						<span ng-show="form.confirm_password.$error.required || form.confirm_password.$error.minlength">Minimal count of symbols is 3</span>
						<span ng-show="form.confirm_password.$error.ngMatch && !form.confirm_password.$error.required && !form.confirm_password.$error.minlength">Passwords do not match</span>
					</div>
				</div>
				<c:import url="../other/submit.jsp"/>
			</fieldset>
		</form>
	</body>
</html>