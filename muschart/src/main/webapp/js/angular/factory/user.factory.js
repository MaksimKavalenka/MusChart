'use strict';
app.factory('UserFactory', ['$http', 'MESSAGE', 'REST', function($http, MESSAGE, REST) {

	function createUser(login, password, callback) {
		$http.post(REST.USERS + '/create/' + login + '/' + password + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.CREATING_USER_ERROR};
			callback(response);
		});
	}

	function authentication(login, password, callback) {
		$http.post(REST.USERS + '/auth/' + login + '/' + password + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.AUTHENTICATION_ERROR};
			callback(response);
		});
	}

	function checkLogin(login, callback) {
		$http.post(REST.USERS + '/checkLogin/' + login + REST.JSON_EXT)
		.success(function(response) {
			if (response) {
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

	function setUserLike(idUser, relation, id, callback) {
		$http.post(REST.USERS + '/' + idUser + '/' + relation + '/' + id + REST.JSON_EXT)
		.success(function(response) {
			response = {success: true};
			callback(response);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.UPDATING_USER_ERROR};
			callback(response);
		});
	}

	return {
		createUser: createUser,
		authentication: authentication,
		checkLogin: checkLogin,
		setUserLike: setUserLike
	};
}]);