'use strict';

var module = angular.module('pp2.utils', []);
module.factory('Util', function() {
	return {
		clone : function(obj) {
			return JSON.parse(JSON.stringify(obj));
		},
		sum : function(collection) {
			return _.reduce(collection, function(acc, num) {
				return acc + num;
			}, 0);
		}
	}
});
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