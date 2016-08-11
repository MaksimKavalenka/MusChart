'use strict';
app.factory('GenreFactory', ['$http', 'DEFAULT', 'MESSAGE', 'URL', function($http, DEFAULT, MESSAGE, URL) {
	return {

		createGenre: function(name, callback) {
			$http.post(URL.GENRES + '/create/' + name + DEFAULT.JSON_EXT)
			.success(function(response) {
				response = {success: true, message: MESSAGE.ADDING_GENRE_SUCCESS};
				callback(response);
			})
			.error(function(response) {
				response = {success: false, message: MESSAGE.ADDING_GENRE_ERROR};
				callback(response);
			});
		},

		getGenreByName: function(name, callback) {
			$http.post(URL.GENRES + '/' + name + DEFAULT.JSON_EXT)
			.success(function(response) {
				if (response != '') {
					response = {success: true};
				} else {
					response = {success: false, message: MESSAGE.EXIST_GENRE_ERROR};
				}
				callback(response);
			})
			.error(function(response) {
				response = {success: false, message: MESSAGE.GETTING_GENRE_ERROR};
				callback(response);
			});
		},

		getGenresByCriteria: function(sort, order, page, callback) {
			return $http.get(URL.GENRES + '/' + sort + '/' + order + '/' + page + DEFAULT.JSON_EXT)
			.success(function(response) {
				var data = {success: true, data: response};
				callback(data);
			})
			.error(function(response) {
				response = {success: false, message: MESSAGE.GETTING_GENRE_ERROR};
				callback(response);
			});
		},

		deleteGenre: function(id, callback) {
			return $http.delete(URL.GENRES + '/delete/' + id + DEFAULT.JSON_EXT)
			.success(function(response) {
				response = {success: true, message: MESSAGE.DELETING_GENRE_SUCCESS};
				callback(data);
			})
			.error(function(response) {
				response = {success: false, message: MESSAGE.DELETING_GENRE_ERROR};
				callback(response);
			});
		}

	};
}]);