'use strict';
var ngExist = 'ngExist';
var ngMatch = 'ngMatch';
var ngModel = 'ngModel';

app.directive(ngExist, ['$q', '$timeout', 'UserFactory', function($q, $timeout, UserFactory) {
	var timer;
	return {
		require: ngModel,
		link: function(scope, element, attributes, controller) {
			controller.$asyncValidators.ngExist = function(modelValue, viewValue) {
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

app.directive(ngMatch, function() {
	return {
		require: ngModel,
		scope: {
			otherModelValue: "=" + ngMatch
		},
		link: function(scope, element, attributes, controller) {
			controller.$validators.ngMatch = function(modelValue) {
				return modelValue == scope.otherModelValue;
			};
			scope.$watch("otherModelValue", function() {
				controller.$validate();
			});
		}
	};
});