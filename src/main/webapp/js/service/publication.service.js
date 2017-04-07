/*
 * Kris Watson Copyright (c) 2017.
 */

'use strict';
 
angular
.module('localWomenApp')
.service('PublicationService', ['$http', '$q', 
    function($http, $q){
		var services = {
	        list: list,
	        create: create,
	        update: update
	    };
	 
	    return services;
	    
	    function list() {
	    	var deferred = $q.defer();
	    	$http.get('/publication/list').then(function successCallback(response) {
	    		deferred.resolve(response.data);
	    	}, function(errResponse){
                deferred.reject(errResponse);
            });
	        return deferred.promise;
	    }
	    
	    function create(publication) {
	    	var deferred = $q.defer();
	    	$http.post('/publication/create', publication).then(function successCallback(response) {
	    		deferred.resolve(response);
	    	}, function(errResponse){
                deferred.reject(errResponse);
            });
	        return deferred.promise;
	    }
	    
	    function update(publication) {
	    	var deferred = $q.defer();
	    	$http.put('/publication/' + publication.id + '/update', publication).then(function successCallback(response) {
	    		deferred.resolve(response);
	    	}, function(errResponse){
                deferred.reject(errResponse);
            });
	        return deferred.promise;
	    }
	}
]);