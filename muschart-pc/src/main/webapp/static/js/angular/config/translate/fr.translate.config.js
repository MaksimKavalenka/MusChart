'use strict';
app.config(function($translateProvider) {

	$translateProvider.useSanitizeValueStrategy('escapeParameters');

	$translateProvider
	.translations('fr', {
		ARTISTS: 'Artiste',
		GENRES: 'Genre',
		TRACKS: 'Chanson',

		ARTISTS: 'Artistes',
		GENRES: 'Genres',
		TRACKS: 'Chansons',

		MY_ACCOUNT: 'Mon compte',
		MY_ARTISTS: 'Mes artistes',
		MY_GENRES: 'Mes genres',
		MY_TRACKS: 'Mes chansons',

		MORE_ARTISTS: 'Plus de artistes',
		MORE_GENRES: 'Plus de genres',
		MORE_TRACKS: 'Plus de chansons',

		EDIT: 'Éditer',
		LOG_IN: 'Connexion',
		LOG_OUT: 'Déconnexion',
		REGISTER: 'Registre',
		SAVE: 'Enregistrer',
		SETTINGS: 'Réglages',
		SUBMIT: 'Soumettre',

		ADD_ARTIST: 'Ajouter un artiste',
		ADD_GENRE: 'Ajouter un genre',
		REMOVE_ARTIST: 'Supprimer artiste',
		REMOVE_GENRE: 'Supprimer le genre',

		LANGUAGE: 'Langue',
		DESIGN: 'Désign',
		SORTING: 'Tri',

		CLASSIC: 'Classique',
		STANDARD: 'Standard',

		ALPHABET: 'Alphabet',
		PUBLISH_DATE: 'Date de publication',
		RATING: 'Audimat',
		RELEASE_DATE: 'Date de sortie',

		ASC: 'En montant',
		DESC: 'En descendant',

		CONFIRM_PASSWORD: 'Confirmer le mot de passe',
		COVER: 'Couverture',
		LOGIN: 'Login',
		NAME: 'Nom',
		PASSWORD: 'Mot de passe',
		PHOTO: 'Photo',
		REMEMBER_ME: 'Souviens-toi de moiе',
		SONG_NAME: 'Nom de chanson',
		YOUTUBE_VIDEO_ID: 'Youtube video id',

		CHECK_GENRE_NAME_MESSAGE: 'Vérifier si ce genre existe...',
		CHECK_LOGIN_MESSAGE: 'Vérification de la disponibilité de cette login...',
		EXISTS_GENRE_NAME_MESSAGE: 'Ce genre existe déjà',
		EXISTS_LOGIN_MESSAGE: 'Cette login est déjà prise',
		MINIMAL_COUNT_MESSAGE: 'Le nombre minimum de symboles est de 3',
		PASSWORDS_DO_NOT_MATCH_MESSAGE: 'Les mots de passe ne correspondent pas',
		WRONG_DATA: 'Connexion ou mot de passe incorrect',

		SAVING_SETTINGS_SUCCESS: 'Les paramètres ont été correctement enregistrés'
	});

});