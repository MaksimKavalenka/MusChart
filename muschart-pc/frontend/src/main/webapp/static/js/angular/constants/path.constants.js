'use strict';
app.constant('PATH', (function() {

	var path = 'html';
	var contentPath = path + '/content';
	var formPath = path + '/form';
	var titlePath = path + '/title';
	var toolPath = path + '/tool';
	var htmlExt = '.html';

	return {
		INFO_CONTENT: contentPath + '/info' + htmlExt,
		ARTISTS_CONTENT: contentPath + '/artists' + htmlExt,
		GENRES_CONTENT: contentPath + '/genres' + htmlExt,
		TRACKS_CONTENT: contentPath + '/tracks' + htmlExt,
		LOGIN_FORM: formPath + '/login' + htmlExt,
		REGISTER_FORM: formPath + '/register' + htmlExt,
		ARTIST_EDIT_FORM: formPath + '/artist.edit' + htmlExt,
		GENRE_EDIT_FORM: formPath + '/genre.edit' + htmlExt,
		TRACK_EDIT_FORM: formPath + '/track.edit' + htmlExt,
		AUTHENTICATION_HEADER: titlePath + '/authentication.header' + htmlExt,
		EDIT_HEADER: titlePath + '/edit.header' + htmlExt,
		MAIN_HEADER: titlePath + '/main.header' + htmlExt,
		FOOTER: titlePath + '/footer' + htmlExt,
		PAGINATION_TOOL: toolPath + '/pagination' + htmlExt,
		SETTINGS_TOOL: toolPath + '/settings' + htmlExt
	}

})());