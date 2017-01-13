(function(window){
    'use strict';

    angular.module('localWomenApp').controller('NavController', [
    	'$http',
    	'$scope',
    	'UserService',
    	Nav]);
    
    function Nav($http,
	    		$scope,
	    		UserService) {
    	var self = this;
	    self.role = "USER";
	    
	    UserService.get().then(function(data){
	    	self.user = data;
	    	data.authorities.find(function(auth){
    			self.role = auth.authority;
    		});
	    });
	}
})(window);