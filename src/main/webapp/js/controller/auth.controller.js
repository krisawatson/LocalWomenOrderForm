'use strict';
 
angular.module('localWomenApp').controller('AuthController', ['$scope', 'AuthService', function($scope, AuthService) {
    var self = this;
    self.user={username:'',password:''};
    
    self.submit = submit;
 
    function submit() {
        AuthService.login(self.user)
        .then(
            function(d) {
                console.log(d);
            },
            function(errResponse){
                console.error('Error while logging in');
            }
        );
    }
}]);