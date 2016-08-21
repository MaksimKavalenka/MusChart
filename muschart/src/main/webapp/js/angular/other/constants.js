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
	var updatingError = 'Error while updating ';
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
		UPDATING_ARTIST_ERROR: updatingError + 'artist',
		UPDATING_GENRE_ERROR: updatingError + 'genre',
		UPDATING_TRACK_ERROR: updatingError + 'track',
		UPDATING_USER_ERROR: updatingError + 'user',
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
		INFO_CONTENT: contentPath + '/info' + htmlExt,
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
	var artist = 'artist';
	var genre = 'genre';
	var track = 'track';
	var user = 'user';
	var addOperation = '/add';
	return {
		SETTINGS: 'settings',
		LOGIN: 'login',
		REGISTER: 'register',
		ARTISTS: artists,
		GENRES: genres,
		TRACKS: tracks,
		ARTIST: artist,
		GENRE: genre,
		TRACK: track,
		GENRE_ARTISTS: genre + '/' + artists,
		TRACK_ARTISTS: track + '/' + artists,
		USER_ARTISTS: user + '/' + artists,
		ARTIST_GENRES: artist + '/' + genres,
		TRACK_GENRES: track + '/' + genres,
		USER_GENRES: user + '/' + genres,
		ARTIST_TRACKS: artist + '/' + tracks,
		GENRE_TRACKS: genre + '/' + tracks,
		USER_TRACKS: user + '/' + tracks,
		ARTISTS_ADD: artist + addOperation,
		GENRES_ADD: genre + addOperation,
		TRACKS_ADD: track + addOperation
	}
})());
app.constant('TYPE', {
	'COVER': 'cover',
	'PHOTO': 'photo',
	'SONG': 'song'
});
app.constant('URL', (function() {
	var url = '/muschart';
	var artistsUrl = '/artists';
	var genresUrl = '/genres';
	var tracksUrl = '/tracks';
	var artistUrl = '/artist';
	var genreUrl = '/genre';
	var trackUrl = '/track';
	var userUrl = '/user';
	var addOperation = '/add';
	var pageOperation = '/page';
	var idPattern = '/{id:[0-9]{1,}}';
	var pagePattern = '/{page:[0-9]{1,}}';
	return {
		HOME_PAGE: url + tracksUrl + pageOperation + '/1',
		SETTINGS: url + '/settings',
		LOGIN: url + '/login',
		REGISTER: url + '/register',
		ARTISTS: url + artistsUrl + pageOperation + pagePattern,
		GENRES: url + genresUrl + pageOperation + pagePattern,
		TRACKS: url + tracksUrl + pageOperation + pagePattern,
		ARTIST: url + artistUrl + idPattern,
		GENRE: url + genreUrl + idPattern,
		TRACK: url + trackUrl + idPattern,
		GENRE_ARTISTS: url + genreUrl + idPattern + artistsUrl + pageOperation + pagePattern,
		TRACK_ARTISTS: url + trackUrl + idPattern + artistsUrl + pageOperation + pagePattern,
		USER_ARTISTS: url + userUrl + artistsUrl + pageOperation + pagePattern,
		ARTIST_GENRES: url + artistUrl + idPattern + genresUrl + pageOperation + pagePattern,
		TRACK_GENRES: url + trackUrl + idPattern + genresUrl + pageOperation + pagePattern,
		USER_GENRES: url + userUrl + genresUrl + pageOperation + pagePattern,
		ARTIST_TRACKS: url + artistUrl + idPattern + tracksUrl + pageOperation + pagePattern,
		GENRE_TRACKS: url + genreUrl + idPattern + tracksUrl + pageOperation + pagePattern,
		USER_TRACKS: url + userUrl + tracksUrl + pageOperation + pagePattern,
		ARTISTS_ADD: url + artistUrl + addOperation,
		GENRES_ADD: url + genreUrl + addOperation,
		TRACKS_ADD: url + trackUrl + addOperation
	}
})());