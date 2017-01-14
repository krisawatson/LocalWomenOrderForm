'use strict';

angular
.module('localWomenApp')
.directive("userList", UserListDirective);

function UserListDirective() {
	return {
		restrict: "E",
		templateUrl: "tmpl/user/list.html"
	};
}