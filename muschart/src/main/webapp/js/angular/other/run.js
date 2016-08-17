'use strict';
app.run(['$cookieStore', '$http', '$location', '$rootScope', function($cookieStore, $http, $location, $rootScope) {
	$rootScope.sort = {tracks: 0, artists: 0, genres: 0, my_tracks: 0, my_artists: 0, my_genres: 0};
	$rootScope.order = {tracks: 'false', artists: 'false', genres: 'false', my_tracks: 'false', my_artists: 'false', my_genres: 'false'};
	$rootScope.globals = $cookieStore.get('globals') || {};
	if ($rootScope.globals.user) {
		$http.defaults.headers.common['Credentials'] = 'Basic ' + $rootScope.globals.user.authdata;
	}
	/*$rootScope.$on('$locationChangeStart', function(event, next, current) {
		var restrictedPage = $.inArray($location.path(), ['/login', '/register']) === -1;
		var loggedIn = $rootScope.globals.user;
		if (restrictedPage && !loggedIn) {
			$location.path('/login');
		}
	});*/
}]);