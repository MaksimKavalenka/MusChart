'use strict';
app.service('FileService', ['$http', 'REST', function($http, REST) {
	return {

		uploadFile: function(file, type, callback) {
			var formData = new FormData();
			formData.append('file', file);
			$http.post(REST.UPLOAD + '/' + type + REST.JSON_EXT, formData, {
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