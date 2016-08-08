'use strict';
app.service('ChoiceService', ['$http', '$rootScope', 'DEFAULT', 'URL', function($http, $rootScope, DEFAULT, URL) {
	return {

		getAllGenres: function(callback) {
			$rootScope.genres = [];
			return $http.get(URL.GENRES + DEFAULT.JSON_EXT)
			.success(function(response) {
				$rootScope.genres = response;
				response = {success: true};
				callback(response);
			})
			.error(function(response) {
				response = {success: false, message: 'Error while getting genres'};
				callback(response);
			});
		}

	};
}]);