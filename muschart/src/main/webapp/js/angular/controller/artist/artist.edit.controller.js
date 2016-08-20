'use strict';
app.controller('ArtistEditController', ['$scope', 'TYPE', 'ArtistFactory', 'GenreFactory', 'ChoiceService', 'FileService', 'FlashService', function($scope, TYPE, ArtistFactory, GenreFactory, ChoiceService, FileService, FlashService) {
	var self = this;
	self.artist = {name: '', photo: ''};

	self.init = function() {
		ChoiceService.reset();
		GenreFactory.getAllGenres(function(response) {
			if (response.success) {
				$scope.genres = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.createArtist = function() {
		self.dataLoading = true;
		var photoFlag = true;
		FileService.uploadFile($scope.photoFile, TYPE.PHOTO, function(response) {
			if (!response.success) {
				photoFlag = false;
				FlashService.error(response.message);
			}
		});
		if (photoFlag) {
			ArtistFactory.createArtist(self.artist.name, self.artist.photo.replace(/^C:\\fakepath\\/i, ''), angular.toJson($scope.genresChoice), function(response) {
				if (response.success) {
					self.reset();
					FlashService.success(response.message);
				} else {
					FlashService.error(response.message);
				}
			});
		}
		self.dataLoading = false;
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

	self.reset = function() {
		self.artist = {name: '', photo: ''};
		$scope.form.$setPristine();
	};

	self.init();

}]);