'use strict';
app.constant('STATE', (function() {
	var delimiter = '_';
	var artists = 'artists';
	var genres = 'genres';
	var tracks = 'tracks';
	var artist = 'artist';
	var genre = 'genre';
	var track = 'track';
	var user = 'user';
	var addOperation = delimiter + 'add';
	return {
		LOGIN: 'login',
		REGISTER: 'register',
		SETTINGS: 'settings',
		PLAYLIST: 'playlist',
		ARTIST_ADD: artist + addOperation,
		GENRE_ADD: genre + addOperation,
		TRACK_ADD: track + addOperation,
		ARTISTS: artists,
		GENRES: genres,
		TRACKS: tracks,
		ARTIST: artist,
		GENRE: genre,
		TRACK: track,
		GENRE_ARTISTS: genre + delimiter + artists,
		TRACK_ARTISTS: track + delimiter + artists,
		USER_ARTISTS: user + delimiter + artists,
		ARTIST_GENRES: artist + delimiter + genres,
		TRACK_GENRES: track + delimiter + genres,
		USER_GENRES: user + delimiter + genres,
		ARTIST_TRACKS: artist + delimiter + tracks,
		GENRE_TRACKS: genre + delimiter + tracks,
		USER_TRACKS: user + delimiter + tracks
	}
})());