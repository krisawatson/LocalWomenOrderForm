(function(window){
    'use strict';

    angular.module('localWomenApp').controller('OrderController', [
        '$http',
        '$scope',
        'ngDialog',
        'DetailsService',
        'OrderService',
        Order]);
    
    function Order($http,
                $scope,
                ngDialog,
                DetailsService,
                OrderService) {
        var self = this;
        var now = new Date();
        self.id = 1;
        self.currentMonth = now.getMonth() + 1;
        self.currentYear = now.getFullYear();
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
                       {id:12, name:"December"}]
        
        setStart();
        buildYears();
        getResources();
        
        self.addMoreDetails = addMoreDetails;
        self.submitOrder = submitOrder;
        self.goToNextStep = goToNextStep;
        self.goToPreviousStep = goToPreviousStep;
        self.removeOrder = removeOrder;
        self.reset = setStart;
        
        function addMoreDetails() {
            // For new order set the month to the next one
            var lastMonth = self.orderParts[self.orderParts.length - 1].month;
            var lastYear = self.orderParts[self.orderParts.length - 1].year;
            if(lastMonth == 12) {
                lastMonth = 0;
                ++lastYear;
            }
            var newOrder = {"id": ++self.id, "month": ++lastMonth, "year": lastYear, "publications": []};
            self.orderParts.push(newOrder);
        }
        
        function submitOrder(valid) {
            var atLeastOneOrder = validateOrderParts();
            if(!atLeastOneOrder) {
                self.errorMsg = "At least one advert must be entered in the first 'Order Part'";
            } else {
                delete self.errorMsg;
            }
            valid = valid && atLeastOneOrder;
            if(!valid) return;
            
            var processingDialog = ngDialog.open({
            	template: 'tmpl/order-processing-dialog.html',
            	controller: 'OrderController', 
            	className: 'ngdialog-theme-default'
            });
            
            self.orderError = null;
            fixOrderDetails();
            var order = {
                "business": self.business,
                "orderParts": self.orderParts
            }
            OrderService.create(order).then(function(orderNumber){
                processingDialog.close();
                self.orderNumber = orderNumber;
                self.goToNextStep(valid);
            },function(error){
                processingDialog.close();
                self.orderError = "Something went wrong with your order, please review details and try again";
            });
        }
        
        function goToNextStep(valid) {
            if(valid) {
                self.steps.step = ++self.steps.step;
            }
        }
        
        function goToPreviousStep () {
            self.steps.step = --self.steps.step;
        };
        
        function removeOrder(id) {
            self.orderParts = self.orderParts.filter(function(order) {
                return order.id !== id;
            });
        }
        
        function setStart() {
            var order = {
                "id":self.id,
                "month": self.currentMonth + 1,
                "year": self.currentYear,
                "publications": []
            };
            self.business = {};
            self.orderParts = [order];
            
            self.steps = {
                "step": 1,
                "advertContentStep": 2,
                "maxSteps": 2
            };
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
        }
        
        function buildYears() {
            var years = [];
            for(var i=0;i<3;i++) {
              years.push(self.currentYear + i);
            }
            self.years = years;
        }
        
        function fixOrderDetails() {
            angular.forEach(self.orderParts,function(order){
                delete order.id;
                var publications = []
                angular.forEach(order.publications, function(publication, index){
                    publication.publicationId = index;
                    publications.push(publication);
                });
                order.publications = publications;
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
    }
})(window);
