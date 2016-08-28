'use strict';
app.run(['$cookies', '$location', '$rootScope', '$state', 'STATE', 'CookieService', 'FlashService', function($cookies, $location, $rootScope, $state, STATE, CookieService, FlashService) {
	$rootScope.saveSettings = function() {
		CookieService.setSettings();
	};
	$rootScope.showAmplitude = function() {
		return $.inArray($state.current.name, [STATE.PLAYLIST, STATE.ARTISTS, STATE.GENRES, STATE.TRACKS, STATE.ARTIST, STATE.GENRE, STATE.TRACK, STATE.GENRE_ARTISTS, STATE.TRACK_ARTISTS, STATE.USER_ARTISTS, STATE.ARTIST_GENRES, STATE.TRACK_GENRES, STATE.USER_GENRES, STATE.ARTIST_TRACKS, STATE.GENRE_TRACKS, STATE.USER_TRACKS]) !== -1;
	}

	$rootScope.settings = $cookies.getObject('settings');
	if ($rootScope.settings == null) {
		$rootScope.settings = {};
		$rootScope.settings.sort = {tracks: 0, artists: 0, genres: 0, my_tracks: 0, my_artists: 0, my_genres: 0};
		$rootScope.settings.order = {tracks: 'false', artists: 'false', genres: 'false', my_tracks: 'false', my_artists: 'false', my_genres: 'false'};
		$rootScope.saveSettings();
	}

	$rootScope.tracks = $cookies.getObject('tracks');

	$rootScope.user = $cookies.getObject('user');

	$rootScope.$on('$stateChangeStart', function() {
		FlashService.clearFlashMessage(0);
	});
	/*$rootScope.$on('$locationChangeStart', function(event, next, current) {
		var restrictedPage = $.inArray($location.path(), ['/login', '/register']) === -1;
		var loggedIn = $rootScope.globals.user;
		if (restrictedPage && !loggedIn) {
			$location.path('/login');
		}
	});*/
}]);