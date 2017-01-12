'use strict';
 
angular.module('localWomenApp').factory('AuthService', ['$http', '$q', function($http, $q){
 
    var REST_SERVICE_URI = '/auth/';
 
    var factory = {
        login: login
    };
 
    return factory;
 
    function login(user) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, user)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while creating User');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
}]);