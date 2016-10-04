'use strict';
app.controller('UserEditController', ['$rootScope', '$scope', '$state', 'STATE', 'UserFactory', 'CookieService', 'FlashService', function($rootScope, $scope, $state, STATE, UserFactory, CookieService, FlashService) {

	var self = this;

	self.login = function() {
		self.dataLoading = true;
		UserFactory.authentication(self.user.login, self.user.password, function(response) {
			if (response.success) {
				$rootScope.user = {id: response.data.id};
				$state.go(STATE.TRACKS, {page: 1});
			} else {
				FlashService.error(response.message);
			}
			self.dataLoading = false;
		});
	};

	self.logout = function() {
		$rootScope.user = null;
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

	self.register = function() {
		self.dataLoading = true;
		UserFactory.createUser(self.user.login, self.user.password, self.user.confirmPassword, function(response) {
			if (response.success) {
				self.login();
			} else {
				FlashService.error(response.message);
			}
			self.dataLoading = false;
		});
	};

	self.like = function(entity, entityId) {
		UserFactory.setUserLike(entity, entityId, function(response) {
			if (!response.success) {
				FlashService.error(response.message);
			}
		});
	};

}]);