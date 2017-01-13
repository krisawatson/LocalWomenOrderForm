'use strict';
angular.module('localWomenApp', ['ngRoute'])
	.config(['$routeProvider',
	    function($routeProvider) {
			
			$routeProvider
			.when('/', {
				templateUrl: 'tmpl/order.html',
				controller: 'OrderController'
			})
			.when('/orderform', {
				templateUrl: 'tmpl/order.html',
				controller: 'OrderController'
			})
			.when('/search', {
				templateUrl: 'tmpl/search.html',
				controller: 'SearchController'
			})
			.when('/login', {
				templateUrl: 'tmpl/login.html',
				controller: 'AuthController'
			})
			.otherwise({
				redirectTo: '/'
			});
		}
	]);