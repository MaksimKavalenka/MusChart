'use strict';
app.directive('checklogin', ['$q', '$timeout', 'UserFactory', function($q, $timeout, UserFactory) {
	var timer;
	return {

		require: 'ngModel',
		link: function(scope, elm, attrs, ctrl) {
			ctrl.$asyncValidators.checklogin = function(modelValue, viewValue) {
				var def = $q.defer();
				$timeout.cancel(timer);

				timer = $timeout(function() {
					UserFactory.ifExists(modelValue).then(
						function(response) {
							if (!response) {
								def.resolve();
							} else {
								def.reject();
							}
						},
						function(errResponse) {
							console.error('Error while checking login');
							def.reject();
						}
					);
				}, 1000);

				return def.promise;
			};
		}
	};

}]);