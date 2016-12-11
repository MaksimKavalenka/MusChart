'use strict';
app.factory('UnitFactory', function($http, MESSAGE, REST) {

	function getAllUnitsIdAndName(callback) {
		$http.get(REST.UNITS + '/get/all/id_name')
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

});