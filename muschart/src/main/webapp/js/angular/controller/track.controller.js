'use strict';
app.controller('TrackController', ['$scope', '$stateParams', 'DEFAULT', 'TrackFactory', function($scope, $stateParams, DEFAULT, TrackFactory) {
	var self = this;
	self.track = {id: null, name: '', song: '', cover: '', date: '', rating: null};
	self.tracks = [];

	self.addTrack = function(track) {
		TrackFactory.addTrack(track).then(
			self.getAllTracks,
			function(errResponse) {
				console.error('Error while adding track');
			}
		);
	};

	self.getTracksByIds = function(idFrom, idTo) {
		TrackFactory.getTracksByIds(idFrom, idTo).then(
			function(response) {
				self.tracks = response;
			},
			function(errResponse) {
				console.error('Error while getting tracks');
			}
		);
	};

	self.getAmplitudeTracksByIds = function(idFrom, idTo) {
		TrackFactory.getAmplitudeTracksByIds(idFrom, idTo).then(
			function(response) {
				Amplitude.init(response);
			},
			function(errResponse) {
				console.error('Error while getting tracks');
			}
		);
	};

	self.getAllTracks = function() {
		TrackFactory.getAllTracks().then(
			function(response) {
				self.tracks = response;
			},
			function(errResponse) {
				console.error('Error while getting tracks');
			}
		);
	};

	self.deleteTrack = function(id) {
		TrackFactory.deleteTrack(id).then(
			self.getAllTracks,
			function(errResponse) {
				console.error('Error while deleting track');
			}
		);
	};

	self.getTracksByPage = function(page) {
		self.getTracksByIds(DEFAULT.COUNT * (page - 1) + 1, DEFAULT.COUNT * page);
		self.getAmplitudeTracksByIds(DEFAULT.COUNT * (page - 1) + 1, DEFAULT.COUNT * page);
	};

	self.reset = function() {
		self.track = {id: null, name: '', song: '', cover: '', date: '', rating: null};
		$scope.form.$setPristine();
	};

	self.getTracksByPage($stateParams.page);

}]);