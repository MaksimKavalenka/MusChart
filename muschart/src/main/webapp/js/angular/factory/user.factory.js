'use strict';
app.factory('UserFactory', ['$http', 'DEFAULT', 'ERROR', 'URL', function($http, DEFAULT, ERROR, URL) {
	return {

		createUser: function(login, password, callback) {
			$http.post(URL.USERS + '/create/' + login + '/' + password + DEFAULT.JSON_EXT)
			.success(function(response) {
				response = {success: true};
				callback(response);
			})
			.error(function(response) {
				response = {success: false, message: 'Error while adding user'};
				callback(response);
			});
		},

		getUser: function(login, password, callback) {
			$http.post(URL.USERS + '/' + login + '/' + password + DEFAULT.JSON_EXT)
			.success(function(response) {
				var data = {success: true, data: response};
				callback(data);
			})
			.error(function(response) {
				response = {success: false, message: ERROR.AUTHENTICATION};
				callback(response);
			});
		},

		getUserByLogin: function(login, callback) {
			$http.post(URL.USERS + '/' + login + DEFAULT.JSON_EXT)
			.success(function(response) {
				if (response != '') {
					response = {success: true};
				} else {
					response = {success: false, message: ERROR.TAKEN_LOGIN};
				}
				callback(response);
			})
			.error(function(response) {
				response = {success: false, message: ERROR.TAKEN_LOGIN};
				callback(response);
			});
		}

	};
}]);