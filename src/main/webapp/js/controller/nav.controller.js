(function(window){
    'use strict';

    var app = angular.module('localWomenApp');
    app.controller('NavController', ['$scope','$http', 
	    function($scope,$http) {
		    $scope.role = "USER";
		    $http.get("/user").then(function successCallback(response) {
	    		response.data.authorities.find(function(auth){
	    			$scope.role = auth.authority;
	    		});
	    	}, function errorCallback(response) {
	    		console.error(response);
	    	});
		}
	])
})(window);