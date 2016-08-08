'use strict';
app.factory('GenreFactory', ['$http', 'DEFAULT', 'URL', function($http, DEFAULT, URL) {
	return {

		createGenre: function(name, callback) {
			$http.post(URL.GENRES + '/create/' + name + DEFAULT.JSON_EXT)
			.success(function(response) {
				response = {success: true, message: 'Genre has been added successfully'};
				callback(response);
			})
			.error(function(response) {
				response = {success: false, message: 'Error while adding genre'};
				callback(response);
			});
		},

		getGenresByIds: function(idFrom, idTo, callback) {
			return $http.get(URL.GENRES + '/' + idFrom + '/' + idTo + DEFAULT.JSON_EXT)
			.success(function(response) {
				var data = {success: true, data: response};
				callback(data);
			})
			.error(function(response) {
				response = {success: false, message: 'Error while getting genres'};
				callback(response);
			});
		},

		deleteGenre: function(id, callback) {
			return $http.delete(URL.GENRES + '/delete/' + id + DEFAULT.JSON_EXT)
			.success(function(response) {
				response = {success: true, message: 'Genre has been deleted successfully'};
				callback(data);
			})
			.error(function(response) {
				response = {success: false, message: 'Error while deleting genre'};
				callback(response);
			});
		}

	};
}]);