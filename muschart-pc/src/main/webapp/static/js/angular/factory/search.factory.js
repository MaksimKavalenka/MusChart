'use strict';
app.factory('SearchFactory', function($http, MESSAGE, REST, UtilityService) {

	function getSuggestions(query, callback) {
		if (!UtilityService.allNotEmpty(callback, query)) {
			return;
		}

		$http.post(REST.SEARCH + '/suggest', query)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_RESULTS_ERROR};
			callback(response);
		});
	}

	return {
		getSuggestions: getSuggestions
	};

});