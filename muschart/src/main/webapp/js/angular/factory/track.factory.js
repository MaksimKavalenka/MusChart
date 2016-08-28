'use strict';
app.factory('TrackFactory', ['$http', 'MESSAGE', 'REST', 'UPLOAD', function($http, MESSAGE, REST, UPLOAD) {

	function createTrack(name, song, cover, video, release, artists, genres, callback) {
		$http.post(REST.TRACKS + '/create/' + name + '/' + song + '/' + cover + '/' + video + '/' + release + '/' + artists + '/' + genres + REST.JSON_EXT)
		.success(function(response) {
			response = {success: true, message: MESSAGE.ADDING_TRACK_SUCCESS};
			callback(response);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.ADDING_TRACK_ERROR};
			callback(response);
		});
	}

	function deleteTrack(id, callback) {
		return $http.delete(REST.TRACKS + '/delete/' + id + REST.JSON_EXT)
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
		return $http.get(REST.TRACKS + '/' + id + REST.JSON_EXT)
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
		return $http.get(REST.TRACKS + '/' + sort + '/' + order + '/' + page + REST.JSON_EXT)
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
		return $http.get(REST.TRACKS + '/' + relation + '/' + id + '/' + sort + '/' + order + '/' + page + REST.JSON_EXT)
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
		return $http.get(REST.TRACKS + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_TRACK_ERROR};
			callback(response);
		});
	}

	function parseToAmplitudeSong(tracks) {
		var songs = [];
		for (var i = 0; i < tracks.length; i++) {
			var artist = tracks[i].artists[0].name;
			for (var j = 0; j < tracks[i].units.length; j++) {
				artist += tracks[i].units[j].name + tracks[i].artists[j + 1].name;
			}
			songs.push({
				id: tracks[i].id,
				album: '',
				artist: artist,
				cover_art_url: UPLOAD.TRACK_COVER + '/' + tracks[i].cover,
				name: '<a ui-sref="track({id: {{tracks[i].id}}})">' + tracks[i].name + '</a>',
				url: UPLOAD.AUDIO + '/' + tracks[i].song
			});
		};
		return {songs: songs};
	}

	return {
		createTrack: createTrack,
		deleteTrack: deleteTrack,
		getTrackById: getTrackById,
		getTracksByCriteria: getTracksByCriteria,
		getTracksByCriteriaExt: getTracksByCriteriaExt,
		getAllTracks: getAllTracks,
		parseToAmplitudeSong: parseToAmplitudeSong
	};
}]);