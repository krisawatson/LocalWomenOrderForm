(function(window){
    'use strict';

    angular
    .module('localWomenApp')
    .controller('NavController', [
    	'$http',
    	'$scope',
    	'UserService',
	    function($http,
	    		$scope,
	    		UserService) {
		    $scope.role = "USER";
		    UserService.get().then(function(data){
		    	$scope.user = data;
		    	data.authorities.find(function(auth){
	    			$scope.role = auth.authority;
	    		});
		    });
		}
	])
})(window);