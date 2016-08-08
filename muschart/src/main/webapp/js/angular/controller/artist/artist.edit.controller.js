'use strict';
app.controller('ArtistEditController', ['$scope', 'ArtistFactory', 'ChoiceService', 'FileService', 'FlashService', function($scope, ArtistFactory, ChoiceService, FileService, FlashService) {
	var self = this;
	self.artist = {id: null, name: '', photo: '', rating: null};
	self.genresChoice = [{id: 0}];

	self.init = function() {
		ChoiceService.getAllGenres(function(response) {
			if (!response.success) {
				FlashService.error(response.message);
			}
		});
	};

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
		self.artist = {id: null, name: '', photo: '', rating: null};
		$scope.form.$setPristine();
	};

	self.addChoice = function() {
		self.genresChoice.push({id: self.genresChoice.length});
	};

	self.removeChoice = function(id) {
		self.genresChoice.splice(id, 1);
		for (var i = id; i < self.genresChoice.length; i++) {
			self.genresChoice[i].id = self.genresChoice[i].id - 1;
		}
	};

	self.init();

}]);