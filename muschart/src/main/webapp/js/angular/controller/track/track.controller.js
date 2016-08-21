'use strict';
app.controller('TrackController', ['$scope', '$state', 'STATE', 'TrackFactory', 'CredentialsService', 'FlashService', 'PaginationService', function($scope, $state, STATE, TrackFactory, CredentialsService, FlashService, PaginationService) {
	var self = this;
	self.url = '#';
	self.info = {image: '', text: ''};
	self.tracks = [];

	self.init = function(state, sort, order, page) {
		switch (state) {
			case STATE.TRACKS:
				self.getTracksByCriteria(sort, order, page);
				self.getAmplitudeTracksByCriteria(sort, order, page);
				self.url = '#';
				break;
			case STATE.TRACK:
			case STATE.TRACK_ARTISTS:
			case STATE.TRACK_GENRES:
				self.getTrackById($state.params.id);
				break;
			case STATE.ARTIST:
				self.getTracksByCriteriaExt('artist', $state.params.id, sort, order, 0);
				self.getAmplitudeTracksByCriteriaExt('artist', $state.params.id, sort, order, 0);
				self.url = 'artist/tracks({id: ' + $state.params.id + ', page: 1})';
				break;
			case STATE.GENRE:
				self.getTracksByCriteriaExt('genre', $state.params.id, sort, order, 0);
				self.getAmplitudeTracksByCriteriaExt('genre', $state.params.id, sort, order, 0);
				self.url = 'genre/tracks({id: ' + $state.params.id + ', page: 1})';
				break;
			case STATE.ARTIST_TRACKS:
				self.getTracksByCriteriaExt('artist', $state.params.id, sort, order, page);
				self.getAmplitudeTracksByCriteriaExt('artist', $state.params.id, sort, order, page);
				self.url = '#';
				break;
			case STATE.GENRE_TRACKS:
				self.getTracksByCriteriaExt('genre', $state.params.id, sort, order, page);
				self.getAmplitudeTracksByCriteriaExt('genre', $state.params.id, sort, order, page);
				self.url = '#';
				break;
			case STATE.USER_TRACKS:
				self.getTracksByCriteriaExt('user', $scope.user.id, sort, order, page);
				self.getAmplitudeTracksByCriteriaExt('user', $scope.user.id, sort, order, page);
				self.url = '#';
				break;
		}
		PaginationService.getPages(page, state);
	};

	self.getTrackById = function(id) {
		TrackFactory.getTrackById(id, function(response) {
			if (response.success) {
				self.info.image = '/muschart/img/track/' + response.data.cover;
				self.info.text = response.data.name;
			} else {
				FlashService.error(response.message);
			}
		});
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
				CredentialsService.setTracks(response.data);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.getAmplitudeTracksByCriteriaExt = function(relation, id, sort, order, page) {
		TrackFactory.getAmplitudeTracksByCriteriaExt(relation, id, sort, order, page, function(response) {
			if (response.success) {
				Amplitude.init(response.data);
				CredentialsService.setTracks(response.data);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.init($state.current.name, $scope.sort.tracks, $scope.order.tracks, $state.params.page);

}]);