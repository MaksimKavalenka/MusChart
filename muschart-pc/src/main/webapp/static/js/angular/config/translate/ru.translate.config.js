'use strict';
app.config(function($translateProvider) {

	$translateProvider.useSanitizeValueStrategy('escapeParameters');

	$translateProvider
	.translations('ru', {
		ARTIST: 'Исполнитель',
		GENRE: 'Жанр',
		TRACK: 'Песня',

		ARTISTS: 'Исполнители',
		GENRES: 'Жанры',
		TRACKS: 'Песни',

		MY_ACCOUNT: 'Мой аккаунт',
		MY_ARTISTS: 'Мои исполнители',
		MY_GENRES: 'Мои жанры',
		MY_TRACKS: 'Мои песни',

		MORE_ARTISTS: 'Больше исполнителей',
		MORE_GENRES: 'Больше жанров',
		MORE_TRACKS: 'Больше песен',

		EDIT: 'Редактировать',
		LOG_IN: 'Войти',
		LOG_OUT: 'Выйти',
		REGISTER: 'Регистрация',
		SAVE: 'Сохранить',
		SETTINGS: 'Настройки',
		SUBMIT: 'Выполнить',

		ADD_ARTIST: 'Добавить артиста',
		ADD_GENRE: 'Добавить жанр',
		REMOVE_ARTIST: 'Удалить артиста',
		REMOVE_GENRE: 'Удалить жанр',

		LANGUAGE: 'Язык',
		SORTING: 'Сортировка',

		ALPHABET: 'Алфавит',
		PUBLISH_DATE: 'Дата публикации',
		RATING: 'Рейтинг',
		RELEASE_DATE: 'Дата выпуска',

		ASC: 'По возрастанию',
		DESC: 'По убыванию',

		CONFIRM_PASSWORD: 'Подтвердите пароль',
		COVER: 'Обложка',
		LOGIN: 'Логин',
		NAME: 'Имя',
		PASSWORD: 'Пароль',
		PHOTO: 'Фото',
		REMEMBER_ME: 'Запомнить меня',
		SONG_NAME: 'Имя песни',
		YOUTUBE_VIDEO_ID: 'Id Youtube видео',

		CHECK_GENRE_NAME_MESSAGE: 'Проверка существования жанра...',
		CHECK_LOGIN_MESSAGE: 'Проверка занятости логина...',
		EXISTS_GENRE_NAME_MESSAGE: 'Данный жанр уже есть',
		EXISTS_LOGIN_MESSAGE: 'Данный логин уже занят',
		MINIMAL_COUNT_MESSAGE: 'Минимальное количество символов - 3',
		PASSWORDS_DO_NOT_MATCH_MESSAGE: 'Пароли не совпадают',
		WRONG_DATA: 'Неправильный логин или пароль',

		SAVING_SETTINGS_SUCCESS: 'Настройки были успешно сохранены'
	});

});