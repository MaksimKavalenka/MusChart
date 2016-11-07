'use strict';
app.controller('TrackController', function($cookies, $scope, $state, STATE, UPLOAD, TrackFactory, AmplitudeService, FlashService) {

	var self = this;
	self.url = '#';
	self.info = {};
	self.tracks = [];

	self.playSong = function(track) {
		Amplitude.playNow(AmplitudeService.parseSong(track));
	};

	$scope.showModal = function(video) {
		$scope.video = video;
		$scope.modal = true;
	};

	function init(state, sort, order, page) {
		switch (state) {
			case STATE.PLAYLIST:
				self.url = '#';
				self.tracks = AmplitudeService.playlist;
				break;
			case STATE.TRACKS:
				self.url = '#';
				getTracks(sort, order, page);
				break;
			case STATE.TRACK:
			case STATE.TRACK_ARTISTS:
			case STATE.TRACK_GENRES:
				getTrackById($state.params.id);
				break;
			case STATE.ARTIST:
				self.url = 'artist_tracks({id: ' + $state.params.id + ', page: 1})';
				getEntityTracks('artist', $state.params.id, sort, order, 0);
				break;
			case STATE.GENRE:
				self.url = 'genre_tracks({id: ' + $state.params.id + ', page: 1})';
				getEntityTracks('genre', $state.params.id, sort, order, 0);
				break;
			case STATE.ARTIST_TRACKS:
				self.url = '#';
				getEntityTracks('artist', $state.params.id, sort, order, page);
				break;
			case STATE.GENRE_TRACKS:
				self.url = '#';
				getEntityTracks('genre', $state.params.id, sort, order, page);
				break;
			case STATE.USER_TRACKS:
				self.url = '#';
				getUserTracks(sort, order, page);
				break;
		}
	}

	function getTrackById(id) {
		TrackFactory.getTrackById(id, function(response) {
			if (response.success) {
				self.info.image = UPLOAD.TRACK_COVER + '/' + response.data.cover;
				self.info.data = response.data.name;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getTracks(sort, order, page) {
		TrackFactory.getTracks(sort, order, page, function(response) {
			if (response.success) {
				self.tracks = response.data;
				Amplitude.init(AmplitudeService.parseSongs(self.tracks));
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getEntityTracks(entity, entityId, sort, order, page) {
		TrackFactory.getEntityTracks(entity, entityId, sort, order, page, function(response) {
			if (response.success) {
				self.tracks = response.data;
				Amplitude.init(AmplitudeService.parseSongs(self.tracks));
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getUserTracks(sort, order, page) {
		TrackFactory.getUserTracks(sort, order, page, function(response) {
			if (response.success) {
				self.tracks = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	init($state.current.name, $cookies.getObject('settings').sort.tracks, $cookies.getObject('settings').order.tracks, $state.params.page);

});