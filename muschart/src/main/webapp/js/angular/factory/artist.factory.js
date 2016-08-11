'use strict';
app.factory('ArtistFactory', ['$http', 'DEFAULT', 'MESSAGE', 'URL', function($http, DEFAULT, MESSAGE, URL) {
	return {

		createArtist: function(name, photo, genres, callback) {
			$http.post(URL.ARTISTS + '/create/' + name + '/' + photo + '/' + genres + DEFAULT.JSON_EXT)
			.success(function(response) {
				response = {success: true, message: MESSAGE.ADDING_ARTIST_SUCCESS};
				callback(response);
			})
			.error(function(response) {
				response = {success: false, message: MESSAGE.ADDING_ARTIST_ERROR};
				callback(response);
			});
		},

		getArtistsByCriteria: function(sort, order, page, callback) {
			return $http.get(URL.ARTISTS + '/' + sort + '/' + order + '/' + page + DEFAULT.JSON_EXT)
			.success(function(response) {
				var data = {success: true, data: response};
				callback(data);
			})
			.error(function(response) {
				response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
				callback(response);
			});
		},

		deleteArtist: function(id, callback) {
			return $http.delete(URL.ARTISTS + '/delete/' + id + DEFAULT.JSON_EXT)
			.success(function(response) {
				response = {success: true, message: MESSAGE.GELETING_ARTIST_SUCCESS};
				callback(data);
			})
			.error(function(response) {
				response = {success: false, message: MESSAGE.GELETING_ARTIST_ERROR};
				callback(response);
			});
		}

	};
}]);