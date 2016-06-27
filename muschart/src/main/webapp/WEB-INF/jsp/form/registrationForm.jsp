<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>Registration</title>
		<link rel="SHORTCUT ICON" href="/muschart/image/other/icon.png" type="image/x-icon">
		<link rel="stylesheet" type="text/css" href="/muschart/css/style.css"/>
		<link rel="stylesheet" type="text/css" href="/muschart/css/bootstrap.min.css">
	</head>

	<body>
		<c:import url="../title/header.jsp"/>
		<div class="col-lg-8">
			<div class="well bs-component">
				<form method="POST" class="form-horizontal" action="/muschart/edit">
					<input type="hidden" name="action" value="registration">
					<fieldset>
						<legend>Registration</legend>
						<b class="error">${error}</b>
						<div class="form-group">
							<label for="login" class="col-lg-2 control-label">Login</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="login" name="login" placeholder="Enter the login" maxlength="255">
							</div>
						</div>
						<div class="form-group">
							<label for="password" class="col-lg-2 control-label">Password</label>
							<div class="col-lg-10">
								<input type="password" class="form-control" id="password" name="password" placeholder="Enter the password" maxlength="255">
							</div>
						</div>
						<div class="form-group">
							<label for="checkPassword" class="col-lg-2 control-label">Password</label>
							<div class="col-lg-10">
								<input type="password" class="form-control" id="checkPassword" name="checkPassword" placeholder="Enter the password again" maxlength="255">
							</div>
						</div>
						<c:import url="../other/buttonSubmit.jsp"/>
					</fieldset>
				</form>
			</div>
		</div>
	</body>
</html>