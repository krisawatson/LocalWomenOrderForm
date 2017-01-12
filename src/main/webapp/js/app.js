'use strict';
 
var App = angular.module('localWomenApp',[]);
 
App.config(['$httpProvider', function($httpProvider) {
    $httpProvider.interceptors.push('AuthInterceptor');
}]);