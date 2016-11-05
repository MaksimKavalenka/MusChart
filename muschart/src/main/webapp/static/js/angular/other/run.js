'use strict';
app.run(['$cookies', '$rootScope', '$state', 'STATE', 'UserFactory', 'FlashService', function($cookies, $rootScope, $state, STATE, UserFactory, FlashService) {

	$rootScope.$state = $state;

	$rootScope.settings = $cookies.getObject('settings');
	if ($rootScope.settings == null) {
		$rootScope.settings = {};
		$rootScope.settings.language = 'en';
		$rootScope.settings.sort = {tracks: 0, artists: 0, genres: 0, my_tracks: 0, my_artists: 0, my_genres: 0};
		$rootScope.settings.order = {tracks: 'false', artists: 'false', genres: 'false', my_tracks: 'false', my_artists: 'false', my_genres: 'false'};
		$rootScope.saveSettings();
	}

	$rootScope.tracks = $cookies.getObject('tracks');

	$rootScope.$on('$stateChangeStart', function() {
		FlashService.clearFlashMessage(0);
	});

	UserFactory.getUser(function(response) {
		if (response.success) {
			if (response.data !== null) {
				$rootScope.user = {
					name: response.data.login,
					admin: response.data.admin
				};
			} else {
				$state.go(STATE.LOGIN);
			}
		}
	});

}]);