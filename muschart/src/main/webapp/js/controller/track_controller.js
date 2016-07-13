'use strict';
app.controller('TrackController', ['$scope', 'TrackService', function($scope, TrackService) {
	var self = this;
	self.track = {id:null, name:'', song:'', cover:'', date:null, rating:null};
	self.tracks = [];
	var count = 15;

	self.addTrack = function(track) {
		TrackService.addTrack(track).then(
			self.getAllTracks,
			function(errResponse) {
				console.error('Error while adding track');
			}
		);
	};

	self.getTracksByIds = function(idFrom, idTo) {
		TrackService.getTracksByIds(idFrom, idTo).then(
			function(d) {
				self.tracks = d;
			},
			function(errResponse) {
				console.error('Error while getting tracks');
			}
		);
	};

	self.getAllTracks = function() {
		TrackService.getAllTracks().then(
			function(d) {
				self.tracks = d;
			},
			function(errResponse) {
				console.error('Error while getting tracks');
			}
		);
	};

	self.deleteTrack = function(id) {
		TrackService.deleteTrack(id).then(
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