'use strict';
app.controller('ArtistController', ['$scope', '$stateParams', 'DEFAULT', 'ArtistFactory', 'FileService', 'FlashService', 'PaginationService', function($scope, $stateParams, DEFAULT, ArtistFactory, FileService, FlashService, PaginationService) {
	var self = this;
	self.artist = {id: null, name: '', photo: '', rating: null};
	self.artists = [];

	self.createArtist = function() {
		self.dataLoading = true;
		ArtistFactory.createArtist(self.artist.name, self.artist.photo, function(response) {
			if (response.success) {
				FlashService.success(response.message);
			} else {
				FlashService.error(response.message);
			}
			self.dataLoading = false;
		});
	};

	self.getArtistsByIds = function(idFrom, idTo) {
		ArtistFactory.getArtistsByIds(idFrom, idTo, function(response) {
			if (response.success) {
				self.artists = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.deleteArtist = function(id) {
		ArtistFactory.deleteArtist(id, function(response) {
			if (response.success) {
				FlashService.success(response.message);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.getArtistsByPage = function(page) {
		self.getArtistsByIds(DEFAULT.COUNT * (page - 1) + 1, DEFAULT.COUNT * page);
		PaginationService.getPages(page, 'artists');
	};

	self.reset = function() {
		self.artist = {id: null, name: '', photo: '', rating: null};
		$scope.form.$setPristine();
	};

	self.getArtistsByPage($stateParams.page);

}]);