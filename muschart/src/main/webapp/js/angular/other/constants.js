'use strict';
app.constant('CONTROLLER', {
	'CTRL': 'ctrl',
	'ARTIST_CONTROLLER': 'ArtistController',
	'ARTIST_EDIT_CONTROLLER': 'ArtistEditController',
	'GENRE_CONTROLLER': 'GenreController',
	'GENRE_EDIT_CONTROLLER': 'GenreEditController',
	'TRACK_CONTROLLER': 'TrackController',
	'TRACK_EDIT_CONTROLLER': 'TrackEditController',
	'USER_EDIT_CONTROLLER': 'UserEditController'
});
app.constant('MESSAGE', (function() {
	var addingError = 'Error while adding ';
	var gettingError = 'Error while getting ';
	var deletingError = 'Error while deleting ';
	var addingSuccess = ' has been added successfully';
	var deletingSuccess = ' has been deleting successfully';
	return {
		AUTHENTICATION_ERROR: 'Login or password is wrong',
		EXIST_GENRE_ERROR: 'This genre already exists',
		TAKEN_LOGIN_ERROR: 'This login is already taken',
		ADDING_ARTIST_ERROR: addingError + 'artist',
		ADDING_GENRE_ERROR: addingError + 'genre',
		ADDING_TRACK_ERROR: addingError + 'track',
		ADDING_USER_ERROR: addingError + 'user',
		GETTING_ARTIST_ERROR: gettingError + 'artist',
		GETTING_GENRE_ERROR: gettingError + 'genre',
		GETTING_TRACK_ERROR: gettingError + 'track',
		GETTING_UNIT_ERROR: gettingError + 'unit',
		GETTING_USER_ERROR: gettingError + 'user',
		DELETING_ARTIST_ERROR: deletingError + 'artist',
		DELETING_GENRE_ERROR: deletingError + 'genre',
		DELETING_TRACK_ERROR: deletingError + 'track',
		ADDING_ARTIST_SUCCESS: 'Artist ' + addingSuccess,
		ADDING_GENRE_SUCCESS: 'Genre ' + addingSuccess,
		ADDING_TRACK_SUCCESS: 'Track ' + addingSuccess,
		DELETING_ARTIST_SUCCESS: 'Artist ' + deletingSuccess,
		DELETING_GENRE_SUCCESS: 'Genre ' + deletingSuccess,
		DELETING_TRACK_SUCCESS: 'Track ' + deletingSuccess
	}
})());
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
		FOOTER: titlePath + '/footer' + htmlExt,
		PAGINATION_TOOL: toolPath + '/pagination' + htmlExt,
		SETTINGS_TOOL: toolPath + '/settings' + htmlExt
	}
})());
app.constant('REST', (function() {
	var url = '/muschart';
	return {
		JSON_EXT: '.json',
		ARTISTS: url + '/artists',
		GENRES: url + '/genres',
		TRACKS: url + '/tracks',
		UNITS: url + '/units',
		UPLOAD: url + '/upload',
		USERS: url + '/users'
	}
})());
app.constant('STATE', (function() {
	var artists = 'artists';
	var genres = 'genres';
	var tracks = 'tracks';
	var artistOperation = '/artist';
	var genreOperation = '/genre';
	var trackOperation = '/track';
	var addOperation = '/add';
	return {
		SETTINGS: 'settings',
		LOGIN: 'login',
		REGISTER: 'register',
		ARTISTS: artists,
		GENRES: genres,
		TRACKS: tracks,
		TRACK_ARTISTS: artists + trackOperation,
		GENRE_ARTISTS: artists + genreOperation,
		TRACK_GENRES: genres + trackOperation,
		ARTIST_GENRES: genres + artistOperation,
		ARTIST_TRACKS: tracks + artistOperation,
		GENRE_TRACKS: tracks + genreOperation,
		ARTISTS_ADD: artists + addOperation,
		GENRES_ADD: genres + addOperation,
		TRACKS_ADD: tracks + addOperation
	}
})());
app.constant('TYPE', {
	'COVER': 'cover',
	'PHOTO': 'photo',
	'SONG': 'song'
});
app.constant('URL', (function() {
	var url = '/muschart';
	var artistsUrl = url + '/artists';
	var genresUrl = url + '/genres';
	var tracksUrl = url + '/tracks';
	var addOperation = '/add';
	var artistOperation = '/artist';
	var genreOperation = '/genre';
	var pageOperation = '/page';
	var trackOperation = '/track';
	var idPattern = '/{id:[0-9]{1,}}';
	var pagePattern = '/{page:[0-9]{1,}}';
	return {
		HOME_PAGE: tracksUrl + pageOperation + '/1',
		SETTINGS: url + '/settings',
		LOGIN: url + '/login',
		REGISTER: url + '/register',
		ARTISTS: artistsUrl + pageOperation + pagePattern,
		GENRES: genresUrl + pageOperation + pagePattern,
		TRACKS: tracksUrl + pageOperation + pagePattern,
		TRACK_ARTISTS: artistsUrl + trackOperation + idPattern + pageOperation + pagePattern,
		GENRE_ARTISTS: artistsUrl + genreOperation + idPattern + pageOperation + pagePattern,
		TRACK_GENRES: genresUrl + trackOperation + idPattern + pageOperation + pagePattern,
		ARTIST_GENRES: genresUrl + artistOperation + idPattern + pageOperation + pagePattern,
		ARTIST_TRACKS: tracksUrl + artistOperation + idPattern + pageOperation + pagePattern,
		GENRE_TRACKS: tracksUrl + genreOperation + idPattern + pageOperation + pagePattern,
		ARTISTS_ADD: artistsUrl + addOperation,
		GENRES_ADD: genresUrl + addOperation,
		TRACKS_ADD: tracksUrl + addOperation
	}
})());