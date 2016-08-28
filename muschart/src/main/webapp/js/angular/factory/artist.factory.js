'use strict';
app.factory('ArtistFactory', ['$http', 'MESSAGE', 'REST', function($http, MESSAGE, REST) {

	function createArtist(name, photo, genres, callback) {
		$http.post(REST.ARTISTS + '/create/' + name + '/' + photo + '/' + genres + REST.JSON_EXT)
		.success(function(response) {
			response = {success: true, message: MESSAGE.ADDING_ARTIST_SUCCESS};
			callback(response);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.ADDING_ARTIST_ERROR};
			callback(response);
		});
	}

	function deleteArtist(id, callback) {
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

	function getArtistById(id, callback) {
		return $http.get(REST.ARTISTS + '/' + id + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
			callback(response);
		});
	}

	function getArtistsByCriteria(sort, order, page, callback) {
		return $http.get(REST.ARTISTS + '/' + sort + '/' + order + '/' + page + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
			callback(response);
		});
	}

	function getArtistsByCriteriaExt(relation, id, sort, order, page, callback) {
		return $http.get(REST.ARTISTS + '/' + relation + '/' + id + '/' + sort + '/' + order + '/' + page + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
			callback(response);
		});
	}

	function getAllArtists(callback) {
		return $http.get(REST.ARTISTS + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
			callback(response);
		});
	}

	return {
		createArtist: createArtist,
		deleteArtist: deleteArtist,
		getArtistById: getArtistById,
		getArtistsByCriteria: getArtistsByCriteria,
		getArtistsByCriteriaExt: getArtistsByCriteriaExt,
		getAllArtists: getAllArtists
	};
}]);