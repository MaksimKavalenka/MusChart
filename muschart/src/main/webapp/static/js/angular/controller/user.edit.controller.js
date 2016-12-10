'use strict';
app.controller('UserEditController', function($rootScope, $scope, $state, STATE, UserFactory, CookieService, FlashService) {

	$scope.login = function() {
		$scope.dataLoading = true;
		UserFactory.authentication($scope.user.login, $scope.user.password, function(response) {
			if (response.success) {
				$rootScope.user = {
					name: response.data.login,
					admin: response.data.admin
				};
				CookieService.setUser($rootScope.user);
				$state.go(STATE.TRACKS, {page: 1});
			} else {
				FlashService.error(response.message);
			}
			$scope.dataLoading = false;
		});
	};

	$scope.logout = function() {
		$rootScope.user = null;
		CookieService.clearUser();
		UserFactory.logout();
		switch ($state.current.name) {
			case STATE.USER_ARTISTS:
				$state.go(STATE.ARTISTS, {page: 1});
				break;
			case STATE.USER_GENRES:
				$state.go(STATE.GENRES, {page: 1});
				break;
			case STATE.USER_TRACKS:
				$state.go(STATE.TRACKS, {page: 1});
				break;
			default:
				$state.reload();
		}
	};

	$scope.register = function() {
		$scope.dataLoading = true;
		UserFactory.createUser($scope.user.login, $scope.user.password, $scope.user.confirmPassword, function(response) {
			if (response.success) {
				$scope.login();
			} else {
				FlashService.error(response.message);
			}
			$scope.dataLoading = false;
		});
	};

	$scope.like = function(entity, entityId) {
		UserFactory.setUserLike(entity, entityId, function(response) {
			if (!response.success) {
				FlashService.error(response.message);
			}
		});
	};

});