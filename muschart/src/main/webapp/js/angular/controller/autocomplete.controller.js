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

	function suggestNames(term) {
		var q = term.toLowerCase().trim();
		var results = [];
		for (var i = 0; i < models.length && results.length < 10; i++) {
			var model = models[i];
			if (model.name.toLowerCase().indexOf(q) === 0) {
				var url = '';
				if (artists.includes(model)) {
					url = 'artist({id: ' + model.id + '})';
				} else if (genres.includes(model)) {
					url = 'genre({id: ' + model.id + '})';
				} else if (tracks.includes(model)) {
					url = 'track({id: ' + model.id + '})';
				}
				results.push({
					label: model.name,
					value: model.name,
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