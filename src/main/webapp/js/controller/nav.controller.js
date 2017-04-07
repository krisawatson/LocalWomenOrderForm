/*
 * Kris Watson Copyright (c) 2017.
 */

(function(window){
    'use strict';

    angular.module('localWomenApp').controller('NavController', [
    	'NavFactory',
    	Nav]);
    
    function Nav(NavFactory) {
    	var self = this;
        self.isSet = isSet;
        
        function isSet(tabName){
          return NavFactory.getTab() === tabName;
        }
    }
})(window);