'use strict';
app.constant('TEMPLATE', (function() {

	var prefix = '/html/content';
	var postfix = '.html';
	var classic = '.classic';
	var standard = '.standard';
	var artists = '/artists';
	var genres = '/genres';
	var tracks = '/tracks';

	return {
		ARTISTS: [prefix + artists + artists + classic + postfix, prefix + artists + artists + standard + postfix],
		GENRES: [prefix + genres + genres + classic + postfix, prefix + genres + genres + standard + postfix],
		TRACKS: [prefix + tracks + tracks + classic + postfix, prefix + tracks + tracks + standard + postfix]
	}

})());