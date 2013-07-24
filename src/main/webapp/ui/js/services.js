'use strict';

var module = angular.module('pp2.services', []);

module.factory('RangedService', function() {
	return {
		calculate : function(options) {
			return {
				size : options.size.difficulty,
				range : options.range.difficulty,
				other : options.other
			};
		},
		options : {
			size : Object.freeze([ {
				index : 0,
				text : "winzig",
				difficulty : 8
			}, {
				index : 1,
				text : "sehr klein",
				difficulty : 6
			}, {
				index : 2,
				text : "klein",
				difficulty : 4
			}, {
				index : 3,
				text : "mittel",
				difficulty : 2
			}, {
				index : 4,
				text : "groß",
				difficulty : 0
			}, {
				index : 5,
				text : "sehr groß",
				difficulty : -2
			} ]),

			range : Object.freeze([ {
				index : 0,
				text : "sehr nah",
				difficulty : -2
			}, {
				index : 1,
				text : "nah",
				difficulty : 0
			}, {
				index : 2,
				text : "mittel",
				difficulty : 4
			}, {
				index : 3,
				text : "weit",
				difficulty : 8
			}, {
				index : 4,
				text : "extrem weit",
				difficulty : 12
			} ])
		}
	};
});