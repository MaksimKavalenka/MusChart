'use strict';
app.controller('TrackController', function($scope, $state, STATE, UPLOAD, TrackFactory, AmplitudeService, CookieService, FlashService) {

	$scope.design = CookieService.getSettings().design;
	$scope.url = '#';
	$scope.info = {};
	$scope.tracks = [];

	$scope.playSong = function(track) {
		Amplitude.playNow(AmplitudeService.parseSong(track));
	};

	$scope.showModal = function(video) {
		$scope.video = video;
		$scope.modal = true;
	};

	function init(state, sort, order, page) {
		switch (state) {
			case STATE.PLAYLIST:
				$scope.url = '#';
				$scope.tracks = AmplitudeService.playlist;
				break;
			case STATE.TRACKS:
				$scope.url = '#';
				getTracks(sort, order, page);
				break;
			case STATE.TRACK:
			case STATE.TRACK_ARTISTS:
			case STATE.TRACK_GENRES:
				getTrackById($state.params.id);
				break;
			case STATE.ARTIST:
				$scope.url = 'artist_tracks({id: ' + $state.params.id + ', page: 1})';
				getEntityTracks('artist', $state.params.id, sort, order, 0);
				break;
			case STATE.GENRE:
				$scope.url = 'genre_tracks({id: ' + $state.params.id + ', page: 1})';
				getEntityTracks('genre', $state.params.id, sort, order, 0);
				break;
			case STATE.ARTIST_TRACKS:
				$scope.url = '#';
				getEntityTracks('artist', $state.params.id, sort, order, page);
				break;
			case STATE.GENRE_TRACKS:
				$scope.url = '#';
				getEntityTracks('genre', $state.params.id, sort, order, page);
				break;
			case STATE.USER_TRACKS:
				$scope.url = '#';
				getUserTracks(sort, order, page);
				break;
		}
	}

	function getTrackById(id) {
		TrackFactory.getTrackById(id, function(response) {
			if (response.success) {
				$scope.info.image = UPLOAD.TRACK_COVER + '/' + response.data.cover;
				$scope.info.data = response.data.name;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getTracks(sort, order, page) {
		TrackFactory.getTracks(sort, order, page, function(response) {
			if (response.success) {
				$scope.tracks = response.data;
				Amplitude.init(AmplitudeService.parseSongs($scope.tracks));
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getEntityTracks(entity, entityId, sort, order, page) {
		TrackFactory.getEntityTracks(entity, entityId, sort, order, page, function(response) {
			if (response.success) {
				$scope.tracks = response.data;
				Amplitude.init(AmplitudeService.parseSongs($scope.tracks));
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getUserTracks(sort, order, page) {
		TrackFactory.getUserTracks(sort, order, page, function(response) {
			if (response.success) {
				$scope.tracks = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	init($state.current.name, CookieService.getSettings().sort.tracks, CookieService.getSettings().order.tracks, $state.params.page);

});