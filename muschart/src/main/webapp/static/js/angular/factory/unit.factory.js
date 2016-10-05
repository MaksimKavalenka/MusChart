'use strict';
app.factory('UnitFactory', ['$http', 'MESSAGE', 'REST', function($http, MESSAGE, REST) {

	function getAllUnitsIdAndName(callback) {
		$http.get(REST.UNITS + '/all/id/name' + REST.JSON_EXT)
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
		getAllUnitsIdAndName: getAllUnitsIdAndName
	};

}]);