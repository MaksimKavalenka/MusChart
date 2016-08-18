'use strict';
app.controller('TrackEditController', ['$scope', 'TYPE', 'TrackFactory', 'ChoiceService', 'FileService', 'FlashService', function($scope, TYPE, TrackFactory, ChoiceService, FileService, FlashService) {
	var self = this;
	self.track = {name: '', song: '', cover: '', video: '', release: ''};

	self.init = function() {
		ChoiceService.reset();
		ChoiceService.getAllUnits(function(response) {
			if (!response.success) {
				FlashService.error(response.message);
			}
		});
		ChoiceService.getAllArtists(function(response) {
			if (!response.success) {
				FlashService.error(response.message);
			}
		});
		ChoiceService.getAllGenres(function(response) {
			if (!response.success) {
				FlashService.error(response.message);
			}
		});
	};

	self.createTrack = function() {
		self.dataLoading = true;
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
			if (self.track.video == '') {
				self.track.video = null;
			}
			TrackFactory.createTrack(self.track.name, self.track.song.replace(/^C:\\fakepath\\/i, ''), self.track.cover.replace(/^C:\\fakepath\\/i, ''), self.track.video, self.track.release, angular.toJson($scope.artistsChoice), angular.toJson($scope.genresChoice), function(response) {
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

	self.deleteTrack = function(id) {
		TrackFactory.deleteTrack(id, function(response) {
			if (response.success) {
				FlashService.success(response.message);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.reset = function() {
		self.track = {name: '', song: '', cover: '', video: '', release: ''};
		$scope.form.$setPristine();
	};

	self.init();

}]);