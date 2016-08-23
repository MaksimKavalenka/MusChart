'use strict';
app.controller('ArtistController', ['$scope', '$state', 'STATE', 'ArtistFactory', 'FlashService', 'PaginationService', function($scope, $state, STATE, ArtistFactory, FlashService, PaginationService) {
	var self = this;
	self.url = '#';
	self.info = {image: '', text: ''};
	self.artists = [];

	self.init = function(state, sort, order, page) {
		switch (state) {
			case STATE.ARTISTS:
				self.getArtistsByCriteria(sort, order, page);
				self.url = '#';
				break;
			case STATE.ARTIST:
			case STATE.ARTIST_GENRES:
			case STATE.ARTIST_TRACKS:
				self.getArtistById($state.params.id);
				break;
			case STATE.GENRE:
				self.getArtistsByCriteriaExt('genre', $state.params.id, sort, order, 0);
				self.url = 'genre/artists({id: ' + $state.params.id + ', page: 1})';
				break;
			case STATE.TRACK:
				self.getArtistsByCriteriaExt('track', $state.params.id, sort, order, 0);
				self.url = 'track/artists({id: ' + $state.params.id + ', page: 1})';
				break;
			case STATE.GENRE_ARTISTS:
				self.getArtistsByCriteriaExt('genre', $state.params.id, sort, order, page);
				self.url = '#';
				break;
			case STATE.TRACK_ARTISTS:
				self.getArtistsByCriteriaExt('track', $state.params.id, sort, order, page);
				self.url = '#';
				break;
			case STATE.USER_ARTISTS:
				self.getArtistsByCriteriaExt('user', $scope.user.id, sort, order, page);
				self.url = '#';
				break;
		}
		PaginationService.getPages(page, state);
	};

	self.getArtistById = function(id) {
		ArtistFactory.getArtistById(id, function(response) {
			if (response.success) {
				self.info.image = '/muschart/img/artist/' + response.data.photo;
				self.info.text = response.data.name;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.getArtistsByCriteria = function(sort, order, page) {
		ArtistFactory.getArtistsByCriteria(sort, order, page, function(response) {
			if (response.success) {
				self.artists = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.getArtistsByCriteriaExt = function(relation, id, sort, order, page) {
		ArtistFactory.getArtistsByCriteriaExt(relation, id, sort, order, page, function(response) {
			if (response.success) {
				self.artists = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.init($state.current.name, $scope.settings.sort.artists, $scope.settings.order.artists, $state.params.page);

}]);