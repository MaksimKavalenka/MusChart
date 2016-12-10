'use strict';
app.run(function($rootScope, $state, $translate, STATE, UserFactory, CookieService, FlashService) {

	$rootScope.$state = $state;
	$rootScope.count = {artists: 6, genres: 18, tracks: 6};

	$rootScope.$on('$stateChangeStart', function() {
		FlashService.clearFlashMessage(0);
	});

	var settings = CookieService.getSettings();
	if (settings === undefined) {
		settings = {};
		settings.language = 'en';
		settings.design = '1';
		settings.sort = {artists: '1', genres: '0', tracks: '1'};
		settings.order = {artists: false, genres: true, tracks: false};
		CookieService.setSettings(settings);
	} else {
		$translate.use(settings.language);
	}

	var user = CookieService.getUser();
	if (user === undefined) {
		UserFactory.getUser(function(response) {
			if (response.success) {
				if (response.data !== null) {
					$rootScope.user = {
						name: response.data.login,
						admin: response.data.admin
					};
					CookieService.setUser($rootScope.user);
				} else {
					$state.go(STATE.LOGIN);
				}
			}
		});
	} else {
		$rootScope.user = user;
	}

});