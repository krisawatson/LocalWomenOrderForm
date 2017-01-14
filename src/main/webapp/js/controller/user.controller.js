(function(window){
    'use strict';

    angular.module('localWomenApp').controller('UserController', [
    	'$scope', 
    	'UserService',
    	User]);
    
	function User($scope, UserService) {
	    var self = this;
	    self.user={username:'',password:'',enabled: true};
	    self.create = create;
	    
	    function create() {
	    	console.log(self.user);
	        UserService.create(self.user).then(
	        	function(d) {
	                console.log(d);
	            },
	            function(errResponse){
	                console.error('Error while logging in');
	            }
	        );
	    }
	}
})(window);