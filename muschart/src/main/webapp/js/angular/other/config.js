'use strict';
app.config(['$locationProvider', function($locationProvider) {
	$locationProvider.html5Mode(true);
}]);
app.config(['$stateProvider', '$urlRouterProvider', 'PATH', 'URL', function($stateProvider, $urlRouterProvider, PATH, URL) {
	var mainHeader = {
		controller: 'UserController',
		controllerAs: 'ctrl',
		templateUrl: PATH.MAIN_HEADER
	}
	var authenticationHeader = {
		templateUrl: PATH.AUTHENTICATION_HEADER
	}
	var editHeader = {
		templateUrl: PATH.EDIT_HEADER
	}
	$stateProvider
	.state('login', {
		url: URL.LOGIN,
		views: {
			header: authenticationHeader,
			content: {
				controller: 'UserController',
				controllerAs: 'ctrl',
				templateUrl: PATH.LOGIN_FORM
			}
		}
	})
	.state('register', {
		url: URL.REGISTER,
		views: {
			header: authenticationHeader,
			content: {
				controller: 'UserController',
				controllerAs: 'ctrl',
				templateUrl: PATH.REGISTER_FORM
			}
		}
	})
	.state('artists', {
		url: URL.ARTISTS + URL.PATTERN_PAGE,
		views: {
			header: mainHeader,
			tool: {
				controller: 'ArtistController',
				controllerAs: 'ctrl',
				templateUrl: PATH.PAGINATION_TOOL
			},
			content: {
				controller: 'ArtistController',
				controllerAs: 'ctrl',
				templateUrl: PATH.ARTIST_CONTENT
			}
		}
	})
	.state('genres', {
		url: URL.GENRES + URL.PATTERN_PAGE,
		views: {
			header: mainHeader,
			tool: {
				controller: 'GenreController',
				controllerAs: 'ctrl',
				templateUrl: PATH.PAGINATION_TOOL
			},
			content: {
				controller: 'GenreController',
				controllerAs: 'ctrl',
				templateUrl: PATH.GENRE_CONTENT
			}
		}
	})
	.state('tracks', {
		url: URL.TRACKS + URL.PATTERN_PAGE,
		views: {
			header: mainHeader,
			tool: {
				controller: 'TrackController',
				controllerAs: 'ctrl',
				templateUrl: PATH.PAGINATION_TOOL
			},
			content: {
				controller: 'TrackController',
				controllerAs: 'ctrl',
				templateUrl: PATH.TRACK_CONTENT
			}
		}
	})
	.state('artists/add', {
		url: URL.ARTISTS_ADD,
		views: {
			header: editHeader,
			content: {
				controller: 'ArtistController',
				controllerAs: 'ctrl',
				templateUrl: PATH.ARTIST_EDIT_FORM
			}
		}
	})
	.state('genres/add', {
		url: URL.GENRES_ADD,
		views: {
			header: editHeader,
			content: {
				controller: 'GenreController',
				controllerAs: 'ctrl',
				templateUrl: PATH.GENRE_EDIT_FORM
			}
		}
	})
	.state('tracks/add', {
		url: URL.TRACKS_ADD,
		views: {
			header: editHeader,
			content: {
				controller: 'TrackController',
				controllerAs: 'ctrl',
				templateUrl: PATH.TRACK_EDIT_FORM
			}
		}
	});
	$urlRouterProvider.otherwise(URL.HOME_PAGE);
}]);