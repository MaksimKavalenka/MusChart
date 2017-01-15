'use strict';
app.run(function($rootScope, $state, $translate, DEFAULT, STATE, UserFactory, CookieService, FlashService) {

	$rootScope.$state = $state;
	$rootScope.count = DEFAULT.COUNT;

	$rootScope.$on('$stateChangeStart', function() {
		FlashService.clearFlashMessage(0);
	});

	var settings = CookieService.getSettings();
	if (settings === undefined) {
		CookieService.setSettings(DEFAULT.SETTINGS);
	} else {
		$translate.use(settings.language);
	}

	var user = CookieService.getUser();
	if (user === undefined) {
		UserFactory.getUser(function(response) {
			if (response.success) {
				if (response.data !== null) {
					$rootScope.user = response.data;
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