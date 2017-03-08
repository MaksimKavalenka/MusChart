'use strict';
app.controller('TrackController', function($scope, $state, $window, DEFAULT, STATE, TEMPLATE, UPLOAD, ArtistFactory, GenreFactory, TrackFactory, UnitFactory, UserFactory, AmplitudeService, ChoiceService, CookieService, FileService, FlashService, UtilityService) {

	$scope.url = '#';
	$scope.info = {};
	$scope.artists = [];
	$scope.genres = [];
	$scope.tracks = [];
	$scope.units = [];
	$scope.artistsChoice = [];
	$scope.genresChoice = [];
	$scope.unitsChoice = [];

	var renew = false;

	$scope.createTrack = function() {
		$scope.dataLoading = true;
		FileService.uploadFile($scope.songFile, TYPE.SONG, function(response) {
			if (response.success) {
				FileService.uploadFile($scope.coverFile, TYPE.COVER, function(response) {
					if (response.success) {
						var artistsId = [];
						var genresId = [];
						var unitsId = [];
						for (var i = 0; i < $scope.artistsChoice.length; i++) {
							artistsId.push($scope.artistsChoice[i].id);
						}
						for (var i = 0; i < $scope.genresChoice.length; i++) {
							genresId.push($scope.genresChoice[i].id);
						}
						for (var i = 0; i < $scope.unitsChoice.length; i++) {
							unitsId.push($scope.unitsChoice[i].id);
						}
						createTrack($scope.track.name, $scope.track.song.replace(/^C:\\fakepath\\/i, ''), $scope.track.cover.replace(/^C:\\fakepath\\/i, ''), $scope.track.video, $scope.track.release, artistsId, unitsId, genresId);
						$scope.dataLoading = false;
					} else {
						$scope.dataLoading = false;
						FlashService.error(response.message);
					}
				});
			} else {
				$scope.dataLoading = false;
				FlashService.error(response.message);
			}
		});
	};

	$scope.deleteTrack = function(id) {
		TrackFactory.deleteTrack(id, function(response) {
			if (response.success) {
				FlashService.success(response.message);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	$scope.like = function(trackId) {
		UserFactory.setUserLike(DEFAULT.ENTITY.track, trackId, function(response) {
			if (response.success) {
				UtilityService.setLike($scope.tracks, trackId);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	$scope.addArtistChoice = function() {
		ChoiceService.addGenreChoice($scope.artistsChoice, $scope.unitsChoice);
	};

	$scope.removeArtistChoice = function(index) {
		ChoiceService.removeGenreChoice($scope.artistsChoice, $scope.unitsChoice, index);
	};

	$scope.addGenreChoice = function() {
		ChoiceService.addGenreChoice($scope.genresChoice);
	};

	$scope.removeGenreChoice = function(index) {
		ChoiceService.removeGenreChoice($scope.genresChoice, index);
	};

	$scope.getTemplate = function() {
		return TEMPLATE.TRACKS[CookieService.getSettings().design];
	};

	$scope.playSong = function(trackId) {
		if (renew) {
			renew = false;
			Amplitude.setSongs(AmplitudeService.parseSongs($scope.tracks));
		}
		Amplitude.playNowById(trackId);
	};

	$scope.openVideo = function(videoId) {
		$window.open('https://www.youtube.com/watch?v=' + videoId);
	};

	function init(state, sort, order, page) {
		switch (state) {
			case STATE.PLAYLIST:
				$scope.url = '#';
				$scope.tracks = AmplitudeService.playlist;
				break;
			case STATE.TRACK_ADD:
				$scope.artistsChoice.push({});
				$scope.genresChoice.push({});
				getAllArtistsIdAndName();
				getAllUnitsIdAndName();
				getAllGenresIdAndName();
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

	function createTrack(name, song, cover, video, release, artistsId, unitsId, genresId) {
		TrackFactory.createTrack(name, song, cover, video, release, artistsId, unitsId, genresId, function(response) {
			if (response.success) {
				FlashService.success(response.message);
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getTrackById(id) {
		TrackFactory.getTrackById(id, function(response) {
			if (response.success) {
				$scope.info.image = UPLOAD.TRACK_COVER + '/' + response.data.cover;
				$scope.info.data = response.data.name;
				$state.current.title = response.data.name;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getTracks(sort, order, page) {
		TrackFactory.getTracks(sort, order, page, function(response) {
			if (response.success) {
				$scope.tracks = response.data;
				Amplitude.init({songs: AmplitudeService.parseSongs($scope.tracks)});
				renew = true;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getEntityTracks(entity, entityId, sort, order, page) {
		TrackFactory.getEntityTracks(entity, entityId, sort, order, page, function(response) {
			if (response.success) {
				$scope.tracks = response.data;
				Amplitude.init({songs: AmplitudeService.parseSongs($scope.tracks)});
				renew = true;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getUserTracks(sort, order, page) {
		TrackFactory.getUserTracks(sort, order, page, function(response) {
			if (response.success) {
				$scope.tracks = response.data;
				Amplitude.init({songs: AmplitudeService.parseSongs($scope.tracks)});
				renew = true;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getAllArtistsIdAndName() {
		ArtistFactory.getAllArtistsIdAndName(function(response) {
			if (response.success) {
				$scope.artists = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getAllUnitsIdAndName() {
		UnitFactory.getAllUnitsIdAndName(function(response) {
			if (response.success) {
				$scope.units = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getAllGenresIdAndName() {
		GenreFactory.getAllGenresIdAndName(function(response) {
			if (response.success) {
				$scope.genres = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	init($state.current.name, CookieService.getSettings().sort.tracks, CookieService.getSettings().order.tracks, $state.params.page);

});