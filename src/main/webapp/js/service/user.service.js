'use strict';
 
angular
.module('localWomenApp')
.service('UserService', ['$http', '$q', 
    function($http, $q){
		var services = {
	        get: get,
	        getId: getId,
	        list: list,
	        roles: roles,
	        create: create,
	        update: update
	    };
	 
	    return services;
	    
	    function get() {
	    	var deferred = $q.defer();
	    	$http.get('/auth').then(function successCallback(response) {
	    		deferred.resolve(response.data);
	    	}, function(errResponse){
                deferred.reject(errResponse);
            });
	        return deferred.promise;
	    }
	    
	    function getId() {
	    	var deferred = $q.defer();
	    	$http.get('/auth/id').then(function successCallback(response) {
	    		deferred.resolve(response.data);
	    	}, function(errResponse){
                deferred.reject(errResponse);
            });
	        return deferred.promise;
	    }
	    
	    function list() {
	    	var deferred = $q.defer();
	    	$http.get('/user/list').then(function successCallback(response) {
	    		deferred.resolve(response.data);
	    	}, function(errResponse){
                deferred.reject(errResponse);
            });
	        return deferred.promise;
	    }
	    
	    function roles() {
	    	var deferred = $q.defer();
	    	$http.get('/user/roles').then(function successCallback(response) {
	    		deferred.resolve(response.data);
	    	}, function(errResponse){
                deferred.reject(errResponse);
            });
	        return deferred.promise;
	    }
	    
	    function create(user) {
	    	var deferred = $q.defer();
	    	$http.post('/user/create', user).then(function successCallback(response) {
	    		deferred.resolve(response);
	    	}, function(errResponse){
                deferred.reject(errResponse);
            });
	        return deferred.promise;
	    }
	    
	    function update(user) {
	    	var deferred = $q.defer();
	    	$http.put('/user/' + user.id + '/update', user).then(function successCallback(response) {
	    		deferred.resolve(response);
	    	}, function(errResponse){
                deferred.reject(errResponse);
            });
	        return deferred.promise;
	    }
	}
]);