(function(window){
    'use strict';

    var app = angular.module('localWomenApp');
    app.controller('SearchController', ['$scope','$http',
	    function($scope,$http) {
		    $http.get('/order/list').then(function successCallback(response) {
	    		$scope.orders = response.data;
	    		console.log(response.data);
	    	}, function errorCallback(response) {
	    		console.error(response);
	    	});
		}
    ])
})(window);