'use strict';
app.factory('UserFactory', ['$http', 'DEFAULT', 'ERROR', function($http, DEFAULT, ERROR) {
	var user_url = DEFAULT.URL + '/user/';
	return {

		createUser: function(login, password, callback) {
			$http.post(user_url + 'create/' + login + '/' + password)
			.success(function(response) {
				response = {success: true};
				callback(response);
			})
			.error(function (response) {
				response = {success: false, message: 'Error while creating user'};
				callback(response);
			});
		},

		getUser: function(login, password, callback) {
			$http.post(user_url + login + '/' + password)
			.success(function(response) {
				var data = {success: true, data: response};
				callback(data);
			})
			.error(function (response) {
				response = {success: false, message: ERROR.AUTHENTICATION};
				callback(response);
			});
		},

		getUserByLogin: function(login, callback) {
			$http.post(user_url + login)
			.success(function(response) {
				if (response != '') {
					response = {success: true};
				} else {
					response = {success: false, message: ERROR.TAKEN_LOGIN};
				}
				callback(response);
			})
			.error(function (response) {
				response = {success: false, message: ERROR.TAKEN_LOGIN};
				callback(response);
			});
		}

	};
}]);