'use strict';
app.controller('TrackController', ['$scope', '$stateParams', 'DEFAULT', 'TYPE', 'FlashFactory', 'FileService', 'PaginationService', 'TrackFactory', function($scope, $stateParams, DEFAULT, TYPE, FlashFactory, FileService, PaginationService, TrackFactory) {
	var self = this;
	self.track = {id: null, name: '', song: '', cover: '', date: '', rating: null};
	self.tracks = [];
	self.pages = [];

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

	self.getTracksByIdsAsc = function(idFrom, idTo) {
		TrackFactory.getTracksByIdsAsc(idFrom, idTo, function(response) {
			if (response.success) {
				self.tracks = response.data;
			} else {
				FlashFactory.error(response.message);
			}
		});
	};

	self.getTracksByIdsDesc = function(idFrom, idTo) {
		TrackFactory.getTracksByIdsDesc(idFrom, idTo, function(response) {
			if (response.success) {
				self.tracks = response.data;
			} else {
				FlashFactory.error(response.message);
			}
		});
	};

	self.getAmplitudeTracksByIdsAsc = function(idFrom, idTo) {
		TrackFactory.getAmplitudeTracksByIdsAsc(idFrom, idTo, function(response) {
			if (response.success) {
				Amplitude.init(response.data);
			} else {
				FlashFactory.error(response.message);
			}
		});
	};

	self.getAmplitudeTracksByIdsDesc = function(idFrom, idTo) {
		TrackFactory.getAmplitudeTracksByIdsDesc(idFrom, idTo, function(response) {
			if (response.success) {
				Amplitude.init(response.data);
			} else {
				FlashFactory.error(response.message);
			}
		});
	};

	self.deleteTrack = function(id) {
		TrackFactory.deleteTrack(id, function(response) {
			if (response.success) {
				FlashFactory.success(response.message);
			} else {
				FlashFactory.error(response.message);
			}
		});
	};

	self.getTracksByPage = function(page) {
		self.getTracksByIdsDesc(DEFAULT.COUNT * (page - 1) + 1, DEFAULT.COUNT * page);
		self.getAmplitudeTracksByIdsDesc(DEFAULT.COUNT * (page - 1) + 1, DEFAULT.COUNT * page);
		PaginationService.getPages(page, 'tracks', function(response) {
			self.pages = response;
		});
	};

	self.reset = function() {
		self.track = {id: null, name: '', song: '', cover: '', date: '', rating: null};
		$scope.form.$setPristine();
	};

	self.getTracksByPage($stateParams.page);

}]);