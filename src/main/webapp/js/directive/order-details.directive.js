'use strict';
angular
.module('localWomenApp')
.directive("orderDetails", OrderDirective);

function OrderDirective() {
	return {
		restrict: "A",
		templateUrl: "tmpl/order-details.html"
	};
}