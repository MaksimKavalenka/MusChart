'use strict';
app.factory('GenreFactory', ['$http', '$q', 'DEFAULT', function($http, $q, DEFAULT) {
	var genreUrl = DEFAULT.URL + '/genre/';
	return {

		createGenre: function(name, callback) {
			$http.post(genreUrl + 'create/' + name)
			.success(function(response) {
				response = {success: true, message: 'Genre has been added successfully'};
				callback(response);
			})
			.error(function(response) {
				response = {success: false, message: 'Error while adding genre'};
				callback(response);
			});
		},

		getGenresByIds: function(idFrom, idTo) {
			return $http.get(genreUrl + idFrom + '/' + idTo).then(
				function(response) {
					return response.data;
				},
				function(errResponse) {
					console.error('Error while getting genres');
					return $q.reject(errResponse);
				}
			);
		},

		getAllGenres: function() {
			return $http.get(genreUrl).then(
				function(response) {
					return response.data;
				},
				function(errResponse) {
					console.error('Error while getting genres');
					return $q.reject(errResponse);
				}
			);
		},

		deleteGenre: function(id) {
			return $http.delete(genreUrl + id).then(
				function(response) {
					return response.data;
				},
				function(errResponse) {
					console.error('Error while deleting genre');
					return $q.reject(errResponse);
				}
			);
		}

	};
}]);