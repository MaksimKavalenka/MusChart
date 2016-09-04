'use strict';
app.service('CookieService', ['$cookies', '$rootScope', function($cookies, $rootScope) {

	function setCredentials(user) {
		$rootScope.user = {
			id: user.id,
			login: user.login,
			role: user.role.name
		};
		$cookies.putObject('user', $rootScope.user);
	}

	function setSettings() {
		$cookies.putObject('settings', $rootScope.settings);
	}

	function setTracks(tracks) {
		$rootScope.tracks = tracks;
		$cookies.putObject('tracks', $rootScope.tracks);
	}

	function clearCredentials() {
		$rootScope.user = null;
		$cookies.remove('user');
	}

	return {
		setCredentials: setCredentials,
		setSettings: setSettings,
		setTracks: setTracks,
		clearCredentials: clearCredentials
	};
}]);