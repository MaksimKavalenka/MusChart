'use strict';
app.service('ChoiceService', function() {

	function addArtistChoice(artistsChoice, unitsChoice) {
		artistsChoice.push({});
		if (artistsChoice.length > 1) {
			unitsChoice.push({});
		}
	}

	function removeArtistChoice(artistsChoice, unitsChoice, index) {
		if (artistsChoice.length > 1) {
			unitsChoice.splice(index - 1, 1);
		}
		artistsChoice.splice(index, 1);
	}

	function addGenreChoice(genresChoice) {
		genresChoice.push({});
	}

	function removeGenreChoice(genresChoice, index) {
		genresChoice.splice(index, 1);
	}

	return {
		addArtistChoice: addArtistChoice,
		removeArtistChoice: removeArtistChoice,
		addGenreChoice: addGenreChoice,
		removeGenreChoice: removeGenreChoice
	};

});