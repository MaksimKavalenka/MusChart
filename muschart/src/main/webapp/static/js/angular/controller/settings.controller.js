'use strict';
app.controller('SettingsController', function($cookies, $scope, $translate) {

	$scope.settings = $cookies.getObject('settings');
	$scope.languages = [{name: 'Беларуская', iso: 'by'}, {name: 'English', iso: 'en'}, {name: 'Français', iso: 'fr'}, {name: 'Русский', iso: 'ru'}];
	$scope.sorts = {};
	$scope.sorts.artists = [{name: '', value: 0}, {name: '', value: 1}, {name: '', value: 2}];
	$scope.sorts.genres = [{name: '', value: 0}, {name: '', value: 1}, {name: '', value: 2}];
	$scope.sorts.tracks = [{name: '', value: 0}, {name: '', value: 1}, {name: '', value: 2}, {name: '', value: 3}];

	$scope.changeLanguage = function(key) {
		$translate.use(key);
		init();
	};

	$scope.saveSettings = function() {
		$cookies.putObject('settings', $scope.settings);
	};

	function init() {
		$translate(['ALPHABET', 'PUBLISH_DATE', 'RATING', 'RELEASE_DATE'])
		.then(function(translations) {
			$scope.sorts.artists[0].name = translations.ALPHABET;
			$scope.sorts.genres[0].name = translations.ALPHABET;
			$scope.sorts.tracks[0].name = translations.ALPHABET;

			$scope.sorts.artists[1].name = translations.PUBLISH_DATE;
			$scope.sorts.genres[1].name = translations.PUBLISH_DATE;
			$scope.sorts.tracks[1].name = translations.PUBLISH_DATE;

			$scope.sorts.artists[2].name = translations.RATING;
			$scope.sorts.genres[2].name = translations.RATING;
			$scope.sorts.tracks[2].name = translations.RATING;

			$scope.sorts.tracks[3].name = translations.RELEASE_DATE;
		});
	}

	init();

});