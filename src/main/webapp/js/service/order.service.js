'use strict';
 
angular
.module('localWomenApp')
.service('OrderService', ['$http', '$q', 
    function($http, $q){
 
	var services = {
			create: create,
	        list: list
	    };
	 
	    return services;
	    
	    function create(order) {
	    	var deferred = $q.defer();
	    	$http.post('/order/create', order).then(function successCallback(response) {
	    		deferred.resolve(response.data);
	    	}, function(errResponse){
                deferred.reject(errResponse);
            });
	        return deferred.promise;
	    }
	    
	    function list() {
	    	var deferred = $q.defer();
	    	$http.get('/order/list').then(function successCallback(response) {
	    		deferred.resolve(response.data);
	    	}, function(errResponse){
                deferred.reject(errResponse);
            });
	        return deferred.promise;
	    }
	}
]);