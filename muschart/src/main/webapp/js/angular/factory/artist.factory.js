'use strict';
app.factory('ArtistFactory', ['$http', 'DEFAULT', 'URL', function($http, DEFAULT, URL) {
	return {

		createArtist: function(name, photo, callback) {
			$http.post(URL.ARTISTS + '/create/' + name + '/' + photo + DEFAULT.JSON_EXT)
			.success(function(response) {
				response = {success: true, message: 'Artist has been added successfully'};
				callback(response);
			})
			.error(function(response) {
				response = {success: false, message: 'Error while adding artist'};
				callback(response);
			});
		},

		getArtistsByIds: function(idFrom, idTo, callback) {
			return $http.get(URL.ARTISTS + '/' + idFrom + '/' + idTo + DEFAULT.JSON_EXT)
			.success(function(response) {
				var data = {success: true, data: response};
				callback(data);
			})
			.error(function(response) {
				response = {success: false, message: 'Error while getting artists'};
				callback(response);
			});
		},

		deleteArtist: function(id, callback) {
			return $http.delete(URL.ARTISTS + '/delete/' + id + DEFAULT.JSON_EXT)
			.success(function(response) {
				response = {success: true, message: 'Artist has been deleted successfully'};
				callback(data);
			})
			.error(function(response) {
				response = {success: false, message: 'Error while deleting artist'};
				callback(response);
			});
		}

	};
}]);