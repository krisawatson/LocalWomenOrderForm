var localWomenApp = angular.module('localWomenApp', []);

localWomenApp.constant('orderURL','http://localhost:8080/rest/order');

localWomenApp.controller('OrderController', function (orderURL, $scope,$http) {

	var id = 1;
	var newOrder = {"id":id};
	$scope.orders = [newOrder];
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
  	    	"type": "Advert",
  	    	"name": "Advert"
  		},
  		{
  			"id": 2,
  	    	"type": "Photoshoot",
  	    	"name": "Photoshoot"
  		}
  	];
	
    $scope.addMoreDetails = function() {
    	newOrder.id = ++id;
    	console.log(newOrder);
    	$scope.orders.push(newOrder);
    }
    
    $scope.submitOrder = function() {
        console.log($scope.publications);
    };
    
    $scope.goToNextStep = function() {
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