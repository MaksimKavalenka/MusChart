'use strict';
app.factory('TrackService', ['$http', '$q', function($http, $q) {
	var url = "http://localhost:8080/muschart";
	return {

		addTrack: function(track) {
			return $http.post(url + '/track/', track).then(
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
			return $http.get(url + '/track/' + idFrom + '/' + idTo).then(
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
			return $http.get(url + '/track/').then(
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
			return $http.delete(url + '/track/' + id).then(
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