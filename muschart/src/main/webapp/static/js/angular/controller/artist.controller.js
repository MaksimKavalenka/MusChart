'use strict';
app.controller('ArtistController', function($scope, $state, DEFAULT, STATE, TEMPLATE, TYPE, UPLOAD, ArtistFactory, GenreFactory, UserFactory, ChoiceService, CookieService, FileService, FlashService, UtilityService) {

	$scope.url = '#';
	$scope.info = {};
	$scope.artists = [];
	$scope.genres = [];
	$scope.genresChoice = [];

	$scope.createArtist = function() {
		$scope.dataLoading = true;
		FileService.uploadFile($scope.photoFile, TYPE.PHOTO, function(response) {
			if (response.success) {
				var genresId = [];
				for (var i = 0; i < $scope.genresChoice.length; i++) {
					genresId.push($scope.genresChoice[i].id);
				}
				createArtist($scope.artist.name, $scope.artist.photo.replace(/^C:\\fakepath\\/i, ''), genresId);
				$scope.dataLoading = false;
			} else {
				$scope.dataLoading = false;
				FlashService.error(response.message);
			}
		});
	};

	$scope.deleteArtist = function(id) {
		ArtistFactory.deleteArtist(id, function(response) {
			if (response.success) {
				FlashService.success(response.message);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	$scope.like = function(artistId) {
		UserFactory.setUserLike(DEFAULT.ENTITY.artist, artistId, function(response) {
			if (response.success) {
				UtilityService.setLike($scope.artists, artistId);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	$scope.addGenreChoice = function() {
		ChoiceService.addGenreChoice($scope.genresChoice);
	};

	$scope.removeGenreChoice = function(index) {
		ChoiceService.removeGenreChoice($scope.genresChoice, index);
	};

	$scope.getTemplate = function() {
		return TEMPLATE.ARTISTS[CookieService.getSettings().design];
	};

	function init(state, sort, order, page) {
		switch (state) {
			case STATE.ARTIST_ADD:
				$scope.genresChoice.push({});
				getAllGenresIdAndName();
				break;
			case STATE.ARTISTS:
				$scope.url = '#';
				getArtists(sort, order, page);
				break;
			case STATE.ARTIST:
			case STATE.ARTIST_GENRES:
			case STATE.ARTIST_TRACKS:
				getArtistById($state.params.id);
				break;
			case STATE.GENRE:
				$scope.url = 'genre_artists({id: ' + $state.params.id + ', page: 1})';
				getEntityArtists('genre', $state.params.id, sort, order, 0);
				break;
			case STATE.TRACK:
				$scope.url = 'track_artists({id: ' + $state.params.id + ', page: 1})';
				getEntityArtists('track', $state.params.id, sort, order, 0);
				break;
			case STATE.GENRE_ARTISTS:
				$scope.url = '#';
				getEntityArtists('genre', $state.params.id, sort, order, page);
				break;
			case STATE.TRACK_ARTISTS:
				$scope.url = '#';
				getEntityArtists('track', $state.params.id, sort, order, page);
				break;
			case STATE.USER_ARTISTS:
				$scope.url = '#';
				getUserArtists(sort, order, page);
				break;
		}
	}

	function createArtist(name, photo, genresId) {
		ArtistFactory.createArtist(name, photo, genresId, function(response) {
			if (response.success) {
				FlashService.success(response.message);
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getArtistById(id) {
		ArtistFactory.getArtistById(id, function(response) {
			if (response.success) {
				$scope.info.image = UPLOAD.ARTIST_PHOTO + '/' + response.data.photo;
				$scope.info.data = response.data.name;
				$state.current.title = response.data.name;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getArtists(sort, order, page) {
		ArtistFactory.getArtists(sort, order, page, function(response) {
			if (response.success) {
				$scope.artists = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getEntityArtists(entity, entityId, sort, order, page) {
		ArtistFactory.getEntityArtists(entity, entityId, sort, order, page, function(response) {
			if (response.success) {
				$scope.artists = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getUserArtists(sort, order, page) {
		ArtistFactory.getUserArtists(sort, order, page, function(response) {
			if (response.success) {
				$scope.artists = response.data;
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

	init($state.current.name, CookieService.getSettings().sort.artists, CookieService.getSettings().order.artists, $state.params.page);

});