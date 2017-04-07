/*
 * Kris Watson Copyright (c) 2017.
 */

'use strict';

angular
.module('localWomenApp')
.directive("userCreate", UserCreateDirective);

function UserCreateDirective() {
	return {
		restrict: "E",
		templateUrl: "tmpl/user/create.html"
	};
}