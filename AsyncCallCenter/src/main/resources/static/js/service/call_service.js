'use strict';

App.factory('CallService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllLines: function() {
					return $http.get('http://localhost:8080/monitor/')
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										console.error('Error while fetching users');
										return $q.reject(errResponse);
									}
							);
			},
		    
			incomingCalls: function(){
					return $http.post('http://localhost:8080/incoming/')
							.then(
									function(response){
										
										return response.data;
									}, 
									function(errResponse){										
											console.error('Error while obtaining number of incoming calls');
											return $q.reject(errResponse);

																																	
									}
							);
		    },
		    
		    
		    createCall: function(id){
					return $http.get('http://localhost:8080/calls/'+id)
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										console.error('Error while creating calls');
										return $q.reject(errResponse);
									}
							);
			}
		
	};

}]);
