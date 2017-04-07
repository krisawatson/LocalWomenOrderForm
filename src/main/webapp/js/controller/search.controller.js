/*
 * Kris Watson Copyright (c) 2017.
 */

(function(window){
    'use strict';

    angular.module('localWomenApp').controller('SearchController', [
        '$filter','$http','$location', '$q','$scope',
        'BusinessService','DetailsService','OrderService',
        'SortingUtilsFactory','uiGridConstants','NavFactory',
        'UserService',Search]);
    
    function Search($filter,
                    $http,
                    $location,
                    $q,
                    $scope,
                    BusinessService,
                    DetailsService,
                    OrderService,
                    SortingUtilsFactory,
                    uiGridConstants,
                    NavFactory,
                    UserService) {
        var self = this;
        var months = [{value: 1,
        			   label: 'January'
        			  },{value: 2,
        			   label: 'February'
        			  },{value: 3,
        			   label: 'March'
        			  },{value: 4,
        			   label: 'April'
        			  },{value: 5,
        			   label: 'May'
        			  },{value: 6,
        			   label: 'June'
        			  },{value: 7,
        			   label: 'July'
        			  },{value: 8,
        			   label: 'August'
        			  },{value: 9,
        			   label: 'September'
        			  },{value: 10,
        			   label: 'October'
        			  },{value: 11,
        			   label: 'November'
        			  },{value: 12,
        			   label: 'December'
        			  }];
        NavFactory.setTab('search');
        self.orderList = [];
        self.inProgressOrders = [];
        self.finishedOrders = [];
        self.orderFilters = [{
        	'id': 1,
        	'name': 'All'
        },{
        	'id': 2,
        	'name': 'In Progress'
        },{
        	'id': 3,
        	'name': 'Finished'
        }];
        self.filter = 1;
        self.editBusiness = editBusiness;
        self.editOrder = editOrder;
        self.canEdit = canEdit;
        self.getMonthByInt = getMonthByInt;
        self.filterOrderList = filterOrderList;
        self.filterTooltip = "You can filter the list of orders by it's current status";
        
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
                           headerTooltip:'Order ID',
                           width: 50,
       					   sortingAlgorithm: SortingUtilsFactory.sortNumbers
                         },
                         { field: 'businessName', 
                           displayName: 'Business Name',
                           headerTooltip:'Business Name',
                           cellTemplate: '<div class="tbl-cell-business">{{row.entity.businessName}}<i class="fa fa-pencil right" title="Edit {{row.entity.businessName}}" data-ng-if="grid.appScope.canEdit(row.entity.userId)" data-ng-click="grid.appScope.editBusiness(row.entity.businessId)"></i></div>',
                           width: 150
                         },
                         { field: 'name', 
                           displayName: 'Publication',
                           headerTooltip:'Publication',
                           width: 120
                         },
                         { field: 'adType', 
                           displayName: 'Type',
                           headerTooltip:'Type'
                         },
                         { field: 'adSize', 
                           displayName: 'Size',
                           headerTooltip:'Size'
                         },
                         { field: 'month', 
                           displayName: 'Month',
                           headerTooltip:'Month',
                           cellTemplate: '<div class="ui-grid-cell-contents" >{{grid.appScope.getMonthByInt(grid.getCellValue(row, col))}}</div>',
                           filter: {
                        	   type: uiGridConstants.filter.SELECT,
                        	   selectOptions: months
                           },
                           sort: {direction: 'asc', priority: 0}
                         },
                         { field: 'year', 
                           displayName: 'Year',
                           headerTooltip:'Year',
                           sort: {direction: 'asc', priority: 1}
                         },
                         { field: 'userId', 
                           displayName: 'User ID',
                           headerTooltip:'User ID',
                           width: 50,
                           sortingAlgorithm: SortingUtilsFactory.sortNumbers
                         },
                         { field: 'priceExVat', 
                           displayName: 'Price',
                           headerTooltip:'Price Excluding / Including VAT',
                           cellTemplate: '<div class="ui-grid-cell-contents">{{row.entity.priceExVat}}<br/>{{row.entity.priceIncVat}}</div>',
                           enableFiltering: false,
                           visible: false
                         },
                         { field: 'deposit', 
                           displayName: 'Deposit',
                           headerTooltip:'Deposit',
                           enableFiltering: false,
                           visible: false
                         },
                         { field: 'userId', 
                           displayName: '',
                           cellTemplate: '<div class="tbl-cell-order center"><i class="fa fa-pencil" style="font-size:18px;" title="Edit Order" data-ng-if="grid.appScope.canEdit(row.entity.userId)" data-ng-click="grid.appScope.editOrder(row.entity.orderId)"></i></div>',
                           enableFiltering: false,
                           width: 50
                         }]
        };

        $q.all([BusinessService.list(),
            DetailsService.publications(), 
            DetailsService.adtypes(), 
            DetailsService.adsizes(),
            OrderService.list(),
            UserService.get(),
            UserService.getId()])
            .then(function(data) {
                self.businesses = data[0];
                self.publications = data[1];
                self.adverts = data[2];
                self.advertSizes = data[3];
                fillOrderListDetails(data[4]);
                self.user = data[5];
                self.user.id = data[6];
                if(self.user.authorities[0].authority === 'ADMIN') {
                	self.gridOptions.columnDefs[8].visible = true;
                    self.gridOptions.columnDefs[9].visible = true;
                }
            });
        
        function editBusiness(businessId) {
        	$location.path('/business/' + businessId + '/edit');
        }
        
        function editOrder(orderId) {
        	$location.path('/order/' + orderId + '/edit');
        }
        
        function getMonthByInt(monthValue) {
            return getLabelByValue(months, monthValue);
        }
        
        function canEdit(userId) {
        	return self.user.authorities[0].authority === 'ADMIN' || self.user.id === userId;

        }
        function filterOrderList() {
        	if(self.filter === 2) {
        		self.gridOptions.data = angular.copy(self.inProgressOrders);
        	} else if (self.filter === 3) {
        		self.gridOptions.data = angular.copy(self.finishedOrders);
        	} else {
        		self.gridOptions.data = angular.copy(self.showOrders);
        	}
        }
        
        function fillOrderListDetails(orders) {
        	angular.forEach(orders, function(order){
                var orderItems = {
                        orderId: order.id,
                        businessId: order.businessId,
                        businessName: getNameById(self.businesses, order.businessId),
                        userId: order.userId,
                        priceExVat: order.priceExVat.toFixed(2),
                        priceIncVat: order.priceIncVat.toFixed(2),
                        deposit: order.deposit.toFixed(2)
                };
                angular.forEach(order.orderParts, function(orderPart){
                    var part = angular.copy(orderItems);
                    part.month = orderPart.month;
                    part.year = orderPart.year;
                    var isActive = isActivePart(part.month, part.year);
                    angular.forEach(orderPart.publications, function(publication){
                        var pub = angular.copy(part);
                        pub.name = getNameById(self.publications, publication.publicationId);
                        pub.adSize = getNameById(self.advertSizes, publication.adSize);
                        pub.adType = getNameById(self.adverts, publication.adType);
                        pub.note = publication.note;
                        self.orderList.push(pub);
                        if(isActive) {
                        	self.inProgressOrders.push(pub);
                        } else {
                        	self.finishedOrders.push(pub);
                        }
                    });
                });
            });
        	
        	self.showOrders = angular.copy(self.orderList);
            self.gridOptions.data = self.showOrders;
        }
        
        var getNameById = function (arrayItems , id) {
        	var name;
            var item = $filter('filter')(arrayItems, function (item) {
                return item.id === id;
            });
            if(item && item[0]) {
            	name = item[0].name;
            }
            return name;
        };
        
        var getLabelByValue = function (arrayItems , value) {
        	var label;
            var item = $filter('filter')(arrayItems, function (item) {
                return item.value === value;
            });
            if(item && item[0]) {
            	label = item[0].label;
            }
            return label;
        };
        
        var isActivePart = function(month, year) {
        	var now = new Date();
        	var currentMonth = now.getMonth() + 1;
        	var currentYear = now.getFullYear();
        	var isActive = false;
        	if(year > currentYear || (month >= currentMonth && year == currentYear)) {
        		isActive = true;
        	}
        	return isActive;
        }
    }
})(window);