'use strict';
app.controller('UserController', ['$location', '$state', 'URL', 'UserFactory', 'CredentialsService', 'FlashService', function($location, $state, URL, UserFactory, CredentialsService, FlashService) {
	var self = this;
	self.user = {id: null, login: '', role: null};

	self.login = function() {
		self.dataLoading = true;
		UserFactory.getUser(self.user.login, self.user.password, function(response) {
			if (response.success) {
				CredentialsService.setCredentials(response.data);
				$location.path(URL.HOME_PAGE);
			} else {
				FlashService.error(response.message);
			}
			self.dataLoading = false;
		});
	};

	self.logout = function() {
		CredentialsService.clearCredentials();
		$state.reload();
	};

	self.register = function() {
		self.dataLoading = true;
		UserFactory.createUser(self.user.login, self.user.password, function(response) {
			if (response.success) {
				CredentialsService.setCredentials(response);
				$location.path(URL.HOME_PAGE);
			} else {
				FlashService.error(response.message);
			}
			self.dataLoading = false;
		});
	};

}]);