'use strict';
app.config(function($translateProvider) {

	$translateProvider.useSanitizeValueStrategy('escapeParameters');

	$translateProvider
	.translations('by', {
		ARTIST: 'Выканавец',
		GENRE: 'Жанр',
		TRACK: 'Песня',

		ARTISTS: 'Выканаўцы',
		GENRES: 'Жанры',
		TRACKS: 'Песні',

		MY_ACCOUNT: 'Мой рахунак',
		MY_ARTISTS: 'Мае выканаўцы',
		MY_GENRES: 'Мае жанры',
		MY_TRACKS: 'Мае песні',

		MORE_ARTISTS: 'Больш выканаўцаў',
		MORE_GENRES: 'Больш жанраў',
		MORE_TRACKS: 'Больш песен',

		EDIT: 'Рэдагаваць',
		LOG_IN: 'Увайсці',
		LOG_OUT: 'Выйсці',
		REGISTER: 'Рэгістрацыя',
		SAVE: 'Захаваць',
		SETTINGS: 'Налады',
		SUBMIT: 'Адправіць',

		ADD_ARTIST: 'Дадаць выканаўца',
		ADD_GENRE: 'Дадаць жанр',
		REMOVE_ARTIST: 'Выдаліць выканаўца',
		REMOVE_GENRE: 'Выдаліць жанр',

		LANGUAGE: 'Мова',
		SORTING: 'Сартаванне',

		ALPHABET: 'Алфавіт',
		PUBLISH_DATE: 'Дзень публікацыі',
		RATING: 'Рэйтынг',
		RELEASE_DATE: 'Дзень выпуску',

		ASC: 'Узр',
		DESC: 'Змян',

		CONFIRM_PASSWORD: 'Пацвердзіце пароль',
		COVER: 'Вокладка',
		LOGIN: 'Лагін',
		NAME: 'Імя',
		PASSWORD: 'Пароль',
		PHOTO: 'Фота',
		REMEMBER_ME: 'Запомніць мяне',
		SONG_NAME: 'Імя песні',
		YOUTUBE_VIDEO_ID: 'Id Youtube відэа',

		CHECK_GENRE_NAME_MESSAGE: 'Праверка існавання жанру...',
		CHECK_LOGIN_MESSAGE: 'Праверка занятасці лагіну...',
		EXISTS_GENRE_NAME_MESSAGE: 'Дадзены жанр ужо ёсць',
		EXISTS_LOGIN_MESSAGE: 'Дадзены лагін ужо заняты',
		MINIMAL_COUNT_MESSAGE: 'Мінімальная колькасць сімвалаў - 3',
		PASSWORDS_DO_NOT_MATCH_MESSAGE: 'Паролі не супадаюць',

		SAVING_SETTINGS_SUCCESS: 'Налады былі паспяхова захаваны'
	});

});