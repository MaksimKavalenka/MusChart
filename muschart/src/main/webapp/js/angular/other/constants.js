'use strict';
app.constant('DEFAULT', {
	'COUNT': '15',
	'JSON_EXT': '.json',
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
	'ARTIST_CONTENT': 'muschart/html/content/artist.html',
	'GENRE_CONTENT': 'muschart/html/content/genre.html',
	'TRACK_CONTENT': 'muschart/html/content/track.html',
	'ARTIST_EDIT_FORM': 'muschart/html/form/artist.edit.html',
	'GENRE_EDIT_FORM': 'muschart/html/form/genre.edit.html',
	'TRACK_EDIT_FORM': 'muschart/html/form/track.edit.html',
	'AUTHENTICATION_HEADER': 'muschart/html/title/authentication.header.html',
	'EDIT_HEADER': 'muschart/html/title/edit.header.html',
	'MAIN_HEADER': 'muschart/html/title/main.header.html',
	'PAGINATION_TOOL': 'muschart/html/tool/pagination.html'
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