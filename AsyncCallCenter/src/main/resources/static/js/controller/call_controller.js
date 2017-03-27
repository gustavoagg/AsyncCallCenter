'use strict';

App.controller('CallController', [
		'$scope',
		'CallService','$interval',
		function($scope, CallService,$interval) {
			var self = this;
			self.ncalls = 5;
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
			
			self.incomingCalls = function() {
				CallService.incomingCalls().then(function(d) {
					self.waiting = d;
				}, function(errResponse) {
					console.error('Error while getting incoming calls');
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
				self.incomingCalls();
	          }, 1000);
			

			self.submit = function() {
				console.log('Creating New Calls', self.ncalls);
				self.createCall(self.ncalls);
				self.reset();
			};

			

		} ]);
