'use strict';
app.controller('TrackController', ['$scope', '$state', 'STATE', 'UPLOAD', 'TrackFactory', 'FlashService', 'PaginationService', function($scope, $state, STATE, UPLOAD, TrackFactory, FlashService, PaginationService) {
	var self = this;
	self.url = '#';
	self.info = {image: '', data: ''};
	self.tracks = [];

	self.init = function(state, sort, order, page) {
		switch (state) {
			case STATE.PLAYLIST:
				self.tracks = [];
				alert(Amplitude.songs.length);
				for (var i = 0; i < Amplitude.songs.length; i++) {
					self.tracks.push(self.getTrackById(Amplitude.songs[i].id));
				}
				self.url = '#';
				break;
			case STATE.TRACKS:
				self.getTracksByCriteria(sort, order, page);
				self.url = '#';
				break;
			case STATE.TRACK:
			case STATE.TRACK_ARTISTS:
			case STATE.TRACK_GENRES:
				self.getTrackById($state.params.id);
				break;
			case STATE.ARTIST:
				self.getTracksByCriteriaExt('artist', $state.params.id, sort, order, 0);
				self.url = 'artist/tracks({id: ' + $state.params.id + ', page: 1})';
				break;
			case STATE.GENRE:
				self.getTracksByCriteriaExt('genre', $state.params.id, sort, order, 0);
				self.url = 'genre/tracks({id: ' + $state.params.id + ', page: 1})';
				break;
			case STATE.ARTIST_TRACKS:
				self.getTracksByCriteriaExt('artist', $state.params.id, sort, order, page);
				self.url = '#';
				break;
			case STATE.GENRE_TRACKS:
				self.getTracksByCriteriaExt('genre', $state.params.id, sort, order, page);
				self.url = '#';
				break;
			case STATE.USER_TRACKS:
				self.getTracksByCriteriaExt('user', $scope.user.id, sort, order, page);
				self.url = '#';
				break;
		}
		PaginationService.getPages(page, state);
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
				Amplitude.init(TrackFactory.parseToAmplitudeSong(self.tracks));
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.getTracksByCriteriaExt = function(relation, id, sort, order, page) {
		TrackFactory.getTracksByCriteriaExt(relation, id, sort, order, page, function(response) {
			if (response.success) {
				self.tracks = response.data;
				Amplitude.init(TrackFactory.parseToAmplitudeSong(self.tracks));
			} else {
				FlashService.error(response.message);
			}
		});
	};

	$scope.showModal = function(video) {
		$scope.video = video;
		$scope.modal = true;
	}

	self.init($state.current.name, $scope.settings.sort.tracks, $scope.settings.order.tracks, $state.params.page);

}]);