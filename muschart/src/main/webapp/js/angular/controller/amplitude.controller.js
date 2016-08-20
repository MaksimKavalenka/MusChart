'use strict';
app.controller('AmplitudeController', ['$scope', '$state', 'STATE', function($scope, $state, STATE) {
	$scope.show = function() {
		return $.inArray($state.current.name, [STATE.ARTISTS, STATE.GENRES, STATE.TRACKS, STATE.ARTIST, STATE.GENRE, STATE.TRACK, STATE.GENRE_ARTISTS, STATE.TRACK_ARTISTS, STATE.USER_ARTISTS, STATE.ARTIST_GENRES, STATE.TRACK_GENRES, STATE.USER_GENRES, STATE.ARTIST_TRACKS, STATE.GENRE_TRACKS, STATE.USER_TRACKS]) !== -1;
	}
}]);