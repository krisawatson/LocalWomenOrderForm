'use strict';
angular.module('localWomenApp', [
    'ngDialog',
    'ngRoute',
    'ui.grid',
    'ui.grid.pagination'])
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
            .when('/order/:id/edit', {
            	templateUrl: 'tmpl/order/edit.html',
                controller: 'EditOrderController as editOrderCtrl'
            })
            .when('/search', {
                templateUrl: 'tmpl/search.html',
                controller: 'SearchController as searchCtrl'
            })
            .when('/login', {
                templateUrl: 'tmpl/login.html',
                controller: 'AuthController as authCtrl'
            })
            .when('/user', {
                templateUrl: 'tmpl/user/main.html',
                controller: 'UserController as userCtrl'
            })
            .when('/business/:id/edit', {
                templateUrl: 'tmpl/business/edit.html',
                controller: 'EditBusinessController as editBusinessCtrl'
            })
            .when('/publication', {
                templateUrl: 'tmpl/publication/main.html',
                controller: 'PublicationController as pubCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
        }
    ]);