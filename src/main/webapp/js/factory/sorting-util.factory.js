(function(window){
    'use strict';

    angular.module('localWomenApp').factory('SortingUtilsFactory', [sorting]);

	function sorting() {

		function sortNumbers(a, b) {
			if(!a) {
				a = 0;
			}
			if(!b) {
				b = 0;
			}

			if (a === b) {
				return 0;
			}

			return  a > b ? 1 : -1;
		}

		function sortStringNumbers(a, b) {
			if(typeof a === 'string') {
				a = parseInt(a.replace(',',''));
			}
			if(typeof b === 'string') {
				b = parseInt(b.replace(',',''));
			}
			return sortNumbers(a, b);
		}

		function sortStringNumbersWithValidFirst(a, b) {
			if(typeof a === 'string') {
				a = parseInt(a.replace(',',''));
			}
			if (!a) {
				a = Number.MAX_SAFE_INTEGER;
			}
			if(typeof b === 'string') {
				b = parseInt(b.replace(',',''));
			}
			if (!b) {
				b = Number.MAX_SAFE_INTEGER;
			}
			return sortNumbers(a, b);
		}

		function sortCurrencyNumbers(a, b) {
			if(typeof a === 'string') {
				// Remove any non number values except . 
				a = Number(a.replace(/[^0-9\.]+/g,''));
			}
			if(typeof b === 'string') {
				// Remove any non number values except .
				b = Number(b.replace(/[^0-9\.]+/g,''));
			}
			return sortFloatString(a, b);
		}

		function sortFloatString(a, b) {
			if(typeof a === 'string') {
				a = parseFloat(a.replace(',',''));
			}
			if(typeof b === 'string') {
				b = parseFloat(b.replace(',',''));
			}
			return sortNumbers(a, b);
		}

		function sortInlineEdit(a, b) {
			if (!a || !b) {
				return 0;
			}
			if (a.value > b.value) {
				return a;
			}
			else {
				return b;
			}
		}

		function sortReach(a, b) {

			if(!a || !b) {return 0;}

			if (a.number < b.number) {
				return -1;
			}
			if (a.number > b.number) {
				return 1;
			}

			return 0;
		}

		function sortSpend(a, b) {
			var result;

			if (!a || !b) {
				return 0;
			}
			if (a.spend === b.spend) {
				return 0;
			}

			return a.spend > b.spend ? 1 : -1;
		}

		return {
			sortNumbers: sortNumbers,
			sortStringNumbers: sortStringNumbers,
			sortCurrencyNumbers: sortCurrencyNumbers,
			sortFloatString: sortFloatString
		}
    }
})(window);