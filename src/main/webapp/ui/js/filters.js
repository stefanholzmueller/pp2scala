'use strict';

/* Filters */

angular.module('ppCheck.filters', [])
	.filter('percentage', [ '$filter', function(filter) {
	    return function(number, fractionSize) {
		return filter('number')(number * 100, fractionSize) + " %"
	    }
	}])
	.filter('signed', function() {
	    return function(number) {
		if (number > 0) {
		    return "+" + number;
		} else {
		    return number;
		}
	    }
	});