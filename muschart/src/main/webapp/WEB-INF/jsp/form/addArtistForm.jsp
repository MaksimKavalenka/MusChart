<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>Add Artist</title>
		<link rel="SHORTCUT ICON" href="/muschart/image/other/icon.png" type="image/x-icon">
		<link rel="stylesheet" type="text/css" href="/muschart/css/bootstrap.css"/>
	</head>

	<body>
		<c:import url="../title/editHeader.jsp"/>
		<div class="col-lg-8">
			<div class="well bs-component">
				<form method="POST" class="form-horizontal" action="/muschart/edit">
					<input type="hidden" name="action" value="add_artist">
					<fieldset>
						<legend>New artist</legend>
						<b class="error">${error}</b>
						<div class="form-group">
							<label for="name" class="col-lg-2 control-label">Name</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="name" name="name" placeholder="The name of a new artist" maxlength="255">
							</div>
						</div>
						<div class="form-group">
							<label for="image" class="col-lg-2 control-label">Image</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="image" name="image" placeholder="Choose an image of a new artist" maxlength="255">
							</div>
						</div>
						<c:import url="../other/buttonSubmit.jsp"/>
					</fieldset>
				</form>
			</div>
		</div>
	</body>
</html>