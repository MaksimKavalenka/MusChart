'use strict';
app.controller('ArtistEditController', function($scope, TYPE, ArtistFactory, GenreFactory, ChoiceService, FileService, FlashService) {

	$scope.genres = [];

	$scope.createArtist = function() {
		$scope.dataLoading = true;
		var photoFlag = true;
		FileService.uploadFile($scope.photoFile, TYPE.PHOTO, function(response) {
			if (!response.success) {
				photoFlag = false;
				FlashService.error(response.message);
			}
		});
		if (photoFlag) {
			ArtistFactory.createArtist($scope.artist.name, $scope.artist.photo.replace(/^C:\\fakepath\\/i, ''), angular.toJson($scope.genresChoice), function(response) {
				if (response.success) {
					$scope.reset();
					ChoiceService.reset();
					FlashService.success(response.message);
				} else {
					FlashService.error(response.message);
				}
			});
		}
		$scope.dataLoading = false;
	};

	$scope.deleteArtist = function(id) {
		ArtistFactory.deleteArtist(id, function(response) {
			if (response.success) {
				FlashService.success(response.message);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	$scope.reset = function() {
		$scope.form.$setPristine();
	};

	function init() {
		ChoiceService.reset();
		GenreFactory.getAllGenresIdAndName(function(response) {
			if (response.success) {
				$scope.genres = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	init();

});