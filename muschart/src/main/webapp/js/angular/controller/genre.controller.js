'use strict';
app.controller('GenreController', ['$scope', '$stateParams', 'DEFAULT', 'FlashFactory', 'GenreFactory', 'PaginationService', function($scope, $stateParams, DEFAULT, FlashFactory, GenreFactory, PaginationService) {
	var self = this;
	self.genre = {id: null, name: '', rating: null};
	self.genres = [];
	self.pages = [];

	self.createGenre = function() {
		self.dataLoading = true;
		GenreFactory.createGenre(self.genre.name, function(response) {
			if (response.success) {
				FlashFactory.success(response.message);
			} else {
				FlashFactory.error(response.message);
			}
			self.dataLoading = false;
		});
	};

	self.getGenresByIds = function(idFrom, idTo) {
		GenreFactory.getGenresByIds(idFrom, idTo, function(response) {
			if (response.success) {
				self.genres = response.data;
			} else {
				FlashFactory.error(response.message);
			}
		});
	};

	self.deleteGenre = function(id) {
		GenreFactory.deleteGenre(id, function(response) {
			if (response.success) {
				FlashFactory.success(response.message);
			} else {
				FlashFactory.error(response.message);
			}
		});
	};

	self.getGenresByPage = function(page) {
		self.getGenresByIds(DEFAULT.COUNT * (page - 1) + 1, DEFAULT.COUNT * page);
		PaginationService.getPages(page, 'genres', function(response) {
			self.pages = response;
		});
	};

	self.reset = function() {
		self.genre = {id: null, name: '', rating: null};
		$scope.form.$setPristine();
	};

	self.getGenresByPage($stateParams.page);

}]);