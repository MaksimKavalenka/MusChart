'use strict';
app.factory('TrackFactory', ['$http', 'MESSAGE', 'REST', function($http, MESSAGE, REST) {
	return {

		createTrack: function(name, song, cover, video, artists, genres, release, callback) {
			$http.post(REST.TRACKS + '/create/' + name + '/' + song + '/' + cover + '/' + video + '/' + artists + '/' + genres + '/' + release + REST.JSON_EXT)
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
			return $http.get(REST.TRACKS + '/' + sort + '/' + order + '/' + page + REST.JSON_EXT)
			.success(function(response) {
				var data = {success: true, data: response};
				callback(data);
			})
			.error(function(response) {
				response = {success: false, message: MESSAGE.GETTING_TRACK_ERROR};
				callback(response);
			});
		},

		getTracksByCriteriaExt: function(relation, id, sort, order, page, callback) {
			return $http.get(REST.TRACKS + '/' + relation + '/' + id + '/' + sort + '/' + order + '/' + page + REST.JSON_EXT)
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
			return $http.get(REST.TRACKS + '/amplitude/' + sort + '/' + order + '/' + page + REST.JSON_EXT)
			.success(function(response) {
				var data = {success: true, data: response};
				callback(data);
			})
			.error(function(response) {
				response = {success: false, message: MESSAGE.GETTING_TRACK_ERROR};
				callback(response);
			});
		},

		getAmplitudeTracksByCriteriaExt: function(relation, id, sort, order, page, callback) {
			return $http.get(REST.TRACKS + '/amplitude/' + relation + '/' + id + '/' + sort + '/' + order + '/' + page + REST.JSON_EXT)
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

	};
}]);