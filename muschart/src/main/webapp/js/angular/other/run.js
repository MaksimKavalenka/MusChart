'use strict';
app.run(['$cookies', '$location', '$rootScope', '$state', 'STATE', 'URL', 'CookieService', 'FlashService', function($cookies, $location, $rootScope, $state, STATE, URL, CookieService, FlashService) {

	$rootScope.$state = $state;

	$rootScope.amount = {artists: 6, genres: 18, tracks: 6};
	$rootScope.saveSettings = function() {
		CookieService.setSettings();
	};
	$rootScope.isPermitted = function() {
		var restrictedPage = (/^\/muschart\/artist\/add$/.test($location.path())) || (/^\/muschart\/genre\/add$/.test($location.path())) || (/^\/muschart\/track\/add$/.test($location.path()))
			|| (/^\/muschart\/user\/artists\/page\/[0-9]{1,}$/.test($location.path())) || (/^\/muschart\/user\/genres\/page\/[0-9]{1,}$/.test($location.path()))
			|| (/^\/muschart\/user\/tracks\/page\/[0-9]{1,}$/.test($location.path()));
		var loggedIn = $rootScope.user;
		return (restrictedPage && !loggedIn);
	};

	$rootScope.settings = $cookies.getObject('settings');
	if ($rootScope.settings == null) {
		$rootScope.settings = {};
		$rootScope.settings.sort = {tracks: 0, artists: 0, genres: 0, my_tracks: 0, my_artists: 0, my_genres: 0};
		$rootScope.settings.order = {tracks: 'false', artists: 'false', genres: 'false', my_tracks: 'false', my_artists: 'false', my_genres: 'false'};
		$rootScope.saveSettings();
	}

	$rootScope.tracks = $cookies.getObject('tracks');

	$rootScope.user = $cookies.getObject('user');
	$rootScope.$on('$locationChangeStart', function() {
		if ($rootScope.isPermitted()) {
			$state.go(STATE.LOGIN);
		}
	});
	$rootScope.$on('$stateChangeStart', function() {
		if ($rootScope.isPermitted()) {
			$location.path(URL.LOGIN);
		}
	});
	$rootScope.$on('$stateChangeStart', function() {
		FlashService.clearFlashMessage(0);
	});

}]);