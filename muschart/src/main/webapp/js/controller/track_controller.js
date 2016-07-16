'use strict';
app.controller('TrackController', ['$scope', 'TrackFactory', function($scope, TrackFactory) {
	var self = this;
	self.track = {id:null, name:'', song:'', cover:'', date:null, rating:null};
	self.tracks = [];
	var count = 15;

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
		self.getTracksByIds(count * (page - 1) + 1, count * page);
	};

	self.reset = function() {
		self.track = {id:null, name:'', song:'', cover:'', date:null, rating:null};
		$scope.form.$setPristine();
	};

	self.getTracksByPage(1);

}]);