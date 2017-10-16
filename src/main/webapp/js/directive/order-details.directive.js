/*
 * Kris Watson Copyright (c) 2017.
 */

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