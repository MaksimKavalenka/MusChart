'use strict';
app.controller('GenreController', ['$scope', '$stateParams', 'GenreFactory', 'FlashService', 'PaginationService', function($scope, $stateParams, GenreFactory, FlashService, PaginationService) {
	var self = this;
	self.genres = [];

	self.init = function(sort, order, page) {
		self.getGenresByCriteria(sort, order, page);
		PaginationService.getPages(page, 'genres');
	};

	self.getGenresByCriteria = function(sort, order, page, idTo) {
		GenreFactory.getGenresByCriteria(sort, order, page, function(response) {
			if (response.success) {
				self.genres = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.init($scope.sort.genres, $scope.order.genres, $stateParams.page);

}]);