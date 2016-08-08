'use strict';
app.config(['$locationProvider', function($locationProvider) {
	$locationProvider.html5Mode(true);
}]);
app.config(['$stateProvider', '$urlRouterProvider', 'CONTROLLER', 'DEFAULT', 'PATH', 'STATE', 'URL', function($stateProvider, $urlRouterProvider, CONTROLLER, DEFAULT, PATH, STATE, URL) {
	var mainHeader = {
		controller: CONTROLLER.USER_EDIT_CONTROLLER,
		controllerAs: DEFAULT.CONTROLLER_NAME,
		templateUrl: PATH.MAIN_HEADER
	}
	var authenticationHeader = {
		templateUrl: PATH.AUTHENTICATION_HEADER
	}
	var editHeader = {
		templateUrl: PATH.EDIT_HEADER
	}
	var footer = {
			templateUrl: PATH.FOOTER
		}
	$stateProvider
	.state(STATE.LOGIN, {
		url: URL.LOGIN,
		views: {
			header: authenticationHeader,
			content: {
				controller: CONTROLLER.USER_EDIT_CONTROLLER,
				controllerAs: DEFAULT.CONTROLLER_NAME,
				templateUrl: PATH.LOGIN_FORM
			}
		}
	})
	.state(STATE.REGISTER, {
		url: URL.REGISTER,
		views: {
			header: authenticationHeader,
			content: {
				controller: CONTROLLER.USER_EDIT_CONTROLLER,
				controllerAs: DEFAULT.CONTROLLER_NAME,
				templateUrl: PATH.REGISTER_FORM
			}
		}
	})
	.state(STATE.ARTISTS, {
		url: URL.ARTISTS + URL.PATTERN_PAGE,
		views: {
			header: mainHeader,
			tool: {
				controller: CONTROLLER.ARTIST_CONTROLLER,
				controllerAs: DEFAULT.CONTROLLER_NAME,
				templateUrl: PATH.PAGINATION_TOOL
			},
			content: {
				controller: CONTROLLER.ARTIST_CONTROLLER,
				controllerAs: DEFAULT.CONTROLLER_NAME,
				templateUrl: PATH.ARTIST_CONTENT
			},
			footer: footer
		}
	})
	.state(STATE.GENRES, {
		url: URL.GENRES + URL.PATTERN_PAGE,
		views: {
			header: mainHeader,
			tool: {
				controller: CONTROLLER.GENRE_CONTROLLER,
				controllerAs: DEFAULT.CONTROLLER_NAME,
				templateUrl: PATH.PAGINATION_TOOL
			},
			content: {
				controller: CONTROLLER.GENRE_CONTROLLER,
				controllerAs: DEFAULT.CONTROLLER_NAME,
				templateUrl: PATH.GENRE_CONTENT
			},
			footer: footer
		}
	})
	.state(STATE.TRACKS, {
		url: URL.TRACKS + URL.PATTERN_PAGE,
		views: {
			header: mainHeader,
			tool: {
				controller: CONTROLLER.TRACK_CONTROLLER,
				controllerAs: DEFAULT.CONTROLLER_NAME,
				templateUrl: PATH.PAGINATION_TOOL
			},
			content: {
				controller: CONTROLLER.TRACK_CONTROLLER,
				controllerAs: DEFAULT.CONTROLLER_NAME,
				templateUrl: PATH.TRACK_CONTENT
			},
			footer: footer
		}
	})
	.state(STATE.ARTISTS_ADD, {
		url: URL.ARTISTS_ADD,
		views: {
			header: editHeader,
			content: {
				controller: CONTROLLER.ARTIST_EDIT_CONTROLLER,
				controllerAs: DEFAULT.CONTROLLER_NAME,
				templateUrl: PATH.ARTIST_EDIT_FORM
			}
		}
	})
	.state(STATE.GENRES_ADD, {
		url: URL.GENRES_ADD,
		views: {
			header: editHeader,
			content: {
				controller: CONTROLLER.GENRE_EDIT_CONTROLLER,
				controllerAs: DEFAULT.CONTROLLER_NAME,
				templateUrl: PATH.GENRE_EDIT_FORM
			}
		}
	})
	.state(STATE.TRACKS_ADD, {
		url: URL.TRACKS_ADD,
		views: {
			header: editHeader,
			content: {
				controller: CONTROLLER.TRACK_EDIT_CONTROLLER,
				controllerAs: DEFAULT.CONTROLLER_NAME,
				templateUrl: PATH.TRACK_EDIT_FORM
			}
		}
	});
	$urlRouterProvider.otherwise(URL.HOME_PAGE);
}]);