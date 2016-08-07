'use strict';
app.constant('DEFAULT', {
	'COUNT': '15',
	'JSON_EXT': '.json',
	'URL': 'http://localhost:8080/muschart'
});
app.constant('ERROR', {
	'AUTHENTICATION': 'Login or password is wrong',
	'TAKEN_LOGIN': 'This login is already taken'
});
app.constant('TYPE', {
	'COVER': 'cover',
	'PHOTO': 'photo',
	'SONG': 'song'
});
app.constant('PATH', (function() {
	var path = 'muschart/html';
	var contentPath = path + '/content';
	var formPath = path + '/form';
	var titlePath = path + '/title';
	var toolPath = path + '/tool';
	var htmlExt = '.html';
	return {
		ARTIST_CONTENT: contentPath + '/artist' + htmlExt,
		GENRE_CONTENT: contentPath + '/genre' + htmlExt,
		TRACK_CONTENT: contentPath + '/track' + htmlExt,
		LOGIN_FORM: formPath + '/login' + htmlExt,
		REGISTER_FORM: formPath + '/register' + htmlExt,
		ARTIST_EDIT_FORM: formPath + '/artist.edit' + htmlExt,
		GENRE_EDIT_FORM: formPath + '/genre.edit' + htmlExt,
		TRACK_EDIT_FORM: formPath + '/track.edit' + htmlExt,
		AUTHENTICATION_HEADER: titlePath + '/authentication.header' + htmlExt,
		EDIT_HEADER: titlePath + '/edit.header' + htmlExt,
		MAIN_HEADER: titlePath + '/main.header' + htmlExt,
		PAGINATION_TOOL: toolPath + '/pagination' + htmlExt
	}
})());
app.constant('URL', (function() {
	var url = '/muschart';
	var artistsUrl = url + '/artists';
	var genresUrl = url + '/genres';
	var tracksUrl = url + '/tracks';
	var addOperation = '/add';
	var pageOperation = '/page';
	return {
		HOME_PAGE: tracksUrl + pageOperation + '/1',
		LOGIN: url + '/login',
		REGISTER: url + '/register',
		ARTISTS: artistsUrl,
		GENRES: genresUrl,
		TRACKS: tracksUrl,
		ARTISTS_ADD: artistsUrl + addOperation,
		GENRES_ADD: genresUrl + addOperation,
		TRACKS_ADD: tracksUrl + addOperation,
		PAGE_OPERATION: pageOperation,
		PATTERN_PAGE: pageOperation + '/{page:[0-9]{1,}}'
	}
})());