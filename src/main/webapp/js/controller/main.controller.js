/*
 * Kris Watson Copyright (c) 2017.
 */

(function(window){
    'use strict';

    angular.module('localWomenApp').controller('MainController', [
    	'$http',
    	'$scope',
    	'DetailsService',
    	'UserService',
    	Main]);
    
    function Main($http,
	    		$scope,
	    		DetailsService,
	    		UserService) {
    	var self = this;
	    self.role = "USER";
	    
	    UserService.get().then(function(data){
	    	self.user = data;
	    	data.authorities.find(function(auth){
    			self.role = auth.authority;
    		});
	    });
	    
	    DetailsService.roles().then(function(data){
	    	self.roles = data;
	    });
	}
})(window);