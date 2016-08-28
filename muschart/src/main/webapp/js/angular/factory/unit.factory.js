'use strict';
app.factory('UnitFactory', ['$http', 'MESSAGE', 'REST', function($http, MESSAGE, REST) {

	function getAllUnits(callback) {
		return $http.get(REST.UNITS + REST.JSON_EXT)
		.success(function(response) {
			var data = {success: true, data: response};
			callback(data);
		})
		.error(function(response) {
			response = {success: false, message: MESSAGE.GETTING_UNIT_ERROR};
			callback(response);
		});
	}

	return {
		getAllUnits: getAllUnits
	};
}]);