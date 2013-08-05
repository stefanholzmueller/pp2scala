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
		sidewind : "",
		fast : true,
		second : false,
		other : 0
	};

	$scope.character = {
		sf : {
			shooter : "master" // "sharp"
		},
		weapon : {
			type : "shoot" // "sling", "throw"
		}
	};

	function sum(collection) {
		return _.reduce(collection, function(acc, num) {
			return acc + num;
		}, 0);
	}

	function recalculate(newValue) {
		$scope.difficulty = service.calculateDifficulty(newValue[0], newValue[1]);
		$scope.difficultySum = sum($scope.difficulty);
	}

	$scope.$watch('[ modifications, character ]', recalculate, true);
} ]);

module.factory('RangedService', function() {
	return {
		calculateDifficulty : function(modifications, character) {
			function lookup(key, map, otherwise) {
				return map.hasOwnProperty(key) ? map[key] : otherwise;
			}

			return {
				size : modifications.size.difficulty,
				range : modifications.range.difficulty,
				movement : lookup(modifications.movement.type, {
					"target" : modifications.movement.target.difficulty,
					"combat" : modifications.movement.combat.h * 3 + modifications.movement.combat.ns * 2
				}),
				sight : modifications.sight.difficulty,
				steep : character.sf.shooter === "master" ? 0 : lookup(modifications.steep, {
					"down" : character.weapon.type === "sling" ? 8 : 2,
					"up" : character.weapon.type === "throw" ? 8 : 4
				}, 0),
				sidewind : character.sf.shooter === "master" ? 0 : lookup(modifications.sidewind, {
					"normal" : 4,
					"strong" : 8
				}, 0),
				fast : modifications.fast ? lookup(character.sf.shooter, {
					"master" : 0,
					"sharp" : 1
				}, 2) : 0,
				second : modifications.second ? (character.weapon.type === "throw" ? 2 : 4) : 0,
				other : modifications.other
			};
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
