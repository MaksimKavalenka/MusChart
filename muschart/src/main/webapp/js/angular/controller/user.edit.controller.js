'use strict';
app.controller('UserEditController', ['$scope', '$state', 'STATE', 'UserFactory', 'CookieService', 'FlashService', function($scope, $state, STATE, UserFactory, CookieService, FlashService) {

	var self = this;

	self.login = function() {
		self.dataLoading = true;
		UserFactory.authentication(self.user.login, self.user.password, function(response) {
			if (response.success) {
				CookieService.setCredentials(response.data);
				$state.go(STATE.TRACKS);
			} else {
				FlashService.error(response.message);
			}
			self.dataLoading = false;
		});
	};

	self.logout = function() {
		CookieService.clearCredentials();
		switch ($state.current.name) {
			case STATE.USER_ARTISTS:
				$location.path(URL.ARTISTS);
				break;
			case STATE.USER_GENRES:
				$location.path(URL.GENRES);
				break;
			case STATE.USER_TRACKS:
				$location.path(URL.TRACKS);
				break;
			default:
				$state.reload();
		}
	};

	self.register = function() {
		self.dataLoading = true;
		UserFactory.createUser(self.user.login, self.user.password, function(response) {
			if (response.success) {
				CookieService.setCredentials(response.data);
				$state.go(STATE.TRACKS);
			} else {
				FlashService.error(response.message);
			}
			self.dataLoading = false;
		});
	};

	self.like = function(relation, id) {
		UserFactory.setUserLike($scope.user.id, relation, id, function(response) {
			if (!response.success) {
				FlashService.error(response.message);
			}
		});
	};

}]);