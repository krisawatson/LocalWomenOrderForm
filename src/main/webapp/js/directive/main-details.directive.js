/*
 * Kris Watson Copyright (c) 2017.
 */

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