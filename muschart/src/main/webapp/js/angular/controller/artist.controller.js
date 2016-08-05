'use strict';
app.controller('ArtistController', ['$scope', '$stateParams', 'DEFAULT', 'FlashFactory', 'ArtistFactory', function($scope, $stateParams, DEFAULT, FlashFactory, ArtistFactory) {
	var self = this;
	self.artist = {id: null, name: '', photo: '', rating: null};
	self.artists = [];

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
		ArtistFactory.getArtistsByIds(idFrom, idTo).then(
			function(response) {
				self.artists = response;
			},
			function(errResponse) {
				console.error('Error while getting artists');
			}
		);
	};

	self.getAllArtists = function() {
		ArtistFactory.getAllArtists().then(
			function(response) {
				self.artists = response;
			},
			function(errResponse) {
				console.error('Error while getting artists');
			}
		);
	};

	self.deleteArtist = function(id) {
		ArtistFactory.deleteArtist(id).then(
			function(errResponse) {
				console.error('Error while deleting artist');
			}
		);
	};

	self.getArtistsByPage = function(page) {
		self.getArtistsByIds(DEFAULT.COUNT * (page - 1) + 1, DEFAULT.COUNT * page);
	};

	self.reset = function() {
		self.artist = {id: null, name: '', photo: '', rating: null};
		$scope.form.$setPristine();
	};

	self.getArtistsByPage($stateParams.page);

}]);