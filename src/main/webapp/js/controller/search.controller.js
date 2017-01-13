(function(window){
    'use strict';

    angular
    .module('localWomenApp')
    .controller('SearchController', [
		'$http',
		'$q',
    	'$scope',
    	'BusinessService',
    	'DetailsService',
    	'OrderService',
	    function($http,
	    		$q,
	    		$scope,
	    		BusinessService,
	        	DetailsService,
	        	OrderService) {
    		
			BusinessService.list().then(function(data){
				$scope.businesses = data;
				console.log($scope.businesses);
			});
			
    		$q.all([DetailsService.publications(), 
    			DetailsService.adtypes(), 
    			DetailsService.adsizes(),
    			OrderService.list()])
    			.then(function(data) {
    				var publications = data[0];
    				var adtypes = data[1];
    				var adsizes = data[2];
    				$scope.orders = data[3];
    				
    				console.log(publications);
    				console.log(adtypes);
    				console.log(adsizes);
    				console.log($scope.orders);
    			});
		}
    ])
})(window);