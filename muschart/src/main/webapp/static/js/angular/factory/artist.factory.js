'use strict';
app.factory('ArtistFactory', ['$http', 'MESSAGE', 'REST', function($http, MESSAGE, REST) {

	function createArtist(name, photo, genres, callback) {
		$http.post(REST.ARTISTS + '/create/' + name + '/' + photo + '/' + genres + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response, message: MESSAGE.CREATING_ARTIST_SUCCESS};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.CREATING_ARTIST_ERROR};
			callback(response);
		});
	}

	function deleteArtist(id, callback) {
		$http.delete(REST.ARTISTS + '/delete/' + id + REST.JSON_EXT)
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
		$http.get(REST.ARTISTS + '/' + id + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
			callback(response);
		});
	}

	function getArtists(sort, order, page, callback) {
		$http.get(REST.ARTISTS + '/' + sort + '/' + order + '/' + page + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
			callback(response);
		});
	}

	function getEntityArtists(entity, entityId, sort, order, page, callback) {
		$http.get(REST.ARTISTS + '/' + entity + '/' + entityId + '/' + sort + '/' + order + '/' + page + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
			callback(response);
		});
	}

	function getUserArtists(sort, order, page, callback) {
		$http.get(REST.ARTISTS + '/user/' + sort + '/' + order + '/' + page + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
			callback(response);
		});
	}

	function getAllArtistsIdAndName(callback) {
		$http.get(REST.ARTISTS + '/all/id_name' + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
			callback(response);
		});
	}

	function getPagesCount(callback) {
		$http.get(REST.ARTISTS + '/pages_count' + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
			callback(response);
		});
	}

	function getEntityPagesCount(entity, entityId, callback) {
		$http.get(REST.ARTISTS + '/' + entity + '/' + entityId + '/pages_count' + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
			callback(response);
		});
	}

	function getUserPagesCount(callback) {
		$http.get(REST.ARTISTS + '/user/pages_count' + REST.JSON_EXT)
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
		getArtists: getArtists,
		getEntityArtists: getEntityArtists,
		getUserArtists: getUserArtists,
		getAllArtistsIdAndName: getAllArtistsIdAndName,
		getPagesCount: getPagesCount,
		getEntityPagesCount: getEntityPagesCount,
		getUserPagesCount: getUserPagesCount
	};

}]);