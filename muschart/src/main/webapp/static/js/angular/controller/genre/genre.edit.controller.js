'use strict';
app.controller('GenreEditController', function($scope, GenreFactory, FlashService) {

	var self = this;

	self.createGenre = function() {
		self.dataLoading = true;
		GenreFactory.createGenre(self.genre.name, function(response) {
			if (response.success) {
				self.reset();
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
		$scope.form.$setPristine();
	};

});