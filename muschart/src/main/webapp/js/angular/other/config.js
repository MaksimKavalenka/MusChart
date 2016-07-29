'use strict';
app.config(['$locationProvider', function($locationProvider) {
	$locationProvider.html5Mode(true);
}]);
app.config(['$stateProvider', '$urlRouterProvider', 'DEFAULT', 'PATH', 'URL', function($stateProvider, $urlRouterProvider, DEFAULT, PATH, URL) {
	var main_header = {
		controller: 'UserController',
		controllerAs: 'ctrl',
		templateUrl: PATH.MAIN_HEADER
	}
	var authentication_header = {
		templateUrl: PATH.AUTHENTICATION_HEADER
	}
	var edit_header = {
		templateUrl: PATH.EDIT_HEADER
	}
	$stateProvider
	.state('tracks', {
		url: URL.TRACKS + URL.PAGE,
		views: {
			header: main_header,
			content: {
				controller: 'TrackController',
				controllerAs: 'ctrl',
				templateUrl: PATH.TRACK_CONTENT
			}
		}
	})
	.state('artists', {
		url: URL.ARTISTS + URL.PAGE,
		views: {
			header: main_header,
			content: {
				controller: 'ArtistController',
				controllerAs: 'ctrl',
				templateUrl: PATH.ARTIST_CONTENT
			}
		}
	})
	.state('genres', {
		url: URL.GENRES + URL.PAGE,
		views: {
			header: main_header,
			content: {
				controller: 'GenreController',
				controllerAs: 'ctrl',
				templateUrl: PATH.GENRE_CONTENT
			}
		}
	})
	.state('login', {
		url: URL.LOGIN,
		views: {
			header: authentication_header,
			content: {
				templateUrl: PATH.LOGIN_FORM
			}
		}
	})
	.state('register', {
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