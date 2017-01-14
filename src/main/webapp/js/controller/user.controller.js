(function(window){
    'use strict';

    angular.module('localWomenApp').controller('UserController', [
    	'$scope', 
    	'UserService',
    	User]);
    
	function User($scope, UserService) {
	    var self = this;
	    var newUser = {username:'',password:''};
	    angular.copy(newUser, self.user);
	    self.create = create;
	    
	    function create(valid) {
	    	delete self.errorMsg;
	    	delete self.successMsg
	    	if(!valid){
	    		self.errorMsg = "Not all required fields were properly filled out.  Please check and try again";
	    		return;
	    	}
	    	self.user.enabled = true;
	    	UserService.create(self.user).then(
	        	function(response) {
	        		self.successMsg = "Successfully created account '" + self.user.username + "'";
	        		angular.copy(newUser, self.user);
	            },
	            function(errResponse){
	            	if(errResponse.status == 409) {
	        			self.errorMsg = "Username already exists, try using a different one";
	        		} else {
	        			self.errorMsg = "Failed to create account, error status " + errResponse.status;
	        		}
	            }
	        );
	    }
	}
})(window);