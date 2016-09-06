'use strict';
app.controller('AutocompleteController', ['$scope', 'ArtistFactory', 'GenreFactory', 'TrackFactory', 'FlashService', function ($scope, ArtistFactory, GenreFactory, TrackFactory, FlashService) {

	var artists = [];
	var genres = [];
	var tracks = [];
	var models = [];

	ArtistFactory.getAllArtists(function(response) {
		if (response.success) {
			artists = response.data;
			models = models.concat(response.data);
		} else {
			FlashService.error(response.message);
		}
	});
	GenreFactory.getAllGenres(function(response) {
		if (response.success) {
			genres = response.data;
			models = models.concat(response.data);
		} else {
			FlashService.error(response.message);
		}
	});
	TrackFactory.getAllTracks(function(response) {
		if (response.success) {
			tracks = response.data;
			models = models.concat(response.data);
		} else {
			FlashService.error(response.message);
		}
	});

	function highlight(str, term) {
		var highlightRegex = new RegExp('(' + term + ')', 'gi');
		return str.replace(highlightRegex, '<div class="ac-highlight">$1</div>');
	};

	function suggestNames(term) {
		var q = term.toLowerCase().trim();
		var results = [];
		for (var i = 0; i < models.length && results.length < 10; i++) {
			var model = models[i];
			if (model.name.toLowerCase().indexOf(q) >= 0) {
				var info = '';
				var url = '';
				if (artists.includes(model)) {
					info = 'Artist';
					url = 'artist({id: ' + model.id + '})';
				} else if (genres.includes(model)) {
					info = 'Genre';
					url = 'genre({id: ' + model.id + '})';
				} else if (tracks.includes(model)) {
					info = 'Track';
					url = 'track({id: ' + model.id + '})';
				}
				results.push({
					info: info,
					value: highlight(model.name, term),
					url: url
				});
			}
		}
		return results;
	}

	$scope.options = {
		suggest: suggestNames
	};

}]);