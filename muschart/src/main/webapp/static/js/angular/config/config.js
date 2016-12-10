'use strict';
app.config(function($cookiesProvider, $locationProvider) {

	$cookiesProvider.defaults.path = '/';
	$cookiesProvider.defaults.expires = new Date(new Date().getTime() + 604800000);
	$locationProvider.html5Mode(true);

});