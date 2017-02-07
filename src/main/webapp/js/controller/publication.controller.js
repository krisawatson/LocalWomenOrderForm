(function(window){
    'use strict';

    angular.module('localWomenApp').controller('PublicationController', [
        '$filter',
        '$q',
        'SortingUtilsFactory',
        'PublicationService',
        'NavFactory',
        Publication]);
    
    function Publication($filter, $q, 
    		SortingUtilsFactory,
    		PublicationService, 
    		NavFactory) {
        var self = this;
        self.publications = [];
        var newPublication = {name:'',email:''};
        var createdPublication = {};
        NavFactory.setTab('publications');
        angular.copy(newPublication, self.publication);
        self.create = create;
        self.clearPublication = clearPublication;
        self.editPublication = editPublication;
        self.updatePublication = updatePublication;
        
        self.gridOptions = {
                appScopeProvider: self,
                autoResize: true,
                enableColumnMenus: false,
                enableFiltering: false,
                excludeProperties: '__metadata',
                onRegisterApi: function(gridApi){
                    self.gridApi = gridApi;
                },
                paginationPageSizes: [9, 25, 50, 100],
                paginationPageSize: 9,
                columnDefs: [{ field: 'id', 
                               displayName: 'ID',
                               width: 50,
           					   sortingAlgorithm: SortingUtilsFactory.sortNumbers
                             },
                             { field: 'name', 
                               displayName: 'Publication'
                             },
                             { field: 'email', 
                               displayName: 'Email Address'
                             },
                             { field: 'id', 
	                           displayName: 'Edit',
                               width: 50,
	                           cellTemplate: '<div class="tbl-cell-order center"><i class="fa fa-pencil" style="font-size:18px;" title="Edit Publication" data-ng-click="grid.appScope.editPublication(row.entity)"></i></div>',
	                           enableFiltering: false
	                         }]
            };
        
        function create(valid) {
            delete self.errorMsg;
            delete self.successMsg;
            if(!valid){
                self.errorMsg = "Not all required fields were properly filled out.  Please check and try again";
                return;
            }
            angular.copy(self.publication, createdPublication);
            PublicationService.create(self.publication).then(
                function(response) {
                    self.successMsg = "Successfully created publication '" + self.publication.name + "'";
                    self.publications.push(createdPublication);
                    angular.copy(newPublication, self.publication);
                },
                function(errResponse){
                    self.errorMsg = "Failed to create publication, error status " + errResponse.status;
                }
            );
        }
        
        function updatePublication(publication) {
        	delete self.successMsg;
        	delete self.errorMsg;
        	PublicationService.update(publication).then(
        		function(response) {
                    self.successMsg = "Successfully updated publication";
                    clearPublication();
                    getPublicationList();
                },
                function(errResponse){
                    self.errorMsg = "Failed to update the publication";
                }
            );
        }
        function editPublication(publication) {
        	self.publication = angular.copy(publication);
        }
        function clearPublication() {
        	delete self.publication;
        }
        function getPublicationList() {
            PublicationService.list().then(function(data){
            	self.publications = data;
            	self.gridOptions.data = angular.copy(self.publications);
            },function(error){
            	console.log(error);
            });
        }
        
        getPublicationList();
    }
})(window);