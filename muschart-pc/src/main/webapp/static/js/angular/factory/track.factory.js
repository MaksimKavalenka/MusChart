'use strict';
app.factory('TrackFactory', function($http, MESSAGE, REST, UtilityService) {

	function createTrack(name, song, cover, video, release, artistsId, unitsId, genresId, callback) {
		if (!UtilityService.allNotEmpty(callback, name, song, cover, video, release, artistsId, unitsId, genresId)) {
			return;
		}

		var track = {
			name: name,
			song: song,
			cover: cover,
			video: video,
			release: release,
			artistsId: artistsId,
			unitsId: unitsId,
			genresId: genresId
		};

		$http.post(REST.TRACKS, track)
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
		if (!UtilityService.allNotEmpty(callback, id)) {
			return;
		}

		$http.delete(REST.TRACKS + '/' + id)
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
		if (!UtilityService.allNotEmpty(callback, id)) {
			return;
		}

		$http.get(REST.TRACKS + '/' + id)
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
		if (!UtilityService.allNotEmpty(callback, sort, order, page)) {
			return;
		}

		$http.get(REST.TRACKS + '/' + sort + '/' + order + '/' + page)
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
		if (!UtilityService.allNotEmpty(callback, entity, entityId, sort, order, page)) {
			return;
		}

		$http.get(REST.TRACKS + '/' + entity + '/' + entityId + '/' + sort + '/' + order + '/' + page)
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
		if (!UtilityService.allNotEmpty(callback, sort, order, page)) {
			return;
		}

		$http.get(REST.TRACKS + '/user/' + sort + '/' + order + '/' + page)
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
		$http.get(REST.TRACKS + '/all/id_name')
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
		$http.get(REST.TRACKS + '/pages_count')
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
		if (!UtilityService.allNotEmpty(callback, entity, entityId)) {
			return;
		}

		$http.get(REST.TRACKS + '/' + entity + '/' + entityId + '/pages_count')
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
		$http.get(REST.TRACKS + '/user/pages_count')
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

});