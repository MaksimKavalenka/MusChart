'use strict';
app.controller('ArtistController', ['$scope', '$stateParams', 'ArtistFactory', 'FlashService', 'PaginationService', function($scope, $stateParams, ArtistFactory, FlashService, PaginationService) {
	var self = this;
	self.artists = [];

	self.init = function(sort, order, page) {
		self.getArtistsByCriteria(sort, order, page);
		PaginationService.getPages(page, 'artists');
	};

	self.getArtistsByCriteria = function(sort, order, page) {
		ArtistFactory.getArtistsByCriteria(sort, order, page, function(response) {
			if (response.success) {
				self.artists = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.init($scope.sort.artists, $scope.order.artists, $stateParams.page);

}]);