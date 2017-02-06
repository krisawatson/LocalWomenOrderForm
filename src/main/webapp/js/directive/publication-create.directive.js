'use strict';

angular
.module('localWomenApp')
.directive("publicationCreate", PublicationCreateDirective);

function PublicationCreateDirective() {
	return {
		restrict: "E",
		templateUrl: "tmpl/publication/create.html"
	};
}