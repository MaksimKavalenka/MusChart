'use strict';
app.factory('ArtistFactory', function($http, MESSAGE, REST, UtilityService) {

	function createArtist(name, photo, genresId, callback) {
		if (!UtilityService.allNotEmpty(callback, name, photo, genresId)) {
			return;
		}

		var artist = {
			name: name,
			photo: photo,
			genresId: genresId
		};

		$http.post(REST.ARTISTS, artist)
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
		if (!UtilityService.allNotEmpty(callback, id)) {
			return;
		}

		$http.delete(REST.ARTISTS + '/' + id)
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
		if (!UtilityService.allNotEmpty(callback, id)) {
			return;
		}

		$http.get(REST.ARTISTS + '/' + id)
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
		if (!UtilityService.allNotEmpty(callback, sort, order, page)) {
			return;
		}

		$http.get(REST.ARTISTS + '/' + sort + '/' + order + '/' + page)
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
		if (!UtilityService.allNotEmpty(callback, entity, entityId, sort, order, page)) {
			return;
		}

		$http.get(REST.ARTISTS + '/' + entity + '/' + entityId + '/' + sort + '/' + order + '/' + page)
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
		if (!UtilityService.allNotEmpty(callback, sort, order, page)) {
			return;
		}

		$http.get(REST.ARTISTS + '/user/' + sort + '/' + order + '/' + page)
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
		$http.get(REST.ARTISTS + '/all/id_name')
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
		$http.get(REST.ARTISTS + '/pages_count')
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
		if (!UtilityService.allNotEmpty(callback, entity, entityId)) {
			return;
		}

		$http.get(REST.ARTISTS + '/' + entity + '/' + entityId + '/pages_count')
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
		$http.get(REST.ARTISTS + '/user/pages_count')
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

});