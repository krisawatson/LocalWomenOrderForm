(function(window){
    'use strict';

    angular.module('localWomenApp').controller('UserController', [
    	'$scope', 
    	'$q',
    	'UserService',
    	User]);
    
	function User($scope, $q, UserService) {
	    var self = this;
	    self.users = [];
	    var newUser = {username:'',password:''};
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
	    	UserService.create(self.user).then(
	        	function(response) {
	        		self.successMsg = "Successfully created account '" + self.user.username + "'";
	        		self.users.push(self.user);
	        		self.gridOptions.data = self.users;
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
		    			 { field: 'role', 
			    		   displayName: 'Role',
			    		   width: 70
			    		 },
			    		 { field:'enabled',
			    		   displayName: 'Active',
		    			   width: 70,
			    		   cellTemplate: '<div style="text-align: center;"><i ng-class="row.entity.enabled ? \'fa fa-check acc-active\' : \'fa fa-times acc-inactive\'" aria-hidden="true"></i></div>'
			    		 }]
	    };
		
		$q.all([UserService.roles()])
			.then(function(data) {
				console.log(data[0]);
				self.roles = data[0];
				self.users = [];
				angular.forEach(self.roles, function(role){
					angular.forEach(role.users, function(user){
						user.role = role.name;
						self.users.push(user);
					});
				});
				self.gridOptions.data = self.users;
			});
	}
})(window);