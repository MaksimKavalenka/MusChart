'use strict';
app.factory('ArtistFactory', ['$http', 'MESSAGE', 'REST', function($http, MESSAGE, REST) {
	return {

		createArtist: function(name, photo, genres, callback) {
			$http.post(REST.ARTISTS + '/create/' + name + '/' + photo + '/' + genres + REST.JSON_EXT)
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
			return $http.get(REST.ARTISTS + '/' + sort + '/' + order + '/' + page + REST.JSON_EXT)
			.success(function(response) {
				var data = {success: true, data: response};
				callback(data);
			})
			.error(function(response) {
				response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
				callback(response);
			});
		},

		getArtistsByCriteriaExt: function(relation, id, sort, order, page, callback) {
			return $http.get(REST.ARTISTS + '/' + relation + '/' + id + '/' + sort + '/' + order + '/' + page + REST.JSON_EXT)
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
			return $http.delete(REST.ARTISTS + '/delete/' + id + REST.JSON_EXT)
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