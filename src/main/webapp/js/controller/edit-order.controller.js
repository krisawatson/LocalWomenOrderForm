(function(window){
    angular.module('localWomenApp').controller('EditOrderController', [
        '$http',
        '$location',
        '$scope',
        'ngDialog',
        'DetailsService',
        'OrderService',
        EditOrder]);
    
    function EditOrder($http,
    			$location,
                $scope,
                ngDialog,
                DetailsService,
                OrderService) {
        var self = this;
        var pathParts = $location.path().split("/");
        var orderId = pathParts[2];
        getOrderDetails();
        var now = new Date();
        self.months = [{id:1, name:"January"},
                        {id:2, name:"February"},
                        {id:3, name:"March"},
                        {id:4, name:"April"},
                        {id:5, name:"May"},
                        {id:6, name:"June"},
                        {id:7, name:"July"},
                        {id:8, name:"August"},
                        {id:9, name:"September"},
                        {id:10, name:"October"},
                        {id:11, name:"November"},
                        {id:12, name:"December"}];

        self.currentMonth = now.getMonth() + 1;
        self.currentYear = now.getFullYear();
        
        buildYears();
        getResources();
        
        self.addMoreDetails = addMoreDetails;
        self.update = update;
        self.removeOrder = removeOrder;
        
        function addMoreDetails() {
            // For new order set the month to the next one
            var lastMonth = self.orderParts[self.orderParts.length - 1].month;
            var lastYear = self.orderParts[self.orderParts.length - 1].year;
            var lastPartId = self.orderParts[self.orderParts.length - 1].id;
            if(lastMonth == 12) {
                lastMonth = 0;
                ++lastYear;
            }
            ++lastMonth;
            var newOrder = {"id": ++lastPartId, 
            		"month": lastMonth, 
            		"year": lastYear, 
            		"publications": [],
            		"isNew": true};
            self.orderParts.push(newOrder);
        }
        
        function update(valid) {
        	delete self.successMsg;
        	delete self.errorMsg;
        	if(!valid) return;
        	self.updateOrder = angular.copy(self.order)
        	fixOrderDetails();
        	OrderService.update(self.order).then(function(){
                self.successMsg("Successfully updated order");
            },function(error){
                self.errorMsg;
            });
        }
        
        function removeOrder(id) {
        	self.orderParts = self.orderParts.filter(function(orderPart) {
                return orderPart.id !== id;
            });
        }
        
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
        }
        
        function getOrderDetails() {
        	OrderService.get(orderId).then(
    			function(data){
    				self.order = data;
    				self.orderParts = self.order.orderParts;
    				self.idx = self.orderParts.length;
    				setSelectedPublications();
    			},
    			function(){
    				console.log("Failed to get order");
    			}
    		);
        }
        
        function fixOrderDetails() {
        	console.log(self.updateOrder.orderParts);
            angular.forEach(self.updateOrder.orderParts,function(orderPart){
            	if(orderPart.isNew) {
            		delete orderPart.id;
            	}
                var pubs = []
                angular.forEach(orderPart.publications, function(publication, index){
                	if(publication && publication.selected) {
                		publication.publicationId = ++index;
	                    pubs.push(publication);
                	}
                });
                orderPart.publications = pubs;
            });
        }
        
        function validateOrderParts() {
            var valid = false;
            if(self.orderParts[0].publications.length > 0) {
                angular.forEach(self.orderParts[0].publications, function(pub){
                    if(pub.selected) {
                        valid = true;
                    }
                });
            }
            return valid;
        }
        
        function setSelectedPublications() {
        	angular.forEach(self.orderParts,function(orderPart){
        		var pubs = [];
        		angular.forEach(orderPart.publications, function(orderPub, index){
        			angular.forEach(self.publications, function(pub){
        				if(orderPub.publicationId == pub.id){
        					orderPub.selected = true;
        					pubs.push(orderPub);
        				} else {
        					pubs.push(pub);
        				}
        			});
        			orderPart.publications = pubs;
                });
            });
        }

        function buildYears() {
            var years = [];
            for(var i=0;i<3;i++) {
              years.push(self.currentYear + i);
            }
            self.years = years;
        }
    }
})(window);
