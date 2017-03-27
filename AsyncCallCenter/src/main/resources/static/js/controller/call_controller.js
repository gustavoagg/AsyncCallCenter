'use strict';

App.controller('CallController', [
		'$scope',
		'CallService','$interval',
		function($scope, CallService,$interval) {
			var self = this;
			self.ncalls = 10;
			self.workers = null;
			self.lines = null;
			self.waiting = 0;

			self.fetchAllLines = function() {
				CallService.fetchAllLines().then(function(d) {
					self.lines = d;
				}, function(errResponse) {
					console.error('Error while fetching Lines');
				});
			};

			self.createCall = function(call) {
				CallService.createCall(call).then(function(d) {
					self.lines = d;
				}, function(errResponse) {
					console.error('Error while creating Calls.');
				});
			};

			self.refresh = function(id) {
				self.fetchAllLines();
			};

			var stop = $interval(function() {
				self.fetchAllLines();
	          }, 1000);
			

			self.submit = function() {
				console.log('Creating New Calls', self.ncalls);
				self.createCall(self.ncalls);
				self.reset();
			};

			self.remove = function(id) {
				console.log('id to be deleted', id);
				if (self.user.id === id) {// clean form if the user to be
											// deleted is shown there.
					self.reset();
				}
				self.deleteUser(id);
			};

			self.suggest = function(name) {
				self.reset();
				console.log("suggest attributes received");
				self.user.username = name;
			};

			self.reset = function() {
				self.user = {
					id : null,
					username : ''
				};
				$scope.myForm.$setPristine(); // reset Form
			};

		} ]);
