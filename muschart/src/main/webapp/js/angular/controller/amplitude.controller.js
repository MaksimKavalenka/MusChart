'use strict';
app.controller('AmplitudeController', ['$scope', '$state', 'STATE', function($scope, $state, STATE) {
	$scope.show = function() {
		return $.inArray($state.current.name, [STATE.ARTISTS, STATE.GENRES, STATE.TRACKS, STATE.TRACK_ARTISTS, STATE.GENRE_ARTISTS, STATE.TRACK_GENRES, STATE.ARTIST_GENRES, STATE.ARTIST_TRACKS, STATE.GENRE_TRACKS]) !== -1;
	}
}]);