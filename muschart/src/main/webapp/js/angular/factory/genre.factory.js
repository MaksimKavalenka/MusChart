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

	function getGenresByCriteria(sort, order, page, callback) {
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

	function getGenresByCriteriaExt(relation, id, sort, order, page, callback) {
		$http.get(REST.GENRES + '/' + relation + '/' + id + '/' + sort + '/' + order + '/' + page + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_GENRE_ERROR};
			callback(response);
		});
	}

	function getAllGenres(callback) {
		$http.get(REST.GENRES + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_GENRE_ERROR};
			callback(response);
		});
	}

	function checkName(name, callback) {
		$http.post(REST.GENRES + '/check_name/' + name + REST.JSON_EXT)
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

	function getPageAmount(callback) {
		$http.get(REST.GENRES + '/page_amount' + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
			callback(response);
		});
	}

	function getPageAmountExt(id, relation, callback) {
		$http.get(REST.GENRES + '/' + id + '/' + relation + '/page_amount' + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
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
		getGenresByCriteria: getGenresByCriteria,
		getGenresByCriteriaExt: getGenresByCriteriaExt,
		getAllGenres: getAllGenres,
		getPageAmount: getPageAmount,
		getPageAmountExt: getPageAmountExt,
		checkName: checkName
	};

}]);