'use strict';
app.factory('TrackFactory', ['$http', '$q', 'DEFAULT', function($http, $q, DEFAULT) {
	var track_url = DEFAULT.URL + '/track/';
	return {

		addTrack: function(track) {
			return $http.post(track_url, track).then(
				function(response) {
					return response.data;
				},
				function(errResponse) {
					console.error('Error while adding track');
					return $q.reject(errResponse);
				}
			);
		},

		getTracksByIds: function(idFrom, idTo) {
			return $http.get(track_url + idFrom + '/' + idTo).then(
				function(response) {
					return response.data;
				},
				function(errResponse) {
					console.error('Error while getting tracks');
					return $q.reject(errResponse);
				}
			);
		},

		getAllTracks: function() {
			return $http.get(track_url).then(
				function(response) {
					return response.data;
				},
				function(errResponse) {
					console.error('Error while getting tracks');
					return $q.reject(errResponse);
				}
			);
		},

		deleteTrack: function(id) {
			return $http.delete(track_url + id).then(
				function(response) {
					return response.data;
				},
				function(errResponse) {
					console.error('Error while deleting track');
					return $q.reject(errResponse);
				}
			);
		}

	};
}]);