(function(window){
	'use strict';
	 
	angular
	.module('localWomenApp')
	.service('BusinessService', ['$http', '$q', 
	    function($http, $q){
			var services = {
				get: get,
		        list: list
		    };
		 
		    return services;
		    
		    function get(id) {
		    	var deferred = $q.defer();
		    	$http.get('/business/' + id).then(function successCallback(response) {
		    		deferred.resolve(response.data);
		    	}, function(errResponse){
	                deferred.reject(errResponse);
	            });
		        return deferred.promise;
		    }
		    
		    function list() {
		    	var deferred = $q.defer();
		    	$http.get('/business/list').then(function successCallback(response) {
		    		deferred.resolve(response.data);
		    	}, function(errResponse){
	                deferred.reject(errResponse);
	            });
		        return deferred.promise;
		    }
		}
	])
})(window);