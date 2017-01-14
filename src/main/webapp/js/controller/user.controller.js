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
		
		self.gridOptions = {
			appScopeProvider: self,
			enableColumnMenus: false,
		    enableFiltering: true,
		    excludeProperties: '__metadata',
		    onRegisterApi: function(gridApi){
		    	self.gridApi = gridApi;
		    },
		    paginationPageSizes: [9, 25, 50, 100],
		    paginationPageSize: 9,
		    rowHeight:44,
		    columnDefs: [{ field: 'id', 
		    			   displayName: 'ID',
		    			   width: 50
		    			 },
		    			 { field: 'username', 
		    			   displayName: 'Uername'
		    			 },
		    			 { field: 'firstname', 
			    		   displayName: 'First Name'
			    		 },
		    			 { field: 'lastname', 
			    		   displayName: 'Last Name'
			    		 },
		    			 { field: 'email', 
			    		   displayName: 'Email',
			    		   width: 170
			    		 },
		    			 { field: 'roleName', 
			    		   displayName: 'Role',
			    		   width: 70
			    		 },
			    		 { field:'enabled',
			    		   displayName: 'Active',
		    			   width: 70,
			    		   cellTemplate: '<div style="text-align: center;"><i ng-class="row.entity.enabled ? \'fa fa-check acc-active\' : \'fa fa-times acc-inactive\'" aria-hidden="true"></i></div>'
			    		 }]
	    };
		
		$q.all([UserService.list(),
		        UserService.roles()])
			.then(function(data) {
				console.log(data[0]);
				self.users = data[0];
				self.roles = data[1];
				angular.forEach(self.users, function(user){
					setRoleName(user);
				});
				self.gridOptions.data = self.users;
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