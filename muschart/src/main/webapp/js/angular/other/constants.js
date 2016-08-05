'use strict';
app.constant('DEFAULT', {
	'COUNT': '15',
	'PAGE': '1',
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
app.constant('PATH', {
	'LOGIN_FORM': 'muschart/html/form/login.html',
	'REGISTER_FORM': 'muschart/html/form/register.html',
	'ARTISTS_CONTENT': 'muschart/html/content/artists.html',
	'GENRES_CONTENT': 'muschart/html/content/genres.html',
	'TRACKS_CONTENT': 'muschart/html/content/tracks.html',
	'ARTISTS_EDIT_FORM': 'muschart/html/form/artists.edit.html',
	'GENRES_EDIT_FORM': 'muschart/html/form/genres.edit.html',
	'TRACKS_EDIT_FORM': 'muschart/html/form/tracks.edit.html',
	'AUTHENTICATION_HEADER': 'muschart/html/title/authentication.header.html',
	'EDIT_HEADER': 'muschart/html/title/edit.header.html',
	'MAIN_HEADER': 'muschart/html/title/main.header.html'
});
app.constant('URL', {
	'PAGE': '/{page:[0-9]{1,}}',
	'LOGIN': '/muschart/login',
	'REGISTER': '/muschart/register',
	'ARTISTS': '/muschart/artists',
	'GENRES': '/muschart/genres',
	'TRACKS': '/muschart/tracks',
	'ARTISTS_ADD': '/muschart/artists/add',
	'GENRES_ADD': '/muschart/genres/add',
	'TRACKS_ADD': '/muschart/tracks/add'
});