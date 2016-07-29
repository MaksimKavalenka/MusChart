'use strict';
app.controller('AmplitudeController', ['$scope', '$state', function($scope, $state) {
	$scope.show = function() {
		return $state.is('tracks');
	}
}]);