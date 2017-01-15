'use strict';
app.controller('ArtistController', function($scope, $state, DEFAULT, STATE, TEMPLATE, UPLOAD, ArtistFactory, UserFactory, CookieService, FlashService, UtilityService) {

	$scope.url = '#';
	$scope.info = {};
	$scope.artists = [];

	$scope.like = function(artistId) {
		UserFactory.setUserLike(DEFAULT.ENTITY.artist, artistId, function(response) {
			if (response.success) {
				UtilityService.setLike($scope.artists, artistId);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	$scope.getTemplate = function() {
		return TEMPLATE.ARTISTS[CookieService.getSettings().design];
	};

	function init(state, sort, order, page) {
		switch (state) {
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

	init($state.current.name, CookieService.getSettings().sort.artists, CookieService.getSettings().order.artists, $state.params.page);

});