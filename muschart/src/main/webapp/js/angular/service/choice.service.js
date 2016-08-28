'use strict';
app.service('ChoiceService', ['$rootScope', function($rootScope) {
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

	function reset() {
		$rootScope.artistsChoice = [{index: 0}];
		$rootScope.genresChoice = [{index: 0}];
	}

	return {
		reset: reset
	};
}]);