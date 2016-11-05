app.config(function($translateProvider) {

	$translateProvider.useSanitizeValueStrategy('escapeParameters');

	$translateProvider
	.translations('by', {
		ARTISTS: 'Выканаўцы',
		EDIT: 'Рэдагаваць',
		GENRES: 'Жанры',
		LOG_IN: 'Увайсці',
		LOG_OUT: 'Выйсці',
		MY_ACCOUNT: 'Мой рахунак',
		MY_ARTISTS: 'Мае выканаўцы',
		MY_GENRES: 'Мае жанры',
		MY_TRACKS: 'Мае песні',
		SETTINGS: 'Налады',
		TRACKS: 'Песні',
	})

	.translations('en', {
		ARTISTS: 'Artists',
		EDIT: 'Edit',
		GENRES: 'Genres',
		LOG_IN: 'Log in',
		LOG_OUT: 'Log out',
		MY_ACCOUNT: 'My account',
		MY_ARTISTS: 'My artists',
		MY_GENRES: 'My genres',
		MY_TRACKS: 'My tracks',
		SETTINGS: 'Settings',
		TRACKS: 'Tracks',
	})

	.translations('fr', {
		ARTISTS: 'Artistes',
		EDIT: 'Éditer',
		GENRES: 'Genres',
		LOG_IN: 'Connexion',
		LOG_OUT: 'Déconnexion',
		MY_ACCOUNT: 'Mon compte',
		MY_ARTISTS: 'Mes artistes',
		MY_GENRES: 'Mes genres',
		MY_TRACKS: 'Mes chansons',
		SETTINGS: 'Réglages',
		TRACKS: 'Chansons',
	})

	.translations('ru', {
		ARTISTS: 'Исполнители',
		EDIT: 'Редактировать',
		GENRES: 'Жанры',
		LOG_IN: 'Войти',
		LOG_OUT: 'Выйти',
		MY_ACCOUNT: 'Мой аккаунт',
		MY_ARTISTS: 'Мои исполнители',
		MY_GENRES: 'Мои жанры',
		MY_TRACKS: 'Мои песни',
		SETTINGS: 'Настройки',
		TRACKS: 'Песни',
	});

	$translateProvider.preferredLanguage('en');

});