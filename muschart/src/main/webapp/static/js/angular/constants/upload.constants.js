'use strict';
app.constant('UPLOAD', (function() {

	var imageRoot = '/image';

	return {
		ARTIST_PHOTO: imageRoot + '/artist',
		AUDIO: '/audio',
		TRACK_COVER: imageRoot + '/track'
	}

})());