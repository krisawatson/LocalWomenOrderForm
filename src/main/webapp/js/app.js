'use strict';
angular.module('localWomenApp', [
	'ngRoute',
	'ui.grid'])
	.config(['$routeProvider',
	    function($routeProvider) {
			
			$routeProvider
			.when('/', {
				templateUrl: 'tmpl/order.html',
				controller: 'OrderController as orderCtrl'
			})
			.when('/orderform', {
				templateUrl: 'tmpl/order.html',
				controller: 'OrderController as orderCtrl'
			})
			.when('/search', {
				templateUrl: 'tmpl/search.html',
				controller: 'SearchController as searchCtrl'
			})
			.when('/login', {
				templateUrl: 'tmpl/login.html',
				controller: 'AuthController as authCtrl'
			})
			.otherwise({
				redirectTo: '/'
			});
		}
	]);