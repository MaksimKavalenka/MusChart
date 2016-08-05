'use strict';
app.service('FileService', ['$http', 'DEFAULT', function ($http, DEFAULT) {
	var uploadUrl = DEFAULT.URL + '/upload/';
	return {

		uploadFile: function(file, type, callback) {
			var formData = new FormData();
			formData.append('file', file);
			$http.post(uploadUrl + type, formData, {
				transformRequest: angular.identity,
				headers: {'Content-Type': undefined}
			})
			.success(function(response) {
				response = {success: true};
				callback(response);
			})
			.error(function(response) {
				response = {success: false, message: 'Error while saving file'};
				callback(response);
			});
		}

	};
}]);