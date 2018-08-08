'use strict';
app.controller('PaginationController', function($scope, $state, STATE, ArtistFactory, GenreFactory, TrackFactory, FlashService) {

	$scope.pages = [];

	function init(state, page) {
		var callback = function(response) {
			if (response.success) {
				setPages(state, page, response.data);
			} else {
				FlashService.error(response.message);
			}
		};

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
	}

	function setEntityPagesCount(factory, entity, entityId, callback) {
		factory.getEntityPagesCount(entity, entityId, callback);
	}

	function setUserPagesCount(factory, callback) {
		factory.getUserPagesCount(callback);
	}

	function setPages(state, page, count) {
		if (count === 0) {
			return;
		}

		var currentPage = parseInt(page);
		var from = (currentPage <= 3) ? 1 : (currentPage - 2);
		var to = (currentPage <= 3) ? 5 : (currentPage + 2);
		from = (count <= 5) ? 1 : from;
		to = (to <= count) ? to : count;

		for (var i = from; i <= to; i++) {
			var type = 'default';
			if (currentPage == i) {
				type = 'active';
			}
			$scope.pages.push({
				number: i,
				link: state + '({page:' + i + '})',
				type: type
			});
		}
	}

	init($state.current.name, $state.params.page);

});