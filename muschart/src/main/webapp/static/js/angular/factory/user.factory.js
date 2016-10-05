'use strict';
app.factory('UserFactory', ['$http', 'MESSAGE', 'REST', function($http, MESSAGE, REST) {

	function authentication(login, password, callback) {
		var headers = {authorization: "Basic " + btoa(login + ":" + password)};
		$http.get(REST.USERS + '/auth' + REST.JSON_EXT, {'headers' : headers})
		.success(function(response) {
			if (response) {
				var data = {success: true, data: response};
				callback(data);
			} else {
				response = {success: false, message: MESSAGE.AUTHENTICATION_ERROR};
				callback(response);
			}
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.AUTHENTICATION_ERROR};
			callback(response);
		});
	}

	function logout() {
		$http.post(REST.USERS + '/logout' + REST.JSON_EXT, {});
	}

	function createUser(login, password, confirmPassword, callback) {
		$http.post(REST.USERS + '/create/' + login + '/' + password + '/' + confirmPassword + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			var data = {success: false, message: response.message};
			callback(data);
		});
	}

	function getUser(callback) {
		$http.get(REST.USERS + '/auth' + REST.JSON_EXT, {})
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_USER_ERROR};
			callback(response);
		});
	}

	function setUserLike(entity, entityId, callback) {
		$http.post(REST.USERS + '/like/' + entity + '/' + entityId + REST.JSON_EXT)
		.success(function(response) {
			response = {success: true};
			callback(response);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.UPDATING_USER_ERROR};
			callback(response);
		});
	}

	function checkLogin(login, callback) {
		$http.post(REST.USERS + '/check_login/' + login + REST.JSON_EXT)
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

	return {
		authentication: authentication,
		logout: logout,
		createUser: createUser,
		getUser: getUser,
		setUserLike: setUserLike,
		checkLogin: checkLogin
	};

}]);