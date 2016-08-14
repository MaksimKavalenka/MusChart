'use strict';
app.controller('TrackController', ['$scope', '$state', 'STATE', 'TrackFactory', 'FlashService', 'PaginationService', function($scope, $state, STATE, TrackFactory, FlashService, PaginationService) {
	var self = this;
	self.tracks = [];

	self.init = function(state, sort, order, page) {
		switch (state) {
			case STATE.TRACKS:
				self.getTracksByCriteria(sort, order, page);
				self.getAmplitudeTracksByCriteria(sort, order, page);
				break;
			case STATE.ARTIST_TRACKS:
				self.getTracksByCriteriaExt('artist', $state.params.id, sort, order, page);
				self.getAmplitudeTracksByCriteriaExt('artist', $state.params.id, sort, order, page);
				break;
			case STATE.GENRE_TRACKS:
				self.getTracksByCriteriaExt('genre', $state.params.id, sort, order, page);
				self.getAmplitudeTracksByCriteriaExt('genre', $state.params.id, sort, order, page);
				break;
			case STATE.USER_TRACKS:
				self.getTracksByCriteriaExt('user', $scope.globals.user.id, sort, order, page);
				self.getAmplitudeTracksByCriteriaExt('user', $scope.globals.user.id, sort, order, page);
				break;
		}
		PaginationService.getPages(page, state);
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

	self.getTracksByCriteriaExt = function(relation, id, sort, order, page) {
		TrackFactory.getTracksByCriteriaExt(relation, id, sort, order, page, function(response) {
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

	self.getAmplitudeTracksByCriteriaExt = function(relation, id, sort, order, page) {
		TrackFactory.getAmplitudeTracksByCriteriaExt(relation, id, sort, order, page, function(response) {
			if (response.success) {
				Amplitude.init(response.data);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.init($state.current.name, $scope.sort.tracks, $scope.order.tracks, $state.params.page);

}]);