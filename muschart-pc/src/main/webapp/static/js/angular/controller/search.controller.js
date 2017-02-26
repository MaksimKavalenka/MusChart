'use strict';
app.controller('SearchController', function($scope, SearchFactory, FlashService) {

	$scope.search = function(keyCode) {
		if ((keyCode === -1) || (keyCode === 8) || (keyCode === 46) || ((keyCode >= 48) && (keyCode <= 57)) || ((keyCode >= 65) && (keyCode <= 90))) {
			if (!angular.isUndefined($scope.query) && $scope.query !== '') {
				getSuggestions($scope.query);
			} else {
				$scope.suggestions = [];
			}
		}
	};

	$scope.show = function(show) {
		if (!show && $scope.hold) {
			return;
		}
		if (show) {
			if ($scope.suggestions !== undefined) {
				var divsToShow = document.getElementsByClassName('suggestion');
				for (var i = 0; i < divsToShow.length; i++) {
					divsToShow[i].style.display = 'block';
				}
			} else {
				$scope.search(-1);
			}
		} else {
			var divsToHide = document.getElementsByClassName('suggestion');
			for (var i = 0; i < divsToHide.length; i++) {
				divsToHide[i].style.display = 'none';
			}
		}
	};

	$scope.holdSuggestions = function(hold) {
		$scope.hold = hold;
	};

	function getSuggestions(query) {
		SearchFactory.getSuggestions(query, function(response) {
			if (response.success) {
				$scope.suggestions = response.data;
				for (var i = 0; i < $scope.suggestions.length; i++) {
					$scope.suggestions[i].url = $scope.suggestions[i].entity + '({id: ' + $scope.suggestions[i].id + '})';
				}
			} else {
				FlashService.error(response.message);
			}
		});
	}

});