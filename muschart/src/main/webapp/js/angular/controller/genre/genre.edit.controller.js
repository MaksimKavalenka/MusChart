'use strict';
app.controller('GenreEditController', ['$scope', 'GenreFactory', 'FlashService', function($scope, GenreFactory, FlashService) {
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

	self.deleteGenre = function(id) {
		GenreFactory.deleteGenre(id, function(response) {
			if (response.success) {
				FlashService.success(response.message);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.reset = function() {
		self.genre = {id: null, name: '', rating: null};
		$scope.form.$setPristine();
	};

}]);