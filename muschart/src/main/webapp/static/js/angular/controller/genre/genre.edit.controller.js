'use strict';
app.controller('GenreEditController', function($scope, GenreFactory, FlashService) {

	$scope.createGenre = function() {
		$scope.dataLoading = true;
		GenreFactory.createGenre($scope.genre.name, function(response) {
			if (response.success) {
				$scope.reset();
				FlashService.success(response.message);
			} else {
				FlashService.error(response.message);
			}
			$scope.dataLoading = false;
		});
	};

	$scope.deleteGenre = function(id) {
		GenreFactory.deleteGenre(id, function(response) {
			if (response.success) {
				FlashService.success(response.message);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	$scope.reset = function() {
		$scope.form.$setPristine();
	};

});