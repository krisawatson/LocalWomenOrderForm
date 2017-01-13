'use strict';
angular.module('localWomenApp')
.directive("orderDetails", function() {
    return {
        restrict: "A",
        templateUrl: "tmpl/order-details.html"
    };
});