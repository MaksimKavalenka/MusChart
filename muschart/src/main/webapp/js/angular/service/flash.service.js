'use strict';
app.service('FlashService', ['$rootScope', function($rootScope) {
	return {

		clearFlashMessage: function(delay) {
			var flash = $rootScope.flash;
			if (flash) {
				if (!flash.keepAfterLocationChange) {
					setTimeout(function() {
						$rootScope.$apply(function() {
							delete $rootScope.flash;
						});
					}, delay);
				} else {
					flash.keepAfterLocationChange = false;
				}
			}
		},

		success: function(message) {
			$rootScope.flash = {
				message: message,
				type: 'success',
				keepAfterLocationChange: false
			};
			this.clearFlashMessage(3000);
		},

		error: function(message) {
			$rootScope.flash = {
				message: message,
				type: 'error',
				keepAfterLocationChange: false
			};
			this.clearFlashMessage(3000);
		}

	};
}]);