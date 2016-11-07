'use strict';
app.run(function($cookies, $rootScope, $state, $translate, STATE, UserFactory, FlashService) {

	$rootScope.$state = $state;
	$rootScope.count = {artists: 6, genres: 18, tracks: 6};

	var settings = $cookies.getObject('settings');
	if (settings == null) {
		settings = {};
		settings.language = 'en';
		settings.sort = {artists: '0', genres: '0', tracks: '1'};
		settings.order = {artists: false, genres: true, tracks: false};
		$cookies.putObject('settings', settings);
	} else {
		$translate.use(settings.language);
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

});