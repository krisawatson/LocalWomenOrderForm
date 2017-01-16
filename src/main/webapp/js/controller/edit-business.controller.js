(function(window){
    'use strict';

    angular.module('localWomenApp').controller('EditBusinessController', [
    	'$http',
    	'$location',
    	'$routeParams',
    	'$scope',
    	'BusinessService',
    	Edit]);
    
    function Edit($http,
    			$location,
	    		$scope,
	    		$routeParams,
	    		BusinessService) {
    	var self = this;
    	var businessId = $location.path().split("/")[2];
    	self.update = update;
    	
    	BusinessService.get(businessId).then(function(data){
    			self.business = data;
	        },
	        function(errResponse){
	        	console.log("Failed to get business");
	        }
	    );
    	
    	function update(valid){
    		delete self.successMsg;
    		delete self.errorMsg;
    		if(!valid) return;
    		BusinessService.update(self.business).then(
    			function(response) {
    				self.successMsg ="Successfully updated business";
                },
                function(errResponse){
                    self.errorMsg = "Failed to update the business";
                }
    		);
    	}
	}
})(window);