(function(window){
    'use strict';

    angular.module('localWomenApp').controller('UserController', [
        '$filter',
        '$q',
        '$scope', 
        'UserService',
        User]);
    
    function User($filter, $q, $scope, UserService) {
        var self = this;
        self.users = [];
        var newUser = {username:'',password:''};
        var createdUser = {};
        angular.copy(newUser, self.user);
        self.create = create;
        self.enableUser = enableUser;
        self.disableUser = disableUser;
        
        function create(valid) {
            delete self.errorMsg;
            delete self.successMsg;
            if(self.user.password != self.confirmPass){
                valid = false;
                self.errorMsg = "Passwords do not match";
                return;
            }
            if(!valid){
                self.errorMsg = "Not all required fields were properly filled out.  Please check and try again";
                return;
            }
            self.user.enabled = true;
            angular.copy(self.user, createdUser);
            UserService.create(self.user).then(
                function(response) {
                    self.successMsg = "Successfully created account '" + self.user.username + "'";
                    setRoleName(createdUser);
                    self.users.push(createdUser);
                    angular.copy(newUser, self.user);
                },
                function(errResponse){
                    if(errResponse.status == 409) {
                        self.errorMsg = "Username already exists, try using a different one";
                    } else {
                        self.errorMsg = "Failed to create account, error status " + errResponse.status;
                    }
                }
            );
        }
        
        function enableUser(user) {
        	user.enabled = true;
        	updateUser(user);
        }
        
        function disableUser(user) {
        	user.enabled = false;
        	updateUser(user);
        }
        function updateUser(user) {
        	delete self.updateErrorMsg;
        	UserService.update(user).then(
        		function(response) {
                    console.log("Successfully enabled user")
                },
                function(errResponse){
                    self.user[user.id].updateErrorMsg = "Failed to update the user";
                }
            );
        }
        
        $q.all([UserService.list(),
                UserService.roles()])
            .then(function(data) {
                console.log(data[0]);
                self.users = data[0];
                self.roles = data[1];
                angular.forEach(self.users, function(user){
                    setRoleName(user);
                });
            });
        
        function setRoleName(user) {
            var role = $filter('filter')(self.roles, function (role) {
                if(role.id === user.roleId){
                    return role.name;
                }
            });
            user.roleName = role[0].name;
        }
    }
})(window);