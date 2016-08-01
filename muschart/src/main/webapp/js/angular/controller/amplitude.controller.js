'use strict';
app.controller('AmplitudeController', ['$scope', '$state', function($scope, $state) {
	$scope.show = function() {
		return $.inArray($state.current.name, ['tracks', 'artists', 'genres']) !== -1;
	}
}]);