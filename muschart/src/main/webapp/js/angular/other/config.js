'use strict';
app.config(function($locationProvider) {
	$locationProvider.html5Mode(true);
});
app.config(function($httpProvider, $stateProvider, $urlRouterProvider) {
	var main_header = {
		templateUrl: 'muschart/html/title/main_header.html',
		controller: function($scope) {}
	}
	var authorization_header = {
		templateUrl: 'muschart/html/title/authorization_header.html',
		controller: function($scope) {}
	}
	var edit_header = {
		templateUrl: 'muschart/html/title/edit_header.html',
		controller: function($scope) {}
	}
	$stateProvider
	.state('main', {
		templateUrl: 'muschart/html/page/main.html',
		abstract: true,
	})
	.state('authorization', {
		templateUrl: 'muschart/html/page/authorization.html',
		abstract: true,
	})
	.state('edit', {
		templateUrl: 'muschart/html/page/edit.html',
		abstract: true,
	})
	.state('tracks', {
		url: '/muschart/tracks',
		parent: 'main',
		views: {
			header: main_header,
			content: {
				templateUrl: 'muschart/html/content/track.html',
				controller: function($scope) {}
			},
		}
		//controller: 'TrackController'
	})
	.state('artists', {
		url: '/muschart/artists',
		parent: 'main',
		views: {
			header: main_header,
			content: {
				templateUrl: 'muschart/html/content/artist.html',
				controller: function($scope) {}
			},
		}
		//controller: 'ArtistController'
	})
	.state('genres', {
		url: '/muschart/genres',
		parent: 'main',
		views: {
			header: main_header,
			content: {
				templateUrl: 'muschart/html/content/genre.html',
				controller: function($scope) {}
			},
		}
		//controller: 'GenreController'
	})
	.state('login', {
		url: '/muschart/login',
		parent: 'authorization',
		views: {
			header: authorization_header,
			content: {
				templateUrl: 'muschart/html/form/login.html',
				controller: function($scope) {}
			},
		}
		//controller: 'LoginController'
	})
	.state('registration', {
		url: '/muschart/registration',
		parent: 'authorization',
		views: {
			header: authorization_header,
			content: {
				templateUrl: 'muschart/html/form/registration.html',
				controller: function($scope) {}
			},
		}
		//controller: 'RegistrationController'
	});
	$urlRouterProvider.otherwise('/muschart/tracks');
});