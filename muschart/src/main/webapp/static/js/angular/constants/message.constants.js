'use strict';
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
		DELETING_TRACK_SUCCESS: 'Track ' + deletingSuccess,
		VALIDATION_ERROR: 'All required attributes must be filled'
	}
})());