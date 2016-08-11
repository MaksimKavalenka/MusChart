'use strict';
app.factory('TrackFactory', ['$http', 'DEFAULT', 'MESSAGE', 'URL', function($http, DEFAULT, MESSAGE, URL) {
	return {

		createTrack: function(songName, song, cover, artists, genres, release, callback) {
			$http.post(URL.TRACKS + '/create/' + songName + '/' + song + '/' + cover + '/' + artists + '/' + genres + '/' + release + DEFAULT.JSON_EXT)
			.success(function(response) {
				response = {success: true, message: MESSAGE.ADDING_TRACK_SUCCESS};
				callback(response);
			})
			.error(function(response) {
				response = {success: false, message: MESSAGE.ADDING_TRACK_ERROR};
				callback(response);
			});
		},

		getTracksByCriteria: function(sort, order, page, callback) {
			return $http.get(URL.TRACKS + '/' + sort + '/' + order + '/' + page + DEFAULT.JSON_EXT)
			.success(function(response) {
				var data = {success: true, data: response};
				callback(data);
			})
			.error(function(response) {
				response = {success: false, message: MESSAGE.GETTING_TRACK_ERROR};
				callback(response);
			});
		},

		getAmplitudeTracksByCriteria: function(sort, order, page, callback) {
			return $http.get(URL.TRACKS + '/amplitude/' + sort + '/' + order + '/' + page + DEFAULT.JSON_EXT)
			.success(function(response) {
				var data = {success: true, data: response};
				callback(data);
			})
			.error(function(response) {
				response = {success: false, message: MESSAGE.GETTING_TRACK_ERROR};
				callback(response);
			});
		},

		deleteTrack: function(id, callback) {
			return $http.delete(URL.TRACKS + '/delete/' + id + DEFAULT.JSON_EXT)
			.success(function(response) {
				response = {success: true, message: MESSAGE.GELETING_TRACK_SUCCESS};
				callback(data);
			})
			.error(function(response) {
				response = {success: false, message: MESSAGE.GELETING_TRACK_ERROR};
				callback(response);
			});
		}

	};
}]);