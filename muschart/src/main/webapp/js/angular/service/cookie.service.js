'use strict';
app.service('CookieService', ['$cookies', '$rootScope', function($cookies, $rootScope) {
	return {

		setCredentials: function(user) {
			$rootScope.user = {
				id: user.id,
				login: user.login,
				role: user.role.name
			};
			$cookies.putObject('user', $rootScope.user);
		},

		setSettings: function() {
			$cookies.putObject('settings', $rootScope.settings);
		},

		setTracks: function(tracks) {
			$rootScope.tracks = tracks;
			$cookies.putObject('tracks', $rootScope.tracks);
		},

		clearCredentials: function() {
			$rootScope.user = null;
			$cookies.remove('user');
		}

	};
}]);