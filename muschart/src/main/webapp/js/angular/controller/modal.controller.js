'use strict';
app.controller('ModalController', ['$scope', function($scope) {
	$scope.showModal = function() {
		$scope.modal = true;
	}
}]);