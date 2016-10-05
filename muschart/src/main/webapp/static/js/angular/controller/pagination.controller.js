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
				setPageCount(ArtistFactory, callback);
				break;
			case STATE.GENRES:
				setPageCount(GenreFactory, callback);
				break;
			case STATE.TRACKS:
				setPageCount(TrackFactory, callback);
				break;
			case GENRE_ARTISTS:
				setEntityPageCount(ArtistFactory, 'genre', $state.params.id, callback);
				break;
			case TRACK_ARTISTS:
				setEntityPageCount(ArtistFactory, 'track', $state.params.id, callback);
				break;
			case USER_ARTISTS:
				setUserPageCount(ArtistFactory, callback);
				break;
			case ARTIST_GENRES:
				setEntityPageCount(GenreFactory, 'artist', $state.params.id, callback);
				break;
			case TRACK_GENRES:
				setEntityPageCount(GenreFactory, 'track', $state.params.id, callback);
				break;
			case USER_GENRES:
				setUserPageCount(GenreFactory, callback);
				break;
			case ARTIST_TRACKS:
				setEntityPageCount(TrackFactory, 'artist', $state.params.id, callback);
				break;
			case GENRE_TRACKS:
				setEntityPageCount(TrackFactory, 'genre', $state.params.id, callback);
				break;
			case USER_TRACKS:
				setUserPageCount(TrackFactory, callback);
				break;
		}
	}

	function setPageCount(factory, callback) {
		factory.getPageCount(callback);
	};

	function setEntityPageCount(factory, entity, entityId, callback) {
		factory.getEntityPageCount(entity, entityId, callback);
	};

	function setUserPageCount(factory, callback) {
		factory.getUserPageCount(callback);
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