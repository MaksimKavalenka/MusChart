'use strict';
var fileRequired = 'fileRequired';
var fileModel = 'fileModel';
var ngMatch = 'ngMatch';
var ngModel = 'ngModel';
var ngGenreExist = 'ngGenreExist';
var ngLoginExist = 'ngLoginExist';

app.directive(ngGenreExist, ['$timeout', '$q', 'GenreFactory', function($timeout, $q, GenreFactory) {
	var timer;
	return {
		require: ngModel,
		link: function(scope, element, attributes, controller) {
			controller.$asyncValidators.ngGenreExist = function(modelValue, viewValue) {
				var def = $q.defer();
				$timeout.cancel(timer);
				timer = $timeout(function() {
					GenreFactory.checkName(modelValue, function(response) {
						if (!response.success) {
							def.resolve();
						} else {
							def.reject();
						}
					});
				}, 1000);
				return def.promise;
			};
		}
	};
}]);

app.directive(ngLoginExist, ['$timeout', '$q', 'UserFactory', function($timeout, $q, UserFactory) {
	var timer;
	return {
		require: ngModel,
		link: function(scope, element, attributes, controller) {
			controller.$asyncValidators.ngLoginExist = function(modelValue, viewValue) {
				var def = $q.defer();
				$timeout.cancel(timer);
				timer = $timeout(function() {
					UserFactory.checkLogin(modelValue, function(response) {
						if (!response.success) {
							def.resolve();
						} else {
							def.reject();
						}
					});
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

app.directive(fileRequired, function() {
	return {
		require: ngModel,
		link: function(scope, element, attributes, controller) {
			element.bind('change', function() {
				scope.$apply(function() {
					controller.$setViewValue(element.val());
					controller.$render();
				});
			});
		}
	}
});

app.directive(fileModel, ['$parse', function($parse) {
	return {
		restrict: 'A',
		link: function(scope, element, attributes) {
			var model = $parse(attributes.fileModel);
			var modelSetter = model.assign;
			element.bind('change', function() {
				scope.$apply(function() {
					modelSetter(scope, element[0].files[0]);
				});
			});
		}
	};
}]);

app.directive('modal', function() {
	return {
		template: '<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true"><div class="modal-dialog modal-sm"><div class="modal-content" ng-transclude><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button><h4 class="modal-title" id="myModalLabel">Modal title</h4></div></div></div></div>',
		restrict: 'E',
		transclude: true,
		replace: true,
		scope: {
			visible:'=',
			onSown:'&',
			onHide:'&'
		},
		link: function postLink(scope, element, attributes) {
			$(element).modal({
				show: false,
				keyboard: attributes.keyboard,
				backdrop: attributes.backdrop
			});
			scope.$watch(function() {
				return scope.visible;
			}, function(value) {
				if(value == true) {
					$(element).modal('show');
				} else {
					$(element).modal('hide');
				}
			});
			$(element).on('shown.bs.modal', function() {
				scope.$apply(function() {
					scope.$parent[attributes.visible] = true;
				});
			});
			$(element).on('shown.bs.modal', function() {
				scope.$apply(function() {
					scope.onSown({});
				});
			});
			$(element).on('hidden.bs.modal', function() {
				scope.$apply(function() {
					scope.$parent[attributes.visible] = false;
				});
			});
			$(element).on('hidden.bs.modal', function() {
				scope.$apply(function() {
					scope.onHide({});
				});
			});
		}
	};
});

app.directive('modalBody', function() {
	return {
		template: '<div class="modal-body" ng-transclude></div>',
		replace: true,
		restrict: 'E',
		transclude: true
	};
});