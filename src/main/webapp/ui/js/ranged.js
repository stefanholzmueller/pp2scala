'use strict';

var module = angular.module('pp2.ranged', [ 'pp2.filters' ]);

module.controller('RangedController', [ '$scope', 'RangedService', function($scope, service) {
	$scope.options = service.options;

	$scope.modifications = {
		size : service.options.size[3],
		range : service.options.range[1],
		movement : {
			type : "target",
			target : service.options.movement[2],
			combat : {
				h : 0,
				ns : 0
			}
		},
		sight : service.options.sight[0],
		steep : "",
		second : false,
		other : 0
	};

	function sum(collection) {
		return _.reduce(collection, function(acc, num) {
			return acc + num;
		}, 0);
	}

	function recalculate(newValue) {
		$scope.difficulty = service.calculate(newValue);
		$scope.difficultySum = sum($scope.difficulty);
	}

	$scope.$watch('modifications', recalculate, true);
} ]);

module.factory('RangedService', function() {
	return {
		calculate : function(modifications, character) {
			character = {}; // HACK
			var difficulty = {};

			switch (modifications.movement.type) {
				case "target":
					difficulty.movement = modifications.movement.target.difficulty;
					break;
				case "combat":
					difficulty.movement = modifications.movement.combat.h * 3 + modifications.movement.combat.ns * 2;
					break;
			}

			switch (modifications.steep) {
				case "down":
					difficulty.steep = character.hasSlingWeapon ? 8 : 2; // HACK
					break;
				case "up":
					difficulty.steep = character.hasThrowingWeapon ? 8 : 4; // HACK
					break;
				default:
					difficulty.steep = 0;
			}

			switch (modifications.sidewind) { // TODO as map
				case "normal":
					difficulty.sidewind = 4;
					break;
				case "strong":
					difficulty.sidewind = 8;
					break;
				default:
					difficulty.sidewind = 0;
			}

			difficulty.sight = modifications.sight.difficulty;
			difficulty.size = modifications.size.difficulty;
			difficulty.range = modifications.range.difficulty;
			difficulty.fast = modifications.fast ? 2 : 0; // TODO N/S/M
			difficulty.second = modifications.second ? character.hasThrowingWeapon ? 2 : 4 : 0;
			difficulty.other = modifications.other;
			return difficulty;
		},
		options : {
			size : Object.freeze([ {
				text : "winzig",
				difficulty : 8
			}, {
				text : "sehr klein",
				difficulty : 6
			}, {
				text : "klein",
				difficulty : 4
			}, {
				text : "mittel",
				difficulty : 2
			}, {
				text : "groß",
				difficulty : 0
			}, {
				text : "sehr groß",
				difficulty : -2
			} ]),

			range : Object.freeze([ {
				text : "sehr nah",
				difficulty : -2
			}, {
				text : "nah",
				difficulty : 0
			}, {
				text : "mittel",
				difficulty : 4
			}, {
				text : "weit",
				difficulty : 8
			}, {
				text : "extrem weit",
				difficulty : 12
			} ]),

			movement : Object.freeze([ {
				text : "unbewegliches / fest montiertes Ziel",
				difficulty : -4
			}, {
				text : "stillstehendes Ziel",
				difficulty : -2
			}, {
				text : "leichte Bewegung des Ziels",
				difficulty : 0
			}, {
				text : "schnelle Bewegung des Ziels",
				difficulty : 2
			}, {
				text : "sehr schnell / Ausweichbewegungen",
				difficulty : 4
			} ]),

			sight : Object.freeze([ {
				text : "normal",
				difficulty : 0
			}, {
				text : "Dunst",
				difficulty : 2
			}, {
				text : "Nebel",
				difficulty : 4
			}, { // TODO missing attribute 'darkness : true'
				text : "Dämmerung",
				difficulty : 2
			}, {
				text : "Mondlicht",
				difficulty : 4
			}, {
				text : "Sternenlicht",
				difficulty : 6
			}, {
				text : "Finsternis",
				difficulty : 8
			}, {
				text : "Unsichtbares Ziel",
				difficulty : 8
			} ])
		}
	};
});
