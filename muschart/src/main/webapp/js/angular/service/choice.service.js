'use strict';
app.service('ChoiceService', ['$http', '$rootScope', 'DEFAULT', 'MESSAGE', 'URL', function($http, $rootScope, DEFAULT, MESSAGE, URL) {
	$rootScope.artistsChoice = [{index: 0}];
	$rootScope.genresChoice = [{index: 0}];

	$rootScope.addArtistChoice = function() {
		$rootScope.artistsChoice.push({index: $rootScope.artistsChoice.length});
	};

	$rootScope.addGenreChoice = function() {
		$rootScope.genresChoice.push({index: $rootScope.genresChoice.length});
	};

	$rootScope.removeArtistChoice = function(index) {
		$rootScope.artistsChoice.splice(index, 1);
		for (var i = index; i < $rootScope.artistsChoice.length; i++) {
			$rootScope.artistsChoice[i].index = $rootScope.artistsChoice[i].index - 1;
		}
	};

	$rootScope.removeGenreChoice = function(index) {
		$rootScope.genresChoice.splice(index, 1);
		for (var i = index; i < $rootScope.genresChoice.length; i++) {
			$rootScope.genresChoice[i].index = $rootScope.genresChoice[i].index - 1;
		}
	};

	return {

		getAllUnits: function(callback) {
			$rootScope.units = [];
			return $http.get(URL.UNITS + DEFAULT.JSON_EXT)
			.success(function(response) {
				$rootScope.units = response;
				response = {success: true};
				callback(response);
			})
			.error(function(response) {
				response = {success: false, message: MESSAGE.GETTING_UNIT_ERROR};
				callback(response);
			});
		},

		getAllArtists: function(callback) {
			$rootScope.artists = [];
			return $http.get(URL.ARTISTS + DEFAULT.JSON_EXT)
			.success(function(response) {
				$rootScope.artists = response;
				response = {success: true};
				callback(response);
			})
			.error(function(response) {
				response = {success: false, message: MESSAGE.GETTING_ARTIST_ERROR};
				callback(response);
			});
		},

		getAllGenres: function(callback) {
			$rootScope.genres = [];
			return $http.get(URL.GENRES + DEFAULT.JSON_EXT)
			.success(function(response) {
				$rootScope.genres = response;
				response = {success: true};
				callback(response);
			})
			.error(function(response) {
				response = {success: false, message: MESSAGE.GETTING_GENRE_ERROR};
				callback(response);
			});
		},

		reset: function() {
			$rootScope.artistsChoice = [{index: 0}];
			$rootScope.genresChoice = [{index: 0}];
		}

	};
}]);