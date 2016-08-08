'use strict';
app.controller('GenreController', ['$stateParams', 'DEFAULT', 'GenreFactory', 'FlashService', 'PaginationService', function($stateParams, DEFAULT, GenreFactory, FlashService, PaginationService) {
	var self = this;
	self.genres = [];

	self.getGenresByIds = function(idFrom, idTo) {
		GenreFactory.getGenresByIds(idFrom, idTo, function(response) {
			if (response.success) {
				self.genres = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.getGenresByPage = function(page) {
		self.getGenresByIds(DEFAULT.COUNT * (page - 1) + 1, DEFAULT.COUNT * page);
		PaginationService.getPages(page, 'genres');
	};

	self.getGenresByPage($stateParams.page);

}]);