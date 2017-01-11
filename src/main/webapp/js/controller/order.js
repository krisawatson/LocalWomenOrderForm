var localWomenApp = angular.module('localWomenApp', []);

localWomenApp.constant('orderURL','/order');

localWomenApp.controller('OrderController', function (orderURL, $scope,$http) {

	var id = 1;
	var now = new Date();
	var currentYear = now.getFullYear();
	
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
    	var newOrder = {"id": ++id, "month": ++lastMonth, "year": lastYear, "publications": []};
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
    	$scope.orderParts = $scope.orderParts.filter(function(order) {
    	    return order.id !== id;
    	});
    };
    
    $scope.reset = setStart;
    
    function setStart() {
    	var order = {
    		"id":id,
    		"month": now.getMonth() + 1,
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
    	$http.get("/details/publications").success(
    		function(data){
    			$scope.publications = data;
    			console.log($scope.publications);
    		}
    	);
    	$http.get("/details/adtypes").success(
    		function(data){
    			$scope.adverts = data;
    			console.log($scope.adverts);
    		}
    	);
    	$http.get("/details/adsizes").success(
    		function(data){
    			$scope.advertSizes = data;
    			console.log($scope.advertSizes);
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
})
.directive("mainDetails", function() {
    return {
        restrict: "A",
        templateUrl: "tmpl/main-details.tmpl"
    };
})
.directive("orderDetails", function() {
    return {
        restrict: "A",
        templateUrl: "tmpl/order-details.tmpl"
    };
})
.directive("orderSuccess", function() {
    return {
        restrict: "E",
        templateUrl: "tmpl/order-success.tmpl",
        scope: {
            ordernumber: '=',
            okFn: '&'
        },
        link: function(scope, element, attrs)
        {
        	scope.reset = function() {
                scope.okFn();
        	}
        }
    };
});