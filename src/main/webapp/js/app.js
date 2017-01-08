var localWomenApp = angular.module('localWomenApp', []);

localWomenApp.constant('orderURL','http://localhost:8080/rest/order');

localWomenApp.controller('OrderController', function (orderURL, $scope,$http) {

	var id = 1;
	var order = {"id":id};
	$scope.business = {};
	$scope.orders = [order];
	$scope.steps = {
		"step": 1,
		"advertContentStep": 2,
		"maxSteps": 2
	};
	$scope.publications = [
	    {
	    	id: 1,
	    	name: "North_West_Donegal",
	    	value: "North West / Donegal",
	    	adType: "",
	    	selected: false,
	    	notes: ""
	    },
	    {
	    	id: 2,
	    	name: "North_Coast",
	    	value: "North Coast",
	    	adType: "",
	    	selected: false,
	    	notes: ""
	    },
	    {
	    	id: 3,
	    	name: "Mid_Ulster",
	    	value: "Mid Ulster",
	    	adType: "",
	    	selected: false,
	    	notes: ""
	    },
	    {
	    	id: 4,
	    	name: "Belfast",
	    	value: "Belfast",
	    	adType: "",
	    	selected: false,
	    	notes: ""
	    }
	];
	$scope.adverts = [
  	    {
  	    	"id": 1,
  	    	"type": "advert",
  	    	"name": "Advert"
  		},
  		{
  			"id": 2,
  	    	"type": "feature",
  	    	"name": "Feature"
  		},
  		{
  			"id": 3,
  	    	"type": "editorial",
  	    	"name": "Editorial"
  		},
  		{
  			"id": 4,
  	    	"type": "photoshoot",
  	    	"name": "Photo Shoot"
  		},
  		{
  			"id": 5,
  	    	"type": "frontcover",
  	    	"name": "Front Cover"
  		}
  	];
	
	$scope.advertSizes = [
	    {
	    	"id": 1,
	    	"type": "strap",
	    	"name": "Strap"
		},
		{
			"id": 2,
	    	"type": "quarter",
	    	"name": "1/4"
		},
		{
			"id": 3,
	    	"type": "half",
	    	"name": "1/2"
		},
		{
			"id": 4,
	    	"type": "full",
	    	"name": "Full"
		},
		{
			"id": 5,
	    	"type": "spread",
	    	"name": "Spread"
		}
	];
	
    $scope.addMoreDetails = function() {
    	var newOrder = {"id": ++id};
    	$scope.orders.push(newOrder);
    	console.log($scope.orders);
    }
    
    $scope.submitOrder = function() {
        console.log($scope.publications);
    };
    
    $scope.goToNextStep = function() {
    	if($scope.steps.step == 1) {
    		console.log($scope.business);
    	}
    	$scope.steps.step = ++$scope.steps.step;
    };
    
    $scope.goToPreviousStep = function() {
    	$scope.steps.step = --$scope.steps.step;
    };
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
});