(function(window){
    'use strict';

    angular.module('localWomenApp').controller('SearchController', [
        '$filter','$http','$location', '$q','$scope',
        'BusinessService','DetailsService','OrderService',
        Search]);
    
    function Search($filter,
                    $http,
                    $location,
                    $q,
                    $scope,
                    BusinessService,
                    DetailsService,
                    OrderService) {
        var self = this;
        self.orders = [];
        self.editBusiness = editBusiness;
        self.editOrder = editOrder;
        
        self.gridOptions = {
            appScopeProvider: self,
            autoResize: true,
            enableColumnMenus: false,
            enableFiltering: true,
            excludeProperties: '__metadata',
            onRegisterApi: function(gridApi){
                self.gridApi = gridApi;
            },
            paginationPageSizes: [9, 25, 50, 100],
            paginationPageSize: 9,
            rowHeight:44,
            columnDefs: [{ field: 'orderId', 
                           displayName: 'ID',
                           width: 50
                         },
                         { field: 'businessName', 
                           displayName: 'Business Name',
                           cellTemplate: '<div class="tbl-cell-business">{{row.entity.businessName}}<i class="fa fa-pencil right" title="Edit {{row.entity.businessName}}" data-ng-click="grid.appScope.editBusiness(row.entity.businessId)"></i></div>',
                           width: 150
                         },
                         { field: 'name', 
                           displayName: 'Publication',
                           width: 120
                         },
                         { field: 'adType', 
                           displayName: 'Type'
                         },
                         { field: 'adSize', 
                           displayName: 'Size'
                         },
                         { field: 'month', 
                           displayName: 'Month'
                         },
                         { field: 'year', 
                           displayName: 'Year'
                         },
                         { field: 'orderId', 
                           displayName: 'Edit',
                           cellTemplate: '<div class="tbl-cell-order center"><i class="fa fa-pencil" style="font-size:18px;" title="Edit Order" data-ng-click="grid.appScope.editOrder(row.entity.orderId)"></i></div>',
                           enableFiltering: false
                         }]
        };
        
        $q.all([BusinessService.list(),
            DetailsService.publications(), 
            DetailsService.adtypes(), 
            DetailsService.adsizes(),
            OrderService.list()])
            .then(function(data) {
                self.businesses = data[0];
                self.publications = data[1];
                self.adverts = data[2];
                self.advertSizes = data[3];
                self.orders = data[4];
                self.orderList = [];
                
                angular.forEach(self.orders, function(order){
                    var orders = [];
                    var orderItems = {
                            orderId: order.id,
                            businessId: order.businessId,
                            businessName: getNameById(self.businesses, order.businessId),
                            userId: order.userId
                    }
                    angular.forEach(order.orderParts, function(orderPart){
                        var part = angular.copy(orderItems);
                        part.month = getMonthByInt(orderPart.month);
                        part.year = orderPart.year;
                        angular.forEach(orderPart.publications, function(publication){
                            var pub = angular.copy(part);
                            pub.name = getNameById(self.publications, publication.publicationId);
                            pub.adSize = getNameById(self.advertSizes, publication.adSize);
                            pub.adType = getNameById(self.adverts, publication.adType);
                            pub.note = publication.note;
                            self.orderList.push(pub);
                        });
                    });
                });
                self.gridOptions.data = self.orderList;
            });
        
        function editBusiness(businessId) {
        	$location.path('/business/' + businessId + '/edit');
        }
        
        function editOrder(orderId) {
        	$location.path('/order/' + orderId + '/edit');
        }
        
        var getNameById = function (arrayItems , id) {
            var item = $filter('filter')(arrayItems, function (item) {
                return item.id === id;
            });
            return item[0].name;
        };
        
        var getMonthByInt = function (month) {
            var monthNames = ["January", "February", "March", "April", "May", "June",
                  "July", "August", "September", "October", "November", "December"
                ];
            return monthNames[month-1];
        };
    };
})(window);