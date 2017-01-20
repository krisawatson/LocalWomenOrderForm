(function(window){
    'use strict';

    angular.module('localWomenApp').controller('UserController', [
        '$filter',
        '$q',
        'UserService',
        User]);
    
    function User($filter, $q, UserService) {
        var self = this;
        self.users = [];
        var newUser = {username:'',password:''};
        var createdUser = {};
        angular.copy(newUser, self.user);
        self.create = create;
        self.clearUser = clearUser;
        self.disableUser = disableUser;
        self.editUser = editUser;
        self.enableUser = enableUser;
        self.updateUser = updateUser;
        
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
        	console.log(user);
        	user.enabled = false;
        	updateUser(user);
        }
        function updateUser(user) {
        	delete self.successMsg;
        	delete self.errorMsg;
        	UserService.update(user).then(
        		function(response) {
                    self.successMsg = "Successfully updated user";
                    clearUser();
                    getUserList();
                },
                function(errResponse){
                    self.errorMsg = "Failed to update the user";
                }
            );
        }
        function editUser(user) {
        	self.user = angular.copy(user);
        }
        function clearUser() {
        	delete self.user;
        }
        
        $q.all([UserService.get(),
                UserService.roles()])
            .then(function(data) {
            	getUserList();
                self.currentUser = data[0];
                self.roles = data[1];
            });
        
        function getUserList() {
            UserService.list().then(function(data){
            	self.users = data;
            	angular.forEach(self.users, function(user){
                	delete user.password;
                    setRoleName(user);
                });
            },function(error){
            	console.log(error);
            });
        }
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