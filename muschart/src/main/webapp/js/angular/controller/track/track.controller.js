'use strict';
app.controller('TrackController', ['$scope', '$stateParams', 'TrackFactory', 'FlashService', 'PaginationService', function($scope, $stateParams, TrackFactory, FlashService, PaginationService) {
	var self = this;
	self.tracks = [];

	self.init = function(sort, order, page) {
		self.getTracksByCriteria(sort, order, page);
		self.getAmplitudeTracksByCriteria(sort, order, page);
		PaginationService.getPages(page, 'tracks');
	};

	self.getTracksByCriteria = function(sort, order, page) {
		TrackFactory.getTracksByCriteria(sort, order, page, function(response) {
			if (response.success) {
				self.tracks = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.getAmplitudeTracksByCriteria = function(sort, order, page) {
		TrackFactory.getAmplitudeTracksByCriteria(sort, order, page, function(response) {
			if (response.success) {
				Amplitude.init(response.data);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.init($scope.sort.tracks, $scope.order.tracks, $stateParams.page);

}]);