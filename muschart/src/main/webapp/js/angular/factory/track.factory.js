'use strict';
app.factory('TrackFactory', ['$http', 'DEFAULT', 'URL', function($http, DEFAULT, URL) {
	return {

		createTrack: function(songName, song, cover, artists, genres, date, callback) {
			$http.post(URL.TRACKS + '/create/' + songName + '/' + song + '/' + cover + '/' + artists + '/' + genres + '/' + date + DEFAULT.JSON_EXT)
			.success(function(response) {
				response = {success: true, message: 'Track has been added successfully'};
				callback(response);
			})
			.error(function(response) {
				response = {success: false, message: 'Error while adding track'};
				callback(response);
			});
		},

		getTracksByIdsAsc: function(idFrom, idTo, callback) {
			return $http.get(URL.TRACKS + '/id/asc/' + idFrom + '/' + idTo + DEFAULT.JSON_EXT)
			.success(function(response) {
				var data = {success: true, data: response};
				callback(data);
			})
			.error(function(response) {
				response = {success: false, message: 'Error while getting tracks'};
				callback(response);
			});
		},

		getTracksByIdsDesc: function(idFrom, idTo, callback) {
			return $http.get(URL.TRACKS + '/id/desc/' + idFrom + '/' + idTo + DEFAULT.JSON_EXT)
			.success(function(response) {
				var data = {success: true, data: response};
				callback(data);
			})
			.error(function(response) {
				response = {success: false, message: 'Error while getting tracks'};
				callback(response);
			});
		},

		getAmplitudeTracksByIdsAsc: function(idFrom, idTo, callback) {
			return $http.get(URL.TRACKS + "/id/asc/amplitude/" + idFrom + '/' + idTo + DEFAULT.JSON_EXT)
			.success(function(response) {
				var data = {success: true, data: response};
				callback(data);
			})
			.error(function(response) {
				response = {success: false, message: 'Error while getting tracks'};
				callback(response);
			});
		},

		getAmplitudeTracksByIdsDesc: function(idFrom, idTo, callback) {
			return $http.get(URL.TRACKS + "/id/desc/amplitude/" + idFrom + '/' + idTo + DEFAULT.JSON_EXT)
			.success(function(response) {
				var data = {success: true, data: response};
				callback(data);
			})
			.error(function(response) {
				response = {success: false, message: 'Error while getting tracks'};
				callback(response);
			});
		},

		deleteTrack: function(id, callback) {
			return $http.delete(URL.TRACKS + '/delete/' + id + DEFAULT.JSON_EXT)
			.success(function(response) {
				response = {success: true, message: 'Track has been deleted successfully'};
				callback(data);
			})
			.error(function(response) {
				response = {success: false, message: 'Error while deleting track'};
				callback(response);
			});
		}

	};
}]);