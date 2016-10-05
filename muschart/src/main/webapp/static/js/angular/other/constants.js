'use strict';
app.constant('CONTROLLER', (function() {
	return {
		CTRL: 'ctrl',
		ARTIST_CONTROLLER: 'ArtistController',
		ARTIST_EDIT_CONTROLLER: 'ArtistEditController',
		GENRE_CONTROLLER: 'GenreController',
		GENRE_EDIT_CONTROLLER: 'GenreEditController',
		PAGINATION_CONTROLLER: 'PaginationController',
		TRACK_CONTROLLER: 'TrackController',
		TRACK_EDIT_CONTROLLER: 'TrackEditController',
		USER_EDIT_CONTROLLER: 'UserEditController'
	}
})());

app.constant('MESSAGE', (function() {
	var creatingError = 'Error while creating ';
	var updatingError = 'Error while updating ';
	var deletingError = 'Error while deleting ';
	var gettingError = 'Error while getting ';
	var creatingSuccess = ' has been created successfully';
	var deletingSuccess = ' has been deleted successfully';
	return {
		AUTHENTICATION_ERROR: 'Login or password is wrong',
		EXIST_GENRE_ERROR: 'This genre already exists',
		TAKEN_LOGIN_ERROR: 'This login is already taken',
		SAVING_FILE_ERROR: 'Error while saving file',
		CREATING_ARTIST_ERROR: creatingError + 'artist',
		CREATING_GENRE_ERROR: creatingError + 'genre',
		CREATING_TRACK_ERROR: creatingError + 'track',
		CREATING_USER_ERROR: creatingError + 'user',
		UPDATING_ARTIST_ERROR: updatingError + 'artist',
		UPDATING_GENRE_ERROR: updatingError + 'genre',
		UPDATING_TRACK_ERROR: updatingError + 'track',
		UPDATING_USER_ERROR: updatingError + 'user',
		DELETING_ARTIST_ERROR: deletingError + 'artist',
		DELETING_GENRE_ERROR: deletingError + 'genre',
		DELETING_TRACK_ERROR: deletingError + 'track',
		GETTING_ARTIST_ERROR: gettingError + 'artist',
		GETTING_GENRE_ERROR: gettingError + 'genre',
		GETTING_TRACK_ERROR: gettingError + 'track',
		GETTING_UNIT_ERROR: gettingError + 'unit',
		GETTING_USER_ERROR: gettingError + 'user',
		CREATING_ARTIST_SUCCESS: 'Artist ' + creatingSuccess,
		CREATING_GENRE_SUCCESS: 'Genre ' + creatingSuccess,
		CREATING_TRACK_SUCCESS: 'Track ' + creatingSuccess,
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
		SETTINGS: 'settings',
		LOGIN: 'login',
		REGISTER: 'register',
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

app.constant('TITLE', (function() {
	return {
		LOGIN: 'Login',
		REGISTER: 'Register',
		SETTINGS: 'Settings',
		PLAYLIST: 'Playlist',
		ARTISTS: 'Artists',
		GENRES: 'Genres',
		TRACKS: 'Tracks',
		USER_ARTISTS: 'My artists',
		USER_GENRES: 'My genres',
		USER_TRACKS: 'My tracks'
	}
})());

app.constant('TYPE', (function() {
	return {
		COVER: 'cover',
		PHOTO: 'photo',
		SONG: 'song'
	}
})());

app.constant('UPLOAD', (function() {
	var root = '/muschart';
	var imageRoot = root + '/image';
	return {
		ARTIST_PHOTO: imageRoot + '/artist',
		AUDIO: root + '/audio',
		TRACK_COVER: imageRoot + '/track'
	}
})());

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
	var idKey = '{id:[0-9]{1,}}';
	var pageKey = '{page:[0-9]{1,}}';
	return {
		HOME: url + tracksUrl + '?page=1',
		LOGIN: url + '/login',
		REGISTER: url + '/register',
		SETTINGS: url + '/settings',
		PLAYLIST: url + '/playlist',
		ARTIST_ADD: url + artistUrl + addOperation,
		GENRE_ADD: url + genreUrl + addOperation,
		TRACK_ADD: url + trackUrl + addOperation,
		ARTISTS: url + artistsUrl + '?' + pageKey,
		GENRES: url + genresUrl + '?' + pageKey,
		TRACKS: url + tracksUrl + '?' + pageKey,
		ARTIST: url + artistUrl + '/' + idKey,
		GENRE: url + genreUrl + '/' + idKey,
		TRACK: url + trackUrl + '/' + idKey,
		GENRE_ARTISTS: url + genreUrl + '/' + idKey + artistsUrl + '?' + pageKey,
		TRACK_ARTISTS: url + trackUrl + '/' + idKey + artistsUrl + '?' + pageKey,
		USER_ARTISTS: url + userUrl + artistsUrl + '?' + pageKey,
		ARTIST_GENRES: url + artistUrl + '/' + idKey + genresUrl + '?' + pageKey,
		TRACK_GENRES: url + trackUrl + '/' + idKey + genresUrl + '?' + pageKey,
		USER_GENRES: url + userUrl + genresUrl + '?' + pageKey,
		ARTIST_TRACKS: url + artistUrl + '/' + idKey + tracksUrl + '?' + pageKey,
		GENRE_TRACKS: url + genreUrl + '/' + idKey + tracksUrl + '?' + pageKey,
		USER_TRACKS: url + userUrl + tracksUrl + '?' + pageKey
	}
})());