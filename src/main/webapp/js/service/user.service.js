'use strict';
 
angular
.module('localWomenApp')
.service('UserService', ['$http', '$q', 
    function($http, $q){
		var services = {
	        get: get,
	        create: create
	    };
	 
	    return services;
	    
	    function get() {
	    	var deferred = $q.defer();
	    	$http.get('/user').then(function successCallback(response) {
	    		deferred.resolve(response.data);
	    	}, function(errResponse){
                deferred.reject(errResponse);
            });
	        return deferred.promise;
	    }
	    
	    function create(user) {
	    	var deferred = $q.defer();
	    	$http.post('/user/create', user).then(function successCallback(response) {
	    		deferred.resolve(response.data);
	    	}, function(errResponse){
                deferred.reject(errResponse);
            });
	        return deferred.promise;
	    }
	}
]);