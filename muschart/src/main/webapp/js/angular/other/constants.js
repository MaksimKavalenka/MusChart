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
app.constant('PATH', {
	'ARTIST_CONTENT': 'muschart/html/content/artist.html',
	'GENRE_CONTENT': 'muschart/html/content/genre.html',
	'TRACK_CONTENT': 'muschart/html/content/track.html',
	'LOGIN_FORM': 'muschart/html/form/login.html',
	'REGISTER_FORM': 'muschart/html/form/register.html',
	'AUTHENTICATION_HEADER': 'muschart/html/title/authentication.header.html',
	'EDIT_HEADER': 'muschart/html/title/edit.header.html',
	'MAIN_HEADER': 'muschart/html/title/main.header.html'
});
app.constant('URL', {
	'PAGE': '/{page:[0-9]{1,}}',
	'ARTISTS': '/muschart/artists',
	'GENRES': '/muschart/genres',
	'TRACKS': '/muschart/tracks',
	'LOGIN': '/muschart/login',
	'REGISTER': '/muschart/register'
});