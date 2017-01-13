'use strict';

angular
.module('localWomenApp')
.directive("mainDetails", MainDirective);

function MainDirective() {
	return {
		restrict: "A",
		templateUrl: "tmpl/main-details.html"
	};
}