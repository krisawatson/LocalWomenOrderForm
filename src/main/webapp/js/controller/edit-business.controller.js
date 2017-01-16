(function(window){
    'use strict';

    angular.module('localWomenApp').controller('EditBusinessController', [
    	'$http',
    	'$routeParams',
    	'$scope',
    	'BusinessService',
    	Edit]);
    
    function Edit($http,
	    		$scope,
	    		$routeParams,
	    		BusinessService) {
    	var self = this;
    	var businessId = $routeParams.$id;
    	self.update = update;
    	
    	BusinessService.get(businessId).then(function(data){
    			self.business = data;
	        },
	        function(errResponse){
	        	console.err("Failed to get business");
	        }
	    );
    	
    	function update(valid){
    		if(!valid) return;
    		BusinessService.update(self.business).then(
    			function(response) {
                    console.log("Successfully updated business")
                },
                function(errResponse){
                    self.errorMsg = "Failed to update the business";
                }
    		);
    	}
	}
})(window);