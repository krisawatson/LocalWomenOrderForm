/*
 * Kris Watson Copyright (c) 2017.
 */

(function(window){
    'use strict';

    angular.module('localWomenApp').factory('NavFactory', [nav]);

	function nav() {

		var tab = 'order';
		
		function getTab() {
			return tab;
		}

		function setTab(value) {
			tab = value;
		}

		return {
			getTab: getTab,
			setTab: setTab
		}
    }
})(window);