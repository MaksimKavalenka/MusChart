'use strict';
app.config(function($translateProvider) {

	$translateProvider.useSanitizeValueStrategy('escapeParameters');

	$translateProvider
	.translations('en', {
		ARTIST: 'Artist',
		GENRE: 'Genre',
		TRACK: 'Track',

		ARTISTS: 'Artists',
		GENRES: 'Genres',
		TRACKS: 'Tracks',

		MY_ACCOUNT: 'My account',
		MY_ARTISTS: 'My artists',
		MY_GENRES: 'My genres',
		MY_TRACKS: 'My tracks',

		MORE_ARTISTS: 'More artists',
		MORE_GENRES: 'More genres',
		MORE_TRACKS: 'More tracks',

		EDIT: 'Edit',
		LOG_IN: 'Log in',
		LOG_OUT: 'Log out',
		REGISTER: 'Register',
		SAVE: 'Save',
		SETTINGS: 'Settings',
		SUBMIT: 'Submit',

		ADD_ARTIST: 'Add artist',
		ADD_GENRE: 'Add genre',
		REMOVE_ARTIST: 'Remove artist',
		REMOVE_GENRE: 'Remove genre',

		LANGUAGE: 'Language',
		SORTING: 'Sorting',

		ALPHABET: 'Alphabet',
		PUBLISH_DATE: 'Publish date',
		RATING: 'Rating',
		RELEASE_DATE: 'Release date',

		ASC: 'By ascending',
		DESC: 'By descending',

		CONFIRM_PASSWORD: 'Confirm the password',
		COVER: 'Cover',
		LOGIN: 'Login',
		NAME: 'Name',
		PASSWORD: 'Password',
		PHOTO: 'Photo',
		REMEMBER_ME: 'Remember me',
		SONG_NAME: 'Song name',
		YOUTUBE_VIDEO_ID: 'Youtube video id',

		CHECK_GENRE_NAME_MESSAGE: 'Checking if this genre exists...',
		CHECK_LOGIN_MESSAGE: 'Checking if this login is available...',
		EXISTS_GENRE_NAME_MESSAGE: 'This genre already exists',
		EXISTS_LOGIN_MESSAGE: 'This login is already taken',
		MINIMAL_COUNT_MESSAGE: 'Minimum count of symbols is 3',
		PASSWORDS_DO_NOT_MATCH_MESSAGE: 'Passwords do not match',
		WRONG_DATA: 'Login or password is wrong',

		SAVING_SETTINGS_SUCCESS: 'Settings were successfully saved'
	});

});