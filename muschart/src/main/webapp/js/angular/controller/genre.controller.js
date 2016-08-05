'use strict';
app.controller('GenreController', ['$scope', '$stateParams', 'DEFAULT', 'FlashFactory', 'GenreFactory', function($scope, $stateParams, DEFAULT, FlashFactory, GenreFactory) {
	var self = this;
	self.genre = {id: null, name: '', rating: null};
	self.genres = [];

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
		GenreFactory.getGenresByIds(idFrom, idTo).then(
			function(response) {
				self.genres = response;
			},
			function(errResponse) {
				console.error('Error while getting genres');
			}
		);
	};

	self.getAllGenres = function() {
		GenreFactory.getAllGenres().then(
			function(response) {
				self.genres = response;
			},
			function(errResponse) {
				console.error('Error while getting genres');
			}
		);
	};

	self.deleteGenre = function(id) {
		GenreFactory.deleteGenre(id).then(
			function(errResponse) {
				console.error('Error while deleting genre');
			}
		);
	};

	self.getGenresByPage = function(page) {
		self.getGenresByIds(DEFAULT.COUNT * (page - 1) + 1, DEFAULT.COUNT * page);
	};

	self.reset = function() {
		self.genre = {id: null, name: '', rating: null};
		$scope.form.$setPristine();
	};

	self.getGenresByPage($stateParams.page);

}]);