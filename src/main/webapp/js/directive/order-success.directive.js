angular.module('localWomenApp')
.directive("orderSuccess", function() {
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
});