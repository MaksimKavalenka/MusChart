'use strict';
app.factory('GenreFactory', ['$http', 'DEFAULT', function($http, DEFAULT) {
	var genresUrl = DEFAULT.URL + '/genres';
	return {

		createGenre: function(name, callback) {
			$http.post(genresUrl + '/create/' + name + DEFAULT.JSON_EXT)
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
			return $http.get(genresUrl + '/' + idFrom + '/' + idTo + DEFAULT.JSON_EXT)
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
			return $http.delete(genresUrl + '/delete/' + id + DEFAULT.JSON_EXT)
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