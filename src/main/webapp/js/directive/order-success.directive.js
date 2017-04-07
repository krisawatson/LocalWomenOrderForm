/*
 * Kris Watson Copyright (c) 2017.
 */

'use strict';

angular
.module('localWomenApp')
.directive("orderSuccess", SuccessDirective);

function SuccessDirective() {
	return {
        restrict: "E",
        templateUrl: "tmpl/order-success.html",
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
}