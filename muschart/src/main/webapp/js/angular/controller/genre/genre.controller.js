'use strict';
app.controller('GenreController', ['$scope', '$state', 'STATE', 'GenreFactory', 'FlashService', 'PaginationService', function($scope, $state, STATE, GenreFactory, FlashService, PaginationService) {
	var self = this;
	self.genres = [];

	self.init = function(state, sort, order, page) {
		switch (state) {
			case STATE.GENRES:
				self.getGenresByCriteria(sort, order, page);
				break;
			case STATE.TRACK_GENRES:
				self.getGenresByCriteriaExt('track', $state.params.id, sort, order, page);
				break;
			case STATE.ARTIST_GENRES:
				self.getGenresByCriteriaExt('artist', $state.params.id, sort, order, page);
				break;
		}
		PaginationService.getPages(page, state);
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

	self.init($state.current.name, $scope.sort.genres, $scope.order.genres, $state.params.page);

}]);