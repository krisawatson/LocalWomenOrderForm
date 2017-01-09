var localWomenApp = angular.module('localWomenApp', []);

localWomenApp.constant('orderURL','http://localhost:8080/order');

localWomenApp.controller('OrderController', function (orderURL, $scope,$http) {

	var id = 1;
	var now = new Date();
	var currentYear = now.getFullYear();
	var order = {
			"id":id,
			"month": now.getMonth() + 1,
			"year": currentYear,
			"publications": []
			};
	$scope.business = {};
	$scope.orders = [order];
	
	$scope.steps = {
		"step": 1,
		"advertContentStep": 2,
		"maxSteps": 2
	};
	
	buildYears(currentYear);
	getResources();
	
    $scope.addMoreDetails = function() {
    	// For new order set the month to the next one
    	var lastMonth = $scope.orders[$scope.orders.length - 1].month;
    	var lastYear = $scope.orders[$scope.orders.length - 1].year;
		if(lastMonth == 12) {
    		lastMonth = 0;
    		++lastYear;
    	}
    	var newOrder = {"id": ++id, "month": ++lastMonth, "year": lastYear, "publications": []};
    	$scope.orders.push(newOrder);
    }
    
    $scope.submitOrder = function() {
    	$scope.orderError = null;
    	fixPublications();
    	var order = {
    		"business": $scope.business,
    		"orders": $scope.orders
    	}
    	$http.post(orderURL, order).success(function(orderNumber){
    		$scope.orderNumber = orderNumber;
    		$scope.goToNextStep();
    	}).error(function(){
    		$scope.orderError = "Something went wrong with your order, please review details and try again";
    	});
    };
    
    $scope.goToNextStep = function() {
    	$scope.steps.step = ++$scope.steps.step;
    };
    
    $scope.goToPreviousStep = function() {
    	$scope.steps.step = --$scope.steps.step;
    };
    
    $scope.removeOrder = function(id) {
    	$scope.orders = $scope.orders.filter(function(order) {
    	    return order.id !== id;
    	});
    };
    
    function getResources() {
    	$http.get("resources/publications.json").success(
    		function(data){
    			$scope.publications = data;
    		}
    	);
    	$http.get("resources/adverts.json").success(
    		function(data){
    			$scope.adverts = data;
    		}
    	);
    	$http.get("resources/advertSizes.json").success(
    		function(data){
    			$scope.advertSizes = data;
    		}
    	);
    }
    
    function buildYears(currentYear) {
    	var years = [];
    	for(var i=0;i<3;i++) {
    	  years.push(currentYear + i);
    	}
    	$scope.years = years;
    }
    
    function fixPublications() {
    	angular.forEach($scope.orders,function(order){
    		var publications = []
    		angular.forEach(order.publications, function(publication){
    			publications.push(publication);
    		});
    		order.publications = publications;
        });
    }
})
.directive("mainDetails", function() {
    return {
        restrict: "AE",
        templateUrl: "tmpl/main-details.tmpl"
    };
})
.directive("orderDetails", function() {
    return {
        restrict: "AE",
        templateUrl: "tmpl/order-details.tmpl"
    };
})
.directive("orderSuccess", function() {
    return {
        restrict: "AE",
        templateUrl: "tmpl/order-success.tmpl",
        scope: {
            ordernumber: '=',
        }
    };
});