'use strict';
app.config(['$locationProvider', function($locationProvider) {
	$locationProvider.html5Mode(true);
}]);
app.config(['$stateProvider', '$urlRouterProvider', 'DEFAULT', 'PATH', 'URL', function($stateProvider, $urlRouterProvider, DEFAULT, PATH, URL) {
	var main_header = {
		templateUrl: PATH.MAIN_HEADER
	}
	var authentication_header = {
		templateUrl: PATH.AUTHENTICATION_HEADER
	}
	var edit_header = {
		templateUrl: PATH.EDIT_HEADER
	}
	$stateProvider
	.state('main', {
		abstract: true,
		templateUrl: PATH.MAIN_PAGE
	})
	.state('authentication', {
		abstract: true,
		templateUrl: PATH.AUTHENTICATION_PAGE
	})
	.state('tracks', {
		parent: 'main',
		url: URL.TRACKS + URL.PAGE,
		views: {
			header: main_header,
			content: {
				templateUrl: PATH.TRACK_CONTENT
			}
		}
	})
	.state('artists', {
		parent: 'main',
		url: URL.ARTISTS + URL.PAGE,
		views: {
			header: main_header,
			content: {
				templateUrl: PATH.ARTIST_CONTENT
			}
		}
	})
	.state('genres', {
		parent: 'main',
		url: URL.GENRES + URL.PAGE,
		views: {
			header: main_header,
			content: {
				templateUrl: PATH.GENRE_CONTENT
			}
		}
	})
	.state('login', {
		parent: 'authentication',
		url: URL.LOGIN,
		views: {
			header: authentication_header,
			content: {
				templateUrl: PATH.LOGIN_FORM
			}
		}
	})
	.state('register', {
		parent: 'authentication',
		url: URL.REGISTER,
		views: {
			header: authentication_header,
			content: {
				templateUrl: PATH.REGISTER_FORM
			}
		}
	});
	$urlRouterProvider.otherwise(URL.TRACKS + '/' + DEFAULT.PAGE);
}]);