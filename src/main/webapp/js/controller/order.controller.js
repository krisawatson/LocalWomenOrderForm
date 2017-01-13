(function(window){
    'use strict';

    angular
    .module('localWomenApp')
    .controller('OrderController', [
    	'$http',
	    '$scope',
	    'DetailsService',
	    'OrderService',
	    function ($http,
	    		$scope,
	    		DetailsService,
	    		OrderService) {
	    	var id = 1;
			var now = new Date();
			var currentYear = now.getFullYear();
			$scope.currentMonth = now.getMonth() + 1;
			
			setStart();
			buildYears(currentYear);
			getResources();
			
		    $scope.addMoreDetails = function() {
		    	// For new order set the month to the next one
		    	var lastMonth = $scope.orderParts[$scope.orderParts.length - 1].month;
		    	var lastYear = $scope.orderParts[$scope.orderParts.length - 1].year;
				if(lastMonth == 12) {
		    		lastMonth = 0;
		    		++lastYear;
		    	}
				++lastMonth;
		    	var newOrder = {"id": ++id, "month": lastMonth.toString(), "year": lastYear, "publications": []};
		    	$scope.orderParts.push(newOrder);
		    }
		    
		    $scope.submitOrder = function() {
		    	$scope.orderError = null;
		    	fixOrderDetails();
		    	console.log($scope.orderParts);
		    	var order = {
		    		"business": $scope.business,
		    		"orderParts": $scope.orderParts
		    	}
		    	OrderService.create(order).then(function(orderNumber){
		    		$scope.orderNumber = orderNumber;
		    		$scope.goToNextStep();
		    	},function(error){
		    		$scope.orderError = "Something went wrong with your order, please review details and try again";
		    	});
		    };
		    
		    $scope.goToNextStep = function() {
		    	$scope.steps.step = ++$scope.steps.step;
		    	console.log($scope.publications);
		    };
		    
		    $scope.goToPreviousStep = function() {
		    	$scope.steps.step = --$scope.steps.step;
		    };
		    
		    $scope.removeOrder = function(id) {
		    	$scope.orderParts = $scope.orderParts.filter(function(order) {
		    	    return order.id !== id;
		    	});
		    };
		    
		    $scope.reset = setStart;
		    
		    function setStart() {
		    	var order = {
		    		"id":id,
		    		"month": $scope.currentMonth.toString(),
		    		"year": currentYear,
		    		"publications": []
		    	};
		    	$scope.business = {};
		    	$scope.orderParts = [order];
		    	
		    	$scope.steps = {
		    		"step": 1,
		    		"advertContentStep": 2,
		    		"maxSteps": 2
		    	};
		    };
		    
		    function getResources() {
		    	DetailsService.publications().then(function(data){
		    		$scope.publications = data;
		    	});
		    	
		    	DetailsService.adtypes().then(function (data) {
		    		$scope.adverts = data;
		    	});
		    	
		    	DetailsService.adsizes().then(function (data) {
		    		$scope.advertSizes = data;
		    	});
		    }
		    
		    function buildYears(currentYear) {
		    	var years = [];
		    	for(var i=0;i<3;i++) {
		    	  years.push(currentYear + i);
		    	}
		    	$scope.years = years;
		    }
		    
		    function fixOrderDetails() {
		    	angular.forEach($scope.orderParts,function(order){
		    		delete order.id;
		    		var publications = []
		    		angular.forEach(order.publications, function(publication){
		    			publications.push(publication);
		    		});
		    		order.publications = publications;
		        });
		    }
	}])
})(window);
