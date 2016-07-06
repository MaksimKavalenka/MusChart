<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>Add track</title>
		<link rel="SHORTCUT ICON" href="/muschart/image/other/icon.png" type="image/x-icon">
		<link rel="stylesheet" type="text/css" href="/muschart/css/bootstrap.css"/>
	</head>

	<body>
		<c:import url="../title/editHeader.jsp"/>
		<div class="col-lg-8">
			<div class="well bs-component">
				<form method="POST" class="form-horizontal" enctype="multipart/form-data" action="/muschart/edit">
					<input type="hidden" name="action" value="add_track">
					<fieldset>
						<legend>New track</legend>
						<p class="text-danger"><strong>${error}</strong>
						<div class="form-group">
							<label for="name" class="col-lg-2 control-label">Name</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="name" name="name" placeholder="The name of the new track" maxlength="255">
							</div>
						</div>
						<div class="form-group">
							<label for="song" class="col-lg-2 control-label">Song</label>
							<div class="col-lg-10">
								<input type="file" class="form-control" id="song" name="song" placeholder="Choose the song" maxlength="255">
							</div>
						</div>
						<div class="form-group">
							<label for="cover" class="col-lg-2 control-label">Cover</label>
							<div class="col-lg-10">
								<input type="file" class="form-control" id="cover" name="cover" placeholder="Choose the cover" maxlength="255">
							</div>
						</div>
						<c:import url="../other/buttonSubmit.jsp"/>
					</fieldset>
				</form>
			</div>
		</div>
	</body>
</html>