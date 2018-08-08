'use strict';
app.constant('DEFAULT', (function() {

	return {
		COUNT: {
			artists: 6,
			genres: 18,
			tracks: 6
		},

		ENTITY: {
			artist: 'artist',
			genre: 'genre',
			track: 'track'
		},

		SETTINGS: {
			language: 'en',
			design: '1',
			sort: {artists: '1', genres: '0', tracks: '1'},
			order: {artists: 'false', genres: 'true', tracks: 'false'}
		}
	}

})());