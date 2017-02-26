'use strict';
app.constant('REST', (function() {

	var service = '/service';

	return {
		ARTISTS: service + '/artists',
		GENRES: service + '/genres',
		SEARCH: service + '/search',
		TRACKS: service + '/tracks',
		UNITS: service + '/units',
		UPLOAD: service + '/upload',
		USERS: service + '/users'
	}

})());