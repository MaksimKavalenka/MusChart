'use strict';
app.factory('ArtistFactory', ['$http', '$q', 'DEFAULT', function($http, $q, DEFAULT) {
	var artistUrl = DEFAULT.URL + '/artist/';
	return {

		createArtist: function(name, photo, callback) {
			$http.post(artistUrl + 'create/' + name + '/' + photo)
			.success(function(response) {
				response = {success: true, message: 'Artist has been added successfully'};
				callback(response);
			})
			.error(function(response) {
				response = {success: false, message: 'Error while adding artist'};
				callback(response);
			});
		},

		getArtistsByIds: function(idFrom, idTo) {
			return $http.get(artistUrl + idFrom + '/' + idTo).then(
				function(response) {
					return response.data;
				},
				function(errResponse) {
					console.error('Error while getting artists');
					return $q.reject(errResponse);
				}
			);
		},

		getAllArtists: function() {
			return $http.get(artistUrl).then(
				function(response) {
					return response.data;
				},
				function(errResponse) {
					console.error('Error while getting artists');
					return $q.reject(errResponse);
				}
			);
		},

		deleteArtist: function(id) {
			return $http.delete(artistUrl + id).then(
				function(response) {
					return response.data;
				},
				function(errResponse) {
					console.error('Error while deleting artist');
					return $q.reject(errResponse);
				}
			);
		}

	};
}]);