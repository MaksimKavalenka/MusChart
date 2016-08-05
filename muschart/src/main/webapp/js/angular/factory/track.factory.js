'use strict';
app.factory('TrackFactory', ['$http', '$q', 'DEFAULT', function($http, $q, DEFAULT, UPLOAD_URL) {
	var trackUrl = DEFAULT.URL + '/track/';
	return {

		createTrack: function(name, song, cover, date, callback) {
			$http.post(trackUrl + 'create/' + name + '/' + song + '/' + cover + '/' + date)
			.success(function(response) {
				response = {success: true, message: 'Track has been added successfully'};
				callback(response);
			})
			.error(function(response) {
				response = {success: false, message: 'Error while adding track'};
				callback(response);
			});
		},

		getTracksByIds: function(idFrom, idTo) {
			return $http.get(trackUrl + idFrom + '/' + idTo).then(
				function(response) {
					return response.data;
				},
				function(errResponse) {
					console.error('Error while getting tracks');
					return $q.reject(errResponse);
				}
			);
		},

		getAmplitudeTracksByIds: function(idFrom, idTo) {
			return $http.get(trackUrl + "amplitude/" + idFrom + '/' + idTo).then(
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
			return $http.delete(trackUrl + id).then(
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