'use strict';
app.controller('ModalController', ['$scope', function($scope) {
	$scope.show = function() {
		$scope.showModal = true;
	}
}]);