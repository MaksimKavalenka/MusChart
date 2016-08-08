'use strict';
app.controller('ArtistController', ['$stateParams', 'DEFAULT', 'ArtistFactory', 'FlashService', 'PaginationService', function($stateParams, DEFAULT, ArtistFactory, FlashService, PaginationService) {
	var self = this;
	self.artists = [];

	self.getArtistsByIds = function(idFrom, idTo) {
		ArtistFactory.getArtistsByIds(idFrom, idTo, function(response) {
			if (response.success) {
				self.artists = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.getArtistsByPage = function(page) {
		self.getArtistsByIds(DEFAULT.COUNT * (page - 1) + 1, DEFAULT.COUNT * page);
		PaginationService.getPages(page, 'artists');
	};

	self.getArtistsByPage($stateParams.page);

}]);