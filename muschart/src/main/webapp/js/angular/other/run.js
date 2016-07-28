'use strict';
app.run(['$rootScope', '$location', '$cookieStore', '$http', function($rootScope, $location, $cookieStore, $http) {
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