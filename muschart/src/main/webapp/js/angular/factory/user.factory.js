'use strict';
app.factory('UserFactory', ['$http', 'MESSAGE', 'REST', function($http, MESSAGE, REST) {
	return {

		createUser: function(login, password, callback) {
			$http.post(REST.USERS + '/create/' + login + '/' + password + REST.JSON_EXT)
			.success(function(response) {
				response = {success: true};
				callback(response);
			})
			.error(function(response) {
				response = {success: false, message: MESSAGE.ADDING_USER_ERROR};
				callback(response);
			});
		},

		setUserLike: function(idUser, relation, id, callback) {
			$http.post(REST.USERS + '/' + idUser + '/' + relation + '/' + id + REST.JSON_EXT)
			.success(function(response) {
				response = {success: true};
				callback(response);
			})
			.error(function(response) {
				response = {success: false, message: MESSAGE.UPDATING_USER_ERROR};
				callback(response);
			});
		},

		getUser: function(login, password, callback) {
			$http.post(REST.USERS + '/' + login + '/' + password + REST.JSON_EXT)
			.success(function(response) {
				var data = {success: true, data: response};
				callback(data);
			})
			.error(function(response) {
				response = {success: false, message: MESSAGE.AUTHENTICATION_ERROR};
				callback(response);
			});
		},

		getUserByLogin: function(login, callback) {
			$http.post(REST.USERS + '/' + login + REST.JSON_EXT)
			.success(function(response) {
				if (response != '') {
					response = {success: true};
				} else {
					response = {success: false, message: MESSAGE.TAKEN_LOGIN_ERROR};
				}
				callback(response);
			})
			.error(function(response) {
				response = {success: false, message: MESSAGE.GETTING_USER_ERROR};
				callback(response);
			});
		}

	};
}]);