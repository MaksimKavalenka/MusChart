'use strict';
app.controller('TrackEditController', function($scope, TYPE, ArtistFactory, GenreFactory, TrackFactory, UnitFactory, ChoiceService, FileService, FlashService) {

	$scope.artists = [];
	$scope.genres = [];
	$scope.units = [];

	$scope.createTrack = function() {
		$scope.dataLoading = true;
		var songFlag = true;
		var coverFlag = true;
		FileService.uploadFile($scope.songFile, TYPE.SONG, function(response) {
			if (!response.success) {
				songFlag = false;
				FlashService.error(response.message);
			}
		});
		FileService.uploadFile($scope.coverFile, TYPE.COVER, function(response) {
			if (!response.success) {
				coverFlag = false;
				FlashService.error(response.message);
			}
		});
		if (songFlag && coverFlag) {
			if ($scope.track.video == '') {
				$scope.track.video = null;
			}
			TrackFactory.createTrack($scope.track.name, $scope.track.song.replace(/^C:\\fakepath\\/i, ''), $scope.track.cover.replace(/^C:\\fakepath\\/i, ''), $scope.track.video, $scope.track.release, angular.toJson($scope.artistsChoice), angular.toJson($scope.unitsChoice), angular.toJson($scope.genresChoice), function(response) {
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

	$scope.deleteTrack = function(id) {
		TrackFactory.deleteTrack(id, function(response) {
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
		ArtistFactory.getAllArtistsIdAndName(function(response) {
			if (response.success) {
				$scope.artists = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
		GenreFactory.getAllGenresIdAndName(function(response) {
			if (response.success) {
				$scope.genres = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
		UnitFactory.getAllUnitsIdAndName(function(response) {
			if (response.success) {
				$scope.units = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	init();

});