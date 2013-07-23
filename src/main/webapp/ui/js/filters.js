'use strict';


var module = angular.module('pp2.filters', []);

module.filter('percentage', [ '$filter', function(filter) {
	return function(number, fractionSize) {
		return filter('number')(number * 100, fractionSize) + " %"
	}
} ]);
module.filter('signed', function() {
	return function(number) {
		if (number > 0) {
			return "+" + number;
		} else {
			return number;
		}
	}
});