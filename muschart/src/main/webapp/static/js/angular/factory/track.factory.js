'use strict';
app.factory('TrackFactory', ['$http', 'MESSAGE', 'REST', 'ValidatorService', function($http, MESSAGE, REST, ValidatorService) {

	function createTrack(name, song, cover, video, release, artists, units, genres, callback) {
		if (!ValidatorService.allNotEmpty(callback, name, song, cover, video, release, artists, units, genres)) {
			return;
		}
		$http.post(REST.TRACKS + '/create/' + name + '/' + song + '/' + cover + '/' + video + '/' + release + '/' + artists + '/' + units + '/' + genres + REST.JSON_EXT)
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
		if (!ValidatorService.allNotEmpty(callback, id)) {
			return;
		}
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
		if (!ValidatorService.allNotEmpty(callback, id)) {
			return;
		}
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

	function getTracks(sort, order, page, callback) {
		if (!ValidatorService.allNotEmpty(callback, sort, order, page)) {
			return;
		}
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

	function getEntityTracks(entity, entityId, sort, order, page, callback) {
		if (!ValidatorService.allNotEmpty(callback, entity, entityId, sort, order, page)) {
			return;
		}
		$http.get(REST.TRACKS + '/' + entity + '/' + entityId + '/' + sort + '/' + order + '/' + page + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TRACK_ERROR};
			callback(response);
		});
	}

	function getUserTracks(sort, order, page, callback) {
		if (!ValidatorService.allNotEmpty(callback, sort, order, page)) {
			return;
		}
		$http.get(REST.TRACKS + '/user/' + sort + '/' + order + '/' + page + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TRACK_ERROR};
			callback(response);
		});
	}

	function getAllTracksIdAndName(callback) {
		$http.get(REST.TRACKS + '/all/id_name' + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TRACK_ERROR};
			callback(response);
		});
	}

	function getPagesCount(callback) {
		$http.get(REST.TRACKS + '/pages_count' + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TRACK_ERROR};
			callback(response);
		});
	}

	function getEntityPagesCount(entity, entityId, callback) {
		if (!ValidatorService.allNotEmpty(callback, entity, entityId)) {
			return;
		}
		$http.get(REST.TRACKS + '/' + entity + '/' + entityId + '/pages_count' + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TRACK_ERROR};
			callback(response);
		});
	}

	function getUserPagesCount(callback) {
		$http.get(REST.TRACKS + '/user/pages_count' + REST.JSON_EXT)
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
		getTracks: getTracks,
		getEntityTracks: getEntityTracks,
		getUserTracks: getUserTracks,
		getAllTracksIdAndName: getAllTracksIdAndName,
		getPagesCount: getPagesCount,
		getEntityPagesCount: getEntityPagesCount,
		getUserPagesCount: getUserPagesCount
	};

}]);