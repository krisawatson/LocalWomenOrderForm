/*
 * Kris Watson Copyright (c) 2017.
 */

'use strict';
 
angular
.module('localWomenApp')
.service('OrderService', ['$http', '$q', 
    function($http, $q){
 
		var services = {
			get: get,
			create: create,
	        list: list,
			getInProgress: getInProgress,
			getFinished: getFinished,
	        update: update,
            remove: remove,
            doDelete: doDelete
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
	    
	    function getInProgress() {
	    	var deferred = $q.defer();
	    	$http.get('/order/inprogress').then(function successCallback(response) {
	    		deferred.resolve(response.data);
	    	}, function(errResponse){
                deferred.reject(errResponse);
            });
	        return deferred.promise;
	    }
	    
	    function getFinished() {
	    	var deferred = $q.defer();
	    	$http.get('/order/finished').then(function successCallback(response) {
	    		deferred.resolve(response.data);
	    	}, function(errResponse){
                deferred.reject(errResponse);
            });
	        return deferred.promise;
	    }
	    
	    function update(orderId, order) {
	    	var deferred = $q.defer();
	    	$http.put('/order/'+ orderId, order).then(function successCallback(response) {
	    		deferred.resolve(response.data);
	    	}, function(errResponse){
                deferred.reject(errResponse);
            });
	        return deferred.promise;
	    }
	    
	    function remove(orderId, orderPartId) {
	    	var deferred = $q.defer();
	    	$http.delete('/order/'+ orderId + '/orderpart/' + orderPartId).then(function successCallback(response) {
	    		deferred.resolve(response.data);
	    	}, function(errResponse){
                deferred.reject(errResponse);
            });
	        return deferred.promise;
	    }

        function doDelete(orderId) {
            var deferred = $q.defer();
            $http.delete('/order/' + orderId + '/delete').then(function successCallback(response) {
                deferred.resolve(response.data);
            }, function (errResponse) {
                deferred.reject(errResponse);
            });
            return deferred.promise;
        }
	}
]);