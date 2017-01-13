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
		
		getResources();
		
		self.gridOptions = {
			appScopeProvider: self,
			enableColumnMenus: false,
		    excludeProperties: '__metadata',
		    onRegisterApi: function(gridApi){
		    	self.gridApi = gridApi;
		    },
		    rowHeight:44,
		    columnDefs: [{ field: 'id', 
		    			   displayName: 'ID',
		    			   width: 50
		    			 },
		    			 { field: 'businessId', 
		    			   displayName: 'Business Name',
		    			   width: 200,
		    			   cellTemplate: '<div>{{grid.appScope.getBusinessName(row.entity.businessId)}}</div>'
		    			 },
		    			 { field: 'orderParts', 
			    		   displayName: 'Order Details'
			    		 },
		    			 { field: 'userId', 
			    		   displayName: 'User',
		    			   width: 120
			    		 }]
	    };
		
		$q.all([DetailsService.publications(), 
			DetailsService.adtypes(), 
			DetailsService.adsizes(),
			OrderService.list()])
			.then(function(data) {
				var publications = data[0];
				var adtypes = data[1];
				var adsizes = data[2];
				self.orders = data[3];
				self.orderList = [];
				
				console.log(self.orders);
				angular.forEach(self.orders, function(order){
					var orders = [];
					var orderItems = {
							orderId: order.id,
							businessName: self.getNameById(self.businesses, order.businessId)
					}
					angular.forEach(order.orderParts, function(orderPart){
						var part = angular.copy(orderItems);
						part.month = orderPart.month;
						part.year = orderPart.year;
						angular.forEach(orderPart.publications, function(publication){
							var pub = angular.copy(part);
							pub.adSize = self.getNameById(self.advertSizes, publication.adSize);
							pub.adType = self.getNameById(self.adverts, publication.adType);
							pub.note = publication.note;
							self.orderList.push(pub);
						});
					});
				});
				console.log(self.orderList);
			});
		
		self.getNameById = function (arrayItems , id) {
			var item = $filter('filter')(arrayItems, function (item) {
				return item.id === id;
			});
			return item[0].name;
		};
		
		function getResources() {
	    	DetailsService.publications().then(function(data){
	    		self.publications = data;
	    	});
	    	
	    	DetailsService.adtypes().then(function (data) {
	    		self.adverts = data;
	    	});
	    	
	    	DetailsService.adsizes().then(function (data) {
	    		self.advertSizes = data;
	    	});
	    	BusinessService.list().then(function(data){
				self.businesses = data;
			});
	    }
	};
})(window);