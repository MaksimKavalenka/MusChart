'use strict';
app.constant('URL', (function() {

	var artistsUrl = '/artists';
	var genresUrl = '/genres';
	var tracksUrl = '/tracks';
	var artistUrl = '/artist';
	var genreUrl = '/genre';
	var trackUrl = '/track';
	var userUrl = '/user';
	var addOperation = '/add';
	var idKey = '{id:[0-9]{1,}}';
	var pageKey = '{page:[0-9]{1,}}';

	return {
		HOME: tracksUrl + '?page=1',
		LOGIN: '/login',
		REGISTER: '/register',
		SETTINGS: '/settings',
		PLAYLIST: '/playlist',
		ARTIST_ADD: artistUrl + addOperation,
		GENRE_ADD: genreUrl + addOperation,
		TRACK_ADD: trackUrl + addOperation,
		ARTISTS: artistsUrl + '?' + pageKey,
		GENRES: genresUrl + '?' + pageKey,
		TRACKS: tracksUrl + '?' + pageKey,
		ARTIST: artistUrl + '/' + idKey,
		GENRE: genreUrl + '/' + idKey,
		TRACK: trackUrl + '/' + idKey,
		GENRE_ARTISTS: genreUrl + '/' + idKey + artistsUrl + '?' + pageKey,
		TRACK_ARTISTS: trackUrl + '/' + idKey + artistsUrl + '?' + pageKey,
		USER_ARTISTS: userUrl + artistsUrl + '?' + pageKey,
		ARTIST_GENRES: artistUrl + '/' + idKey + genresUrl + '?' + pageKey,
		TRACK_GENRES: trackUrl + '/' + idKey + genresUrl + '?' + pageKey,
		USER_GENRES: userUrl + genresUrl + '?' + pageKey,
		ARTIST_TRACKS: artistUrl + '/' + idKey + tracksUrl + '?' + pageKey,
		GENRE_TRACKS: genreUrl + '/' + idKey + tracksUrl + '?' + pageKey,
		USER_TRACKS: userUrl + tracksUrl + '?' + pageKey
	}

})());