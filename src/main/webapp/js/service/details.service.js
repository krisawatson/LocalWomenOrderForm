/*
 * Kris Watson Copyright (c) 2017.
 */

'use strict';
 
angular
.module('localWomenApp')
.service('DetailsService', ['$http', '$q', 
    function($http, $q){
 
	    var services = {
	        publications: publications,
	        adtypes: adtypes,
	        adsizes: adsizes,
	        roles: roles
	    };
	 
	    return services;
	 
	    function publications() {
	        var deferred = $q.defer();
	        $http.get("/details/publications").then(function successCallback(response) {
	    		deferred.resolve(response.data);
	    	}, function(errResponse){
                deferred.reject(errResponse);
            });
	        return deferred.promise;
	    }
	    
	    function adtypes() {
	        var deferred = $q.defer();
		    $http.get("/details/adtypes").then(function successCallback(response) {
		    	deferred.resolve(response.data);
	    	}, function(errResponse){
                deferred.reject(errResponse);
            });
		    return deferred.promise;
	    }
	    
	    function adsizes() {
	        var deferred = $q.defer();
		    $http.get("/details/adsizes").then(function successCallback(response) {
		    	deferred.resolve(response.data);
	    	}, function(errResponse){
                deferred.reject(errResponse);
            });
		    return deferred.promise;
	    }
	    
	    function roles() {
	        var deferred = $q.defer();
		    $http.get("/details/roles").then(function successCallback(response) {
		    	deferred.resolve(response.data);
	    	}, function(errResponse){
                deferred.reject(errResponse);
            });
		    return deferred.promise;
	    }
	}
]);