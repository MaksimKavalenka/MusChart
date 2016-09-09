'use strict';
app.controller('TrackController', ['$scope', '$state', 'STATE', 'UPLOAD', 'TrackFactory', 'AmplitudeService', 'FlashService', 'PaginationService', function($scope, $state, STATE, UPLOAD, TrackFactory, AmplitudeService, FlashService, PaginationService) {

	var self = this;
	self.url = '#';
	self.info = {};
	self.tracks = [];

	self.init = function(state, sort, order, page) {
		switch (state) {
			case STATE.PLAYLIST:
				self.url = '#';
				self.tracks = AmplitudeService.playlist;
				break;
			case STATE.TRACKS:
				self.url = '#';
				self.getTracksByCriteria(sort, order, page);
				PaginationService.setPagination(page, state);
				break;
			case STATE.TRACK:
			case STATE.TRACK_ARTISTS:
			case STATE.TRACK_GENRES:
				self.getTrackById($state.params.id);
				break;
			case STATE.ARTIST:
				self.url = 'artist_tracks({id: ' + $state.params.id + ', page: 1})';
				self.getTracksByCriteriaExt('artist', $state.params.id, sort, order, 0);
				break;
			case STATE.GENRE:
				self.url = 'genre_tracks({id: ' + $state.params.id + ', page: 1})';
				self.getTracksByCriteriaExt('genre', $state.params.id, sort, order, 0);
				break;
			case STATE.ARTIST_TRACKS:
				self.url = '#';
				self.getTracksByCriteriaExt('artist', $state.params.id, sort, order, page);
				PaginationService.setPaginationExt('artist', $state.params.id, page, state);
				break;
			case STATE.GENRE_TRACKS:
				self.url = '#';
				self.getTracksByCriteriaExt('genre', $state.params.id, sort, order, page);
				PaginationService.setPaginationExt('genre', $state.params.id, page, state);
				break;
			case STATE.USER_TRACKS:
				self.url = '#';
				self.getTracksByCriteriaExt('user', $scope.user.id, sort, order, page);
				PaginationService.setPaginationExt('user', $scope.user.id, page, state);
				break;
		}
	};

	self.getTrackById = function(id) {
		TrackFactory.getTrackById(id, function(response) {
			if (response.success) {
				self.info.image = UPLOAD.TRACK_COVER + '/' + response.data.cover;
				self.info.data = response.data.name;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.getTracksByCriteria = function(sort, order, page) {
		TrackFactory.getTracksByCriteria(sort, order, page, function(response) {
			if (response.success) {
				self.tracks = response.data;
				Amplitude.init(AmplitudeService.parseSongs(self.tracks));
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.getTracksByCriteriaExt = function(relation, id, sort, order, page) {
		TrackFactory.getTracksByCriteriaExt(relation, id, sort, order, page, function(response) {
			if (response.success) {
				self.tracks = response.data;
				Amplitude.init(AmplitudeService.parseSongs(self.tracks));
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.playSong = function(track) {
		Amplitude.playNow(AmplitudeService.parseSong(track));
	};

	$scope.showModal = function(video) {
		$scope.video = video;
		$scope.modal = true;
	}

	self.init($state.current.name, $scope.settings.sort.tracks, $scope.settings.order.tracks, $state.params.page);

}]);