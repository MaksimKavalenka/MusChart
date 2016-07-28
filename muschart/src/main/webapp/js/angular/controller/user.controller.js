'use strict';
app.controller('UserController', ['$location', '$state', 'CredentialsFactory', 'FlashFactory', 'UserFactory', 'URL', function($location, $state, CredentialsFactory, FlashFactory, UserFactory, URL) {
	var self = this;
	self.user = {id: null, login: '', role: null};

	self.login = function() {
		self.dataLoading = true;
		UserFactory.getUser(self.user.login, self.user.password, function(response) {
			if (response.success) {
				CredentialsFactory.setCredentials(response.data);
				$location.path('tracks({page:1})');
			} else {
				FlashFactory.error(response.message);
				self.dataLoading = false;
			}
		});
	};

	self.logout = function() {
		CredentialsFactory.clearCredentials();
		$state.reload();
	};

	self.register = function() {
		self.dataLoading = true;
		UserFactory.createUser(self.user.login, self.user.password, function(response) {
			if (response.success) {
				CredentialsFactory.setCredentials(response);
				$location.path('tracks({page:1})');
			} else {
				FlashFactory.error(response.message);
				self.dataLoading = false;
			}
		});
	};

}]);