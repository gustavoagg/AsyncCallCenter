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
		    
		    createCall2: function(ncalls){
					return $http.post('http://localhost:8080/call/', ncalls)
							.then(
									function(response){

										console.error('trajo alternativas'+response.data);
										return response.data;
									}, 
									function(errResponse){
										
										if(errResponse.data!=null){
											console.error('Error conflict with User');
											return errResponse.data;
										}
										else{
											console.error('Error while creating user');
											return $q.reject(errResponse);
										}
										
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
