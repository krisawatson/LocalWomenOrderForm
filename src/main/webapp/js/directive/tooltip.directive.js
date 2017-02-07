(function(window){
    'use strict';

    angular
    .module('localWomenApp')
    .directive('tooltip', function(){
	    return {
	        restrict: 'A',
	        link: function(scope, element, attrs){
	            $(element).hover(function(){
	                $(element).tooltip('show');
	            }, function(){
	                $(element).tooltip('hide');
	            });
	        }
	    };
	})
})(window);