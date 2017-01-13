(function(window){
    'use strict';

    var app = angular.module('localWomenApp');
    app.controller('AuthController', ['$scope', 'AuthService', 
	    function($scope, AuthService) {
		    var self = this;
		    self.user={username:'',password:''};
		    
		    self.submit = submit;
		 
		    function submit() {
		    	console.log(self.user);
		        AuthService.login(self.user)
		        .then(
		            function(d) {
		                console.log(d);
		            },
		            function(errResponse){
		                console.error('Error while logging in');
		            }
		        );
		    }
		}
	])
})(window);