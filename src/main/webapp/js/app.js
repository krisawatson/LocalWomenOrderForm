'use strict';
angular.module('localWomenApp', ['ngRoute'])
	.config(['$routeProvider',
	    function($routeProvider) {
			
			$routeProvider
			.when('/', {
				templateUrl: 'tmpl/order.html',
				controller: 'OrderController'
			})
			.when('/orderform.do', {
				templateUrl: 'tmpl/order.html',
				controller: 'OrderController'
			})
			.when('/search.do', {
				templateUrl: 'tmpl/search.html',
				controller: 'SearchController'
			})
			.when('/login.do', {
				templateUrl: 'tmpl/login.html',
				controller: 'AuthController'
			})
			.otherwise({
				redirectTo: '/'
			});
		}
	]);