'use strict';
app.controller('ArtistController', function($cookies, $state, STATE, UPLOAD, ArtistFactory, FlashService) {

	var self = this;
	self.url = '#';
	self.info = {};
	self.artists = [];

	function init(state, sort, order, page) {
		switch (state) {
			case STATE.ARTISTS:
				self.url = '#';
				getArtists(sort, order, page);
				break;
			case STATE.ARTIST:
			case STATE.ARTIST_GENRES:
			case STATE.ARTIST_TRACKS:
				getArtistById($state.params.id);
				break;
			case STATE.GENRE:
				self.url = 'genre_artists({id: ' + $state.params.id + ', page: 1})';
				getEntityArtists('genre', $state.params.id, sort, order, 0);
				break;
			case STATE.TRACK:
				self.url = 'track_artists({id: ' + $state.params.id + ', page: 1})';
				getEntityArtists('track', $state.params.id, sort, order, 0);
				break;
			case STATE.GENRE_ARTISTS:
				self.url = '#';
				getEntityArtists('genre', $state.params.id, sort, order, page);
				break;
			case STATE.TRACK_ARTISTS:
				self.url = '#';
				getEntityArtists('track', $state.params.id, sort, order, page);
				break;
			case STATE.USER_ARTISTS:
				self.url = '#';
				getUserArtists(sort, order, page);
				break;
		}
	}

	function getArtistById(id) {
		ArtistFactory.getArtistById(id, function(response) {
			if (response.success) {
				self.info.image = UPLOAD.ARTIST_PHOTO + '/' + response.data.photo;
				self.info.data = response.data.name;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getArtists(sort, order, page) {
		ArtistFactory.getArtists(sort, order, page, function(response) {
			if (response.success) {
				self.artists = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getEntityArtists(entity, entityId, sort, order, page) {
		ArtistFactory.getEntityArtists(entity, entityId, sort, order, page, function(response) {
			if (response.success) {
				self.artists = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function getUserArtists(sort, order, page) {
		ArtistFactory.getUserArtists(sort, order, page, function(response) {
			if (response.success) {
				self.artists = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	}

	init($state.current.name, $cookies.getObject('settings').sort.artists, $cookies.getObject('settings').order.artists, $state.params.page);

});