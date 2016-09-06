'use strict';
app.service('ChoiceService', ['$rootScope', function($rootScope) {

	$rootScope.artistsChoice = [{index: 0}];
	$rootScope.genresChoice = [{index: 0}];
	$rootScope.unitsChoice = [];

	$rootScope.addArtistChoice = function() {
		$rootScope.artistsChoice.push({index: $rootScope.artistsChoice.length});
		if ($rootScope.artistsChoice.length > 1) {
			$rootScope.unitsChoice.push({index: $rootScope.unitsChoice.length});
		}
	};

	$rootScope.addGenreChoice = function() {
		$rootScope.genresChoice.push({index: $rootScope.genresChoice.length});
	};

	$rootScope.removeArtistChoice = function(index) {
		if ($rootScope.artistsChoice.length > 1) {
			$rootScope.unitsChoice.splice(index - 1, 1);
		}
		$rootScope.artistsChoice.splice(index, 1);
		for (var i = index; i < $rootScope.artistsChoice.length; i++) {
			if ($rootScope.artistsChoice.length > 1) {
				$rootScope.unitsChoice[i].index = $rootScope.unitsChoice[i].index - 1;
			}
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
		$rootScope.unitsChoice = [];
	}

	return {
		reset: reset
	};

}]);