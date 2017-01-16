'use strict';
 
angular
.module('localWomenApp')
.service('OrderService', ['$http', '$q', 
    function($http, $q){
 
	var services = {
			get: get,
			create: create,
	        list: list,
	        update: update
	    };
	 
	    return services;
	    
	    function get(orderId) {
	    	var deferred = $q.defer();
	    	$http.get('/order/' + orderId).then(function successCallback(response) {
	    		deferred.resolve(response.data);
	    	}, function(errResponse){
                deferred.reject(errResponse);
            });
	        return deferred.promise;
	    }
	    
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
	    
	    function update(order) {
	    	var deferred = $q.defer();
	    	$http.put('/order/'+order.id+'/update').then(function successCallback(response) {
	    		deferred.resolve(response.data);
	    	}, function(errResponse){
                deferred.reject(errResponse);
            });
	        return deferred.promise;
	    }
	}
]);