'use strict';
app.controller('TrackEditController', ['$scope', 'TYPE', 'TrackFactory', 'FileService', 'FlashService', function($scope, TYPE, TrackFactory, FileService, FlashService) {
	var self = this;
	self.track = {id: null, artistName: '', songName: '', song: '', cover: '', date: '', rating: null};

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
			TrackFactory.createTrack(self.track.artistName, self.track.songName, self.track.song.replace(/^C:\\fakepath\\/i, ''), self.track.cover.replace(/^C:\\fakepath\\/i, ''), self.track.date, function(response) {
				if (response.success) {
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
		self.track = {id: null, name: '', song: '', cover: '', date: '', rating: null};
		$scope.form.$setPristine();
	};

}]);