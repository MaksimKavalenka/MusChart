'use strict';
app.service('PaginationService', function($rootScope, STATE, ArtistFactory, GenreFactory, TrackFactory, FlashService) {

	function setPagination(page, state) {
		switch (state) {
			case STATE.ARTISTS:
				setPageCount(ArtistFactory, page, state);
				break;
			case STATE.GENRES:
				setPageCount(GenreFactory, page, state);
				break;
			case STATE.TRACKS:
				setPageCount(TrackFactory, page, state);
				break;
		}
	}

	function setPaginationExt(relation, id, page, state) {
		switch (state) {
			case STATE.GENRE_ARTISTS:
			case STATE.TRACK_ARTISTS:
			case STATE.USER_ARTISTS:
				setPageCountExt(ArtistFactory, relation, id, page, state);
				break;
			case STATE.ARTIST_GENRES:
			case STATE.TRACK_GENRES:
			case STATE.USER_GENRES:
				setPageCountExt(GenreFactory, relation, id, page, state);
				break;
			case STATE.ARTIST_TRACKS:
			case STATE.GENRE_TRACKS:
			case STATE.USER_TRACKS:
				setPageCountExt(TrackFactory, relation, id, page, state);
				break;
		}
	}

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

	function setPageCount(factory, page, state) {
		factory.getPageCount(function(response) {
			if (response.success) {
				getPages(page, state, response.data);
			} else {
				FlashService.error(response.message);
			}
		});
	}

	function setPageCountExt(factory, id, relation, page, state) {
		factory.getPageCountExt(id, relation, function(response) {
			if (response.success) {
				getPages(page, state, response.data);
			} else {
				FlashService.error(response.message);
			}
		});
	}

	return {
		setPagination: setPagination,
		setPaginationExt: setPaginationExt
	};

});