'use strict';
app.constant('CONTROLLER', (function() {
	return {
		CTRL: 'ctrl',
		ARTIST_CONTROLLER: 'ArtistController',
		ARTIST_EDIT_CONTROLLER: 'ArtistEditController',
		GENRE_CONTROLLER: 'GenreController',
		GENRE_EDIT_CONTROLLER: 'GenreEditController',
		PAGINATION_CONTROLLER: 'PaginationController',
		SETTINGS_CONTROLLER: 'SettingsController',
		TRACK_CONTROLLER: 'TrackController',
		TRACK_EDIT_CONTROLLER: 'TrackEditController',
		USER_EDIT_CONTROLLER: 'UserEditController'
	}
})());