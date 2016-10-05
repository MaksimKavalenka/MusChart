'use strict';
app.factory('TrackFactory', ['$http', 'MESSAGE', 'REST', function($http, MESSAGE, REST) {

	function createTrack(name, song, cover, video, release, units, artists, genres, callback) {
		$http.post(REST.TRACKS + '/create/' + name + '/' + song + '/' + cover + '/' + video + '/' + release + '/' + units + '/' + artists + '/' + genres + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response, message: MESSAGE.CREATING_TRACK_SUCCESS};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.CREATING_TRACK_ERROR};
			callback(response);
		});
	}

	function deleteTrack(id, callback) {
		$http.delete(REST.TRACKS + '/delete/' + id + REST.JSON_EXT)
		.success(function(response) {
			response = {success: true, message: MESSAGE.GELETING_TRACK_SUCCESS};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GELETING_TRACK_ERROR};
			callback(response);
		});
	}

	function getTrackById(id, callback) {
		$http.get(REST.TRACKS + '/' + id + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TRACK_ERROR};
			callback(response);
		});
	}

	function getTracksByCriteria(sort, order, page, callback) {
		$http.get(REST.TRACKS + '/' + sort + '/' + order + '/' + page + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TRACK_ERROR};
			callback(response);
		});
	}

	function getTracksByCriteriaExt(relation, id, sort, order, page, callback) {
		$http.get(REST.TRACKS + '/' + relation + '/' + id + '/' + sort + '/' + order + '/' + page + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TRACK_ERROR};
			callback(response);
		});
	}

	function getAllTracks(callback) {
		$http.get(REST.TRACKS + '/all' + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TRACK_ERROR};
			callback(response);
		});
	}

	function getPageCount(callback) {
		$http.get(REST.TRACKS + '/page_count' + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TRACK_ERROR};
			callback(response);
		});
	}

	function getPageCountExt(relation, id, callback) {
		$http.get(REST.TRACKS + '/' + relation + '/' + id + '/page_count' + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TRACK_ERROR};
			callback(response);
		});
	}

	return {
		createTrack: createTrack,
		deleteTrack: deleteTrack,
		getTrackById: getTrackById,
		getTracksByCriteria: getTracksByCriteria,
		getTracksByCriteriaExt: getTracksByCriteriaExt,
		getAllTracks: getAllTracks,
		getPageCount: getPageCount,
		getPageCountExt: getPageCountExt
	};

}]);