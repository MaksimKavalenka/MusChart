'use strict';
app.controller('ArtistController', ['$scope', '$state', 'STATE', 'ArtistFactory', 'FlashService', 'PaginationService', function($scope, $state, STATE, ArtistFactory, FlashService, PaginationService) {
	var self = this;
	self.artists = [];

	self.init = function(state, sort, order, page) {
		switch (state) {
			case STATE.ARTISTS:
				self.getArtistsByCriteria(sort, order, page);
				break;
			case STATE.GENRE:
				self.getArtistsByCriteriaExt('genre', $state.params.id, sort, order, 0);
				break;
			case STATE.TRACK:
				self.getArtistsByCriteriaExt('track', $state.params.id, sort, order, 0);
				break;
			case STATE.GENRE_ARTISTS:
				self.getArtistsByCriteriaExt('genre', $state.params.id, sort, order, page);
				break;
			case STATE.TRACK_ARTISTS:
				self.getArtistsByCriteriaExt('track', $state.params.id, sort, order, page);
				break;
			case STATE.USER_ARTISTS:
				self.getArtistsByCriteriaExt('user', $scope.globals.user.id, sort, order, page);
				break;
		}
		PaginationService.getPages(page, state);
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

	self.init($state.current.name, $scope.sort.artists, $scope.order.artists, $state.params.page);

}]);