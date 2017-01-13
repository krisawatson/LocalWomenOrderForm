(function(angular){
    'use strict';

    var app = angular.module('localWomenApp');
    app.directive("mainDetails", function() {
	    return {
	        restrict: "A",
	        templateUrl: "tmpl/main-details.html"
	    };
    })
})(window.angular);