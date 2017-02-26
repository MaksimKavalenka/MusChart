'use strict';
app.service('CookieService', function($cookies, COOKIE) {

	function getUser() {
		return $cookies.getObject(COOKIE.USER);
	}

	function setUser(user) {
		$cookies.putObject(COOKIE.USER, user);
	}

	function clearUser() {
		$cookies.remove(COOKIE.USER);
	}

	function getSettings() {
		return $cookies.getObject(COOKIE.SETTINGS);
	}

	function setSettings(settings) {
		$cookies.putObject(COOKIE.SETTINGS, settings);
	}

	function clearSettings() {
		$cookies.remove(COOKIE.SETTINGS);
	}

	return {
		getUser: getUser,
		setUser: setUser,
		clearUser: clearUser,
		getSettings: getSettings,
		setSettings: setSettings,
		clearSettings: clearSettings
	};

});