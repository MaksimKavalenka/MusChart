'use strict';
app.factory('GenreFactory', ['$http', 'MESSAGE', 'REST', function($http, MESSAGE, REST) {

	function createGenre(name, callback) {
		$http.post(REST.GENRES + '/create/' + name + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response, message: MESSAGE.CREATING_GENRE_SUCCESS};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.CREATING_GENRE_ERROR};
			callback(response);
		});
	}

	function deleteGenre(id, callback) {
		$http.delete(REST.GENRES + '/delete/' + id + REST.JSON_EXT)
		.success(function(response) {
			response = {success: true, message: MESSAGE.DELETING_GENRE_SUCCESS};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.DELETING_GENRE_ERROR};
			callback(response);
		});
	}

	function getGenreById(id, callback) {
		$http.get(REST.GENRES + '/' + id + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_GENRE_ERROR};
			callback(response);
		});
	}

	function getGenres(sort, order, page, callback) {
		$http.get(REST.GENRES + '/' + sort + '/' + order + '/' + page + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_GENRE_ERROR};
			callback(response);
		});
	}

	function getEntityGenres(entity, entityId, sort, order, page, callback) {
		$http.get(REST.GENRES + '/' + entity + '/' + entityId + '/' + sort + '/' + order + '/' + page + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_GENRE_ERROR};
			callback(response);
		});
	}

	function getUserGenres(sort, order, page, callback) {
		$http.get(REST.GENRES + '/user/' + sort + '/' + order + '/' + page + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_GENRE_ERROR};
			callback(response);
		});
	}

	function getAllGenresIdAndName(callback) {
		$http.get(REST.GENRES + '/all/id/name' + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_GENRE_ERROR};
			callback(response);
		});
	}

	function getPageCount(callback) {
		$http.get(REST.GENRES + '/page_count' + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
			callback(response);
		});
	}

	function getEntityPageCount(entity, entityId, callback) {
		$http.get(REST.GENRES + '/' + entity + '/' + entityId + '/page_count' + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_GENRE_ERROR};
			callback(response);
		});
	}

	function getUserPageCount(callback) {
		$http.get(REST.GENRES + '/user/page_count' + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_GENRE_ERROR};
			callback(response);
		});
	}

	function checkGenreName(name, callback) {
		$http.post(REST.GENRES + '/check_genre_name/' + name + REST.JSON_EXT)
		.success(function(response) {
			if (response) {
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
	}

	return {
		createGenre: createGenre,
		deleteGenre: deleteGenre,
		getGenreById: getGenreById,
		getGenres: getGenres,
		getEntityGenres: getEntityGenres,
		getUserGenres: getUserGenres,
		getAllGenresIdAndName: getAllGenresIdAndName,
		getPageCount: getPageCount,
		getEntityPageCount: getEntityPageCount,
		getUserPageCount: getUserPageCount,
		checkGenreName: checkGenreName
	};

}]);