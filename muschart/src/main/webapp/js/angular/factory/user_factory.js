'use strict';
app.factory('UserFactory', ['$http', '$q', function($http, $q) {
	var user_url = url + '/user/';
	return {

		ifExists: function(login) {
			return $http.post(user_url + login).then(
				function(response) {
					return response.data;
				},
				function(errResponse) {
					console.error('Error while checking login');
					return $q.reject(errResponse);
				}
			);
		}

	};
}]);