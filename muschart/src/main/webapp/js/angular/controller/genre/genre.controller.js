'use strict';
app.controller('GenreController', ['$scope', '$state', 'STATE', 'GenreFactory', 'FlashService', 'PaginationService', function($scope, $state, STATE, GenreFactory, FlashService, PaginationService) {
	var self = this;
	self.url = '#';
	self.info = {data: ''};
	self.genres = [];

	self.init = function(state, sort, order, page) {
		switch (state) {
			case STATE.GENRES:
				self.url = '#';
				self.getGenresByCriteria(sort, order, page);
				break;
			case STATE.GENRE:
			case STATE.GENRE_ARTISTS:
			case STATE.GENRE_TRACKS:
				self.getGenreById($state.params.id);
				break;
			case STATE.ARTIST:
				self.url = 'artist/genres({id: ' + $state.params.id + ', page: 1})';
				self.getGenresByCriteriaExt('artist', $state.params.id, sort, order, 0);
				break;
			case STATE.TRACK:
				self.url = 'track/genres({id: ' + $state.params.id + ', page: 1})';
				self.getGenresByCriteriaExt('track', $state.params.id, sort, order, 0);
				break;
			case STATE.ARTIST_GENRES:
				self.url = '#';
				self.getGenresByCriteriaExt('artist', $state.params.id, sort, order, page);
				break;
			case STATE.TRACK_GENRES:
				self.url = '#';
				self.getGenresByCriteriaExt('track', $state.params.id, sort, order, page);
				break;
			case STATE.USER_GENRES:
				self.url = '#';
				self.getGenresByCriteriaExt('user', $scope.user.id, sort, order, page);
				break;
		}
		PaginationService.getPages(page, state);
	};

	self.getGenreById = function(id) {
		GenreFactory.getGenreById(id, function(response) {
			if (response.success) {
				self.info.data = response.data.name;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.getGenresByCriteria = function(sort, order, page, idTo) {
		GenreFactory.getGenresByCriteria(sort, order, page, function(response) {
			if (response.success) {
				self.genres = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.getGenresByCriteriaExt = function(relation, id, sort, order, page, idTo) {
		GenreFactory.getGenresByCriteriaExt(relation, id, sort, order, page, function(response) {
			if (response.success) {
				self.genres = response.data;
			} else {
				FlashService.error(response.message);
			}
		});
	};

	self.init($state.current.name, $scope.settings.sort.genres, $scope.settings.order.genres, $state.params.page);

}]);