'use strict';
app.controller('AutocompleteController', ['$scope', function($scope) {
	$scope.dirty = {};
	var states = [];

	autocomplete = function(term) {
		var ix = term.lastIndexOf(','),
		lhs = term.substring(0, ix + 1),
		rhs = term.substring(ix + 1),
		suggestions = states(rhs);

		suggestions.forEach(function(s) {
			s.value = lhs + s.value;
		});

		return suggestions;
	};

	$scope.ac_option_delimited = {
		suggest: suggest_state_delimited
	};
}]);