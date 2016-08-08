'use strict';
app.controller('TrackController', ['$stateParams', 'DEFAULT', 'TrackFactory', 'FlashService', 'PaginationService', function($stateParams, DEFAULT, TrackFactory, FlashService, PaginationService) {
	var self = this;
	self.tracks = [];

	self.getTracksByIdsAsc = function(idFrom, idTo) {
		TrackFactory.getTracksByIdsAsc(idFrom, idTo, function(response) {
			if (response.success) {
				self.tracks = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.getTracksByIdsDesc = function(idFrom, idTo) {
		TrackFactory.getTracksByIdsDesc(idFrom, idTo, function(response) {
			if (response.success) {
				self.tracks = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.getAmplitudeTracksByIdsAsc = function(idFrom, idTo) {
		TrackFactory.getAmplitudeTracksByIdsAsc(idFrom, idTo, function(response) {
			if (response.success) {
				Amplitude.init(response.data);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.getAmplitudeTracksByIdsDesc = function(idFrom, idTo) {
		TrackFactory.getAmplitudeTracksByIdsDesc(idFrom, idTo, function(response) {
			if (response.success) {
				Amplitude.init(response.data);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.getTracksByPage = function(page) {
		self.getTracksByIdsDesc(DEFAULT.COUNT * (page - 1) + 1, DEFAULT.COUNT * page);
		self.getAmplitudeTracksByIdsDesc(DEFAULT.COUNT * (page - 1) + 1, DEFAULT.COUNT * page);
		PaginationService.getPages(page, 'tracks');
	};

	self.getTracksByPage($stateParams.page);

}]);