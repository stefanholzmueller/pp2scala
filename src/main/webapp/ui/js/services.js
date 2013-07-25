'use strict';

var module = angular.module('pp2.services', []);

module.factory('RangedService', function() {
	return {
		calculate : function(options, character) {
			var difficulty = {};

			switch (options.movement.type) {
				case "target":
					difficulty.movement = options.movement.target.difficulty;
					break;
				case "combat":
					difficulty.movement = options.movement.combat.h * 3 + options.movement.combat.ns * 2;
					break;
			}

			switch (options.steep) {
				case "down":
					difficulty.steep = 2;
					break;
				case "up":
					character = {}; // HACK
					difficulty.steep = character.hasThrowingWeapon ? 8 : 4; // HACK
					break;
				default:
					difficulty.steep = 0;
			}

			difficulty.size = options.size.difficulty;
			difficulty.range = options.range.difficulty;
			difficulty.other = options.other;
			return difficulty;
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
			} ]),

			movement : Object.freeze([ {
				index : 0,
				text : "unbewegliches / fest montiertes Ziel",
				difficulty : -4
			}, {
				index : 1,
				text : "stillstehendes Ziel",
				difficulty : -2
			}, {
				index : 2,
				text : "leichte Bewegung des Ziels",
				difficulty : 0
			}, {
				index : 3,
				text : "schnelle Bewegung des Ziels",
				difficulty : 2
			}, {
				index : 4,
				text : "sehr schnell / Ausweichbewegungen",
				difficulty : 4
			} ])
		}
	};
});