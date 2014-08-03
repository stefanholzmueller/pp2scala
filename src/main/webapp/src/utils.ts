/// <reference path="../typings/tsd.d.ts" />
'use strict';

var module = angular.module('pp2.utils', []);

module.factory('Util', function() {
	return {
		clone : function(obj) {
			return JSON.parse(JSON.stringify(obj));
		},
		roll : function(pips) {
			return Math.floor((Math.random() * pips) + 1);
		},
		sum : function(collection) {
			return _.reduce(collection, function(acc: number, num) {
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
