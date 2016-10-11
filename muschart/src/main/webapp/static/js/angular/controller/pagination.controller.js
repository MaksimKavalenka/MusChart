'use strict';
app.controller('PaginationController', ['$rootScope', '$state', 'STATE', 'ArtistFactory', 'GenreFactory', 'TrackFactory', 'FlashService', function($rootScope, $state, STATE, ArtistFactory, GenreFactory, TrackFactory, FlashService) {

	function init(state, page) {
		var callback = function(response) {
			if (response.success) {
				setPages(state, page, response.data);
			} else {
				FlashService.error(response.message);
			}
		}
		switch (state) {
			case STATE.ARTISTS:
				setPagesCount(ArtistFactory, callback);
				break;
			case STATE.GENRES:
				setPagesCount(GenreFactory, callback);
				break;
			case STATE.TRACKS:
				setPagesCount(TrackFactory, callback);
				break;
			case STATE.GENRE_ARTISTS:
				setEntityPagesCount(ArtistFactory, 'genre', $state.params.id, callback);
				break;
			case STATE.TRACK_ARTISTS:
				setEntityPagesCount(ArtistFactory, 'track', $state.params.id, callback);
				break;
			case STATE.USER_ARTISTS:
				setUserPagesCount(ArtistFactory, callback);
				break;
			case STATE.ARTIST_GENRES:
				setEntityPagesCount(GenreFactory, 'artist', $state.params.id, callback);
				break;
			case STATE.TRACK_GENRES:
				setEntityPagesCount(GenreFactory, 'track', $state.params.id, callback);
				break;
			case STATE.USER_GENRES:
				setUserPagesCount(GenreFactory, callback);
				break;
			case STATE.ARTIST_TRACKS:
				setEntityPagesCount(TrackFactory, 'artist', $state.params.id, callback);
				break;
			case STATE.GENRE_TRACKS:
				setEntityPagesCount(TrackFactory, 'genre', $state.params.id, callback);
				break;
			case STATE.USER_TRACKS:
				setUserPagesCount(TrackFactory, callback);
				break;
		}
	}

	function setPagesCount(factory, callback) {
		factory.getPagesCount(callback);
	};

	function setEntityPagesCount(factory, entity, entityId, callback) {
		factory.getEntityPagesCount(entity, entityId, callback);
	};

	function setUserPagesCount(factory, callback) {
		factory.getUserPagesCount(callback);
	};

	function setPages(state, page, count) {
		$rootScope.pages = [];
		if (count === 0) {
			count = 1;
		}
		var from = ((page <= 3) || (count < 5)) ? 1 : (page - 2);
		var to = ((from + 5) > count) ? (count + 1) : (from + 5);
		to = (page <= count) ? to : (from + 5);
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
		if (count === 1) {
			$rootScope.pages = [];
		}
	};

	init($state.current.name, $state.params.page);

}]);