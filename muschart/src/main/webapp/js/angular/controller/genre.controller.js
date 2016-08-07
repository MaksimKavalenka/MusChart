'use strict';
app.controller('GenreController', ['$scope', '$stateParams', 'DEFAULT', 'GenreFactory', 'FlashService', 'PaginationService', function($scope, $stateParams, DEFAULT, GenreFactory, FlashService, PaginationService) {
	var self = this;
	self.genre = {id: null, name: '', rating: null};
	self.genres = [];

	self.createGenre = function() {
		self.dataLoading = true;
		GenreFactory.createGenre(self.genre.name, function(response) {
			if (response.success) {
				FlashService.success(response.message);
			} else {
				FlashService.error(response.message);
			}
			self.dataLoading = false;
		});
	};

	self.getGenresByIds = function(idFrom, idTo) {
		GenreFactory.getGenresByIds(idFrom, idTo, function(response) {
			if (response.success) {
				self.genres = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.deleteGenre = function(id) {
		GenreFactory.deleteGenre(id, function(response) {
			if (response.success) {
				FlashService.success(response.message);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.getGenresByPage = function(page) {
		self.getGenresByIds(DEFAULT.COUNT * (page - 1) + 1, DEFAULT.COUNT * page);
		PaginationService.getPages(page, 'genres');
	};

	self.reset = function() {
		self.genre = {id: null, name: '', rating: null};
		$scope.form.$setPristine();
	};

	self.getGenresByPage($stateParams.page);

}]);