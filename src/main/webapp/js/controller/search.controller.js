(function(window){
    'use strict';

    angular.module('localWomenApp').controller('SearchController', [
    	'$filter','$http','$q','$scope',
		'BusinessService','DetailsService','OrderService',
    	Search]);
    
	 function Search($filter,
			 		$http,
		    		$q,
		    		$scope,
		    		BusinessService,
		        	DetailsService,
		        	OrderService) {
		var self = this;
		self.orders = [];
		
		self.gridOptions = {
			appScopeProvider: self,
			enableColumnMenus: false,
		    excludeProperties: '__metadata',
		    onRegisterApi: function(gridApi){
		    	self.gridApi = gridApi;
		    },
		    rowHeight:44,
		    columnDefs: [{ field: 'orderId', 
		    			   displayName: 'ID',
		    			   width: 50
		    			 },
		    			 { field: 'businessName', 
		    			   displayName: 'Business Name',
		    			   width: 200
		    			 },
		    			 { field: 'adSize', 
			    		   displayName: 'Size'
			    		 },
		    			 { field: 'adType', 
			    		   displayName: 'Type'
			    		 },
		    			 { field: 'month', 
			    		   displayName: 'Month'
			    		 },
		    			 { field: 'year', 
			    		   displayName: 'Year'
			    		 },
		    			 { field: 'userId', 
			    		   displayName: 'User'
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
				
				console.log(self.orders);
				angular.forEach(self.orders, function(order){
					var orders = [];
					var orderItems = {
							orderId: order.id,
							businessName: self.getNameById(self.businesses, order.businessId),
							userId: order.userId
					}
					angular.forEach(order.orderParts, function(orderPart){
						var part = angular.copy(orderItems);
						part.month = self.getMonthByInt(orderPart.month);
						part.year = orderPart.year;
						angular.forEach(orderPart.publications, function(publication){
							var pub = angular.copy(part);
							console.log(self.advertSizes);
							console.log(publication.adSize);
							pub.adSize = self.getNameById(self.advertSizes, publication.adSize);
							pub.adType = self.getNameById(self.adverts, publication.adType);
							pub.note = publication.note;
							self.orderList.push(pub);
						});
					});
				});
				console.log(self.orderList);
				self.gridOptions.data = self.orderList;
			});
		
		self.getNameById = function (arrayItems , id) {
			var item = $filter('filter')(arrayItems, function (item) {
				return item.id === id;
			});
			return item[0].name;
		};
		
		self.getMonthByInt = function (month) {
			var monthNames = ["January", "February", "March", "April", "May", "June",
				  "July", "August", "September", "October", "November", "December"
				];
			return monthNames[month-1];
		};
	};
})(window);