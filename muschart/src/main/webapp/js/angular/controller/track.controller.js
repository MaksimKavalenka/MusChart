'use strict';
app.controller('TrackController', ['$scope', '$stateParams', 'DEFAULT', 'TYPE', 'FlashFactory', 'FileService', 'TrackFactory', function($scope, $stateParams, DEFAULT, TYPE, FlashFactory, FileService, TrackFactory) {
	var self = this;
	self.track = {id: null, name: '', song: '', cover: '', date: '', rating: null};
	self.tracks = [];

	self.createTrack = function() {
		self.dataLoading = true;
		var songFlag = true;
		var coverFlag = true;
		FileService.uploadFile($scope.songFile, TYPE.SONG, function(response) {
			if (!response.success) {
				songFlag = false;
				FlashFactory.error(response.message);
			}
		});
		FileService.uploadFile($scope.coverFile, TYPE.COVER, function(response) {
			if (!response.success) {
				coverFlag = false;
				FlashFactory.error(response.message);
			}
		});
		if (songFlag && coverFlag) {
			TrackFactory.createTrack(self.track.name, self.track.song.replace(/^C:\\fakepath\\/i, ''), self.track.cover.replace(/^C:\\fakepath\\/i, ''), self.track.date, function(response) {
				if (response.success) {
					FlashFactory.success(response.message);
				} else {
					FlashFactory.error(response.message);
				}
			});
		}
		self.dataLoading = false;
	};

	self.getTracksByIds = function(idFrom, idTo) {
		TrackFactory.getTracksByIds(idFrom, idTo).then(
			function(response) {
				self.tracks = response;
			},
			function(errResponse) {
				console.error('Error while getting tracks');
			}
		);
	};

	self.getAmplitudeTracksByIds = function(idFrom, idTo) {
		TrackFactory.getAmplitudeTracksByIds(idFrom, idTo).then(
			function(response) {
				Amplitude.init(response);
			},
			function(errResponse) {
				console.error('Error while getting tracks');
			}
		);
	};

	self.deleteTrack = function(id) {
		TrackFactory.deleteTrack(id).then(
			function(errResponse) {
				console.error('Error while deleting track');
			}
		);
	};

	self.getTracksByPage = function(page) {
		self.getTracksByIds(DEFAULT.COUNT * (page - 1) + 1, DEFAULT.COUNT * page);
		self.getAmplitudeTracksByIds(DEFAULT.COUNT * (page - 1) + 1, DEFAULT.COUNT * page);
	};

	self.reset = function() {
		self.track = {id: null, name: '', song: '', cover: '', date: '', rating: null};
		$scope.form.$setPristine();
	};

	self.getTracksByPage($stateParams.page);

}]);