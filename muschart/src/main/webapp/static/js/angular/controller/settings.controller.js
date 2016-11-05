'use strict';
app.controller('SettingsController', function($scope, $translate) {

	$scope.languages = [{name: 'Belarusian', iso: 'by'}, {name: 'English', iso: 'en'}, {name: 'Francais', iso: 'fr'}, {name: 'Russian', iso: 'ru'}];
	$scope.sorts = {};
	$scope.sorts.artists = [{name: 'Alphabet', value: 0}, {name: 'Publish date', value: 1}, {name: 'Rating', value: 2}];
	$scope.sorts.genres = [{name: 'Alphabet', value: 0}, {name: 'Publish date', value: 1}, {name: 'Rating', value: 2}];
	$scope.sorts.tracks = [{name: 'Alphabet', value: 0}, {name: 'Publish date', value: 1}, {name: 'Rating', value: 2}, {name: 'Release date', value: 3}];

	$scope.changeLanguage = function(key) {
		$translate.use(key);
	};

});