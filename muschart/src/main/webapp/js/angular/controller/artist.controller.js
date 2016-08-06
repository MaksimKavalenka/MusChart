'use strict';
app.controller('ArtistController', ['$scope', '$stateParams', 'DEFAULT', 'FlashFactory', 'FileService', 'PaginationService', 'ArtistFactory', function($scope, $stateParams, DEFAULT, FlashFactory, FileService, PaginationService, ArtistFactory) {
	var self = this;
	self.artist = {id: null, name: '', photo: '', rating: null};
	self.artists = [];
	self.pages = [];

	self.createArtist = function() {
		self.dataLoading = true;
		ArtistFactory.createArtist(self.artist.name, self.artist.photo, function(response) {
			if (response.success) {
				FlashFactory.success(response.message);
			} else {
				FlashFactory.error(response.message);
			}
			self.dataLoading = false;
		});
	};

	self.getArtistsByIds = function(idFrom, idTo) {
		ArtistFactory.getArtistsByIds(idFrom, idTo, function(response) {
			if (response.success) {
				self.artists = response.data;
			} else {
				FlashFactory.error(response.message);
			}
		});
	};

	self.deleteArtist = function(id) {
		ArtistFactory.deleteArtist(id, function(response) {
			if (response.success) {
				FlashFactory.success(response.message);
			} else {
				FlashFactory.error(response.message);
			}
		});
	};

	self.getArtistsByPage = function(page) {
		self.getArtistsByIds(DEFAULT.COUNT * (page - 1) + 1, DEFAULT.COUNT * page);
		PaginationService.getPages(page, 'artists', function(response) {
			self.pages = response;
		});
	};

	self.reset = function() {
		self.artist = {id: null, name: '', photo: '', rating: null};
		$scope.form.$setPristine();
	};

	self.getArtistsByPage($stateParams.page);

}]);