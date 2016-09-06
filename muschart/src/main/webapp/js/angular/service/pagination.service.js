'use strict';
app.service('PaginationService', ['$rootScope', 'STATE', 'ArtistFactory', 'GenreFactory', 'TrackFactory', 'UserFactory', 'FlashService', function($rootScope, STATE, ArtistFactory, GenreFactory, TrackFactory, UserFactory, FlashService) {

	function setPagination(page, state) {
		switch (state) {
			case STATE.ARTISTS:
				setPageAmount(ArtistFactory, page, state);
				break;
			case STATE.GENRES:
				setPageAmount(GenreFactory, page, state);
				break;
			case STATE.TRACKS:
				setPageAmount(TrackFactory, page, state);
				break;
		}
	};

	function setPaginationExt(id, relation, page, state) {
		switch (state) {
			case STATE.ARTIST_GENRES:
			case STATE.ARTIST_TRACKS:
				setPageAmountExt(ArtistFactory, id, relation, page, state);
				break;
			case STATE.GENRE_ARTISTS:
			case STATE.GENRE_TRACKS:
				setPageAmountExt(GenreFactory, id, relation, page, state);
				break;
			case STATE.TRACK_ARTISTS:
			case STATE.TRACK_GENRES:
				setPageAmountExt(TrackFactory, id, relation, page, state);
				break;
			case STATE.USER_ARTISTS:
			case STATE.USER_GENRES:
			case STATE.USER_TRACKS:
				setPageAmountExt(UserFactory, id, relation, page, state);
				break;
		}
	};

	function getPages(page, state, amount) {
		$rootScope.pages = [];
		var from = ((page <= 3) || (amount < 5)) ? 1 : (page - 2);
		var to = ((from + 5) > amount) ? (amount + 1) : (from + 5);
		to = (page <= amount) ? to : (from + 5);
		for (var i = from; i < to; i++) {
			var type = 'default';
			if (page == i) {
				type = 'active';
			}
			$rootScope.pages.push({
				number: i,
				link: state + '({page:' + i + '})',
				type: type
			});
		}
		if (amount == 1) {
			$rootScope.pages = [];
		}
	}

	function setPageAmount(factory, page, state) {
		factory.getPageAmount(function(response) {
			if (response.success) {
				getPages(page, state, response.data);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	function setPageAmountExt(factory, id, relation, page, state) {
		factory.getPageAmountExt(id, relation, function(response) {
			if (response.success) {
				getPages(page, state, response.data);
			} else {
				FlashService.error(response.message);
			}
		});
	};

	return {
		setPagination: setPagination,
		setPaginationExt: setPaginationExt
	};

}]);