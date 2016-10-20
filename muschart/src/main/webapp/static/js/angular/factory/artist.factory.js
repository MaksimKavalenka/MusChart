'use strict';
app.factory('ArtistFactory', ['$http', 'MESSAGE', 'REST', 'ValidatorService', function($http, MESSAGE, REST, ValidatorService) {

	function createArtist(name, photo, genres, callback) {
		if (!ValidatorService.allNotEmpty(callback, name, photo, genres)) {
			return;
		}
		$http.post(REST.ARTIST + '/create/' + name + '/' + photo + '/' + genres + REST.JSON_EXT)
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
		if (!ValidatorService.allNotEmpty(callback, id)) {
			return;
		}
		$http.delete(REST.ARTIST + '/delete/' + id + REST.JSON_EXT)
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
		if (!ValidatorService.allNotEmpty(callback, id)) {
			return;
		}
		$http.get(REST.ARTIST + '/get/' + id + REST.JSON_EXT)
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
		if (!ValidatorService.allNotEmpty(callback, sort, order, page)) {
			return;
		}
		$http.get(REST.ARTIST + '/get/' + sort + '/' + order + '/' + page + REST.JSON_EXT)
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
		if (!ValidatorService.allNotEmpty(callback, entity, entityId, sort, order, page)) {
			return;
		}
		$http.get(REST.ARTIST + '/get/' + entity + '/' + entityId + '/' + sort + '/' + order + '/' + page + REST.JSON_EXT)
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
		if (!ValidatorService.allNotEmpty(callback, sort, order, page)) {
			return;
		}
		$http.get(REST.ARTIST + '/user/' + sort + '/' + order + '/' + page + REST.JSON_EXT)
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
		$http.get(REST.ARTIST + '/get/all/id_name' + REST.JSON_EXT)
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
		$http.get(REST.ARTIST + '/get/pages_count' + REST.JSON_EXT)
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
		if (!ValidatorService.allNotEmpty(callback, entity, entityId)) {
			return;
		}
		$http.get(REST.ARTIST + '/get/' + entity + '/' + entityId + '/pages_count' + REST.JSON_EXT)
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
		$http.get(REST.ARTIST + '/user/pages_count' + REST.JSON_EXT)
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