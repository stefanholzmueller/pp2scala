/// <reference path="../../typings/tsd.d.ts" />
/// <reference path='evaluate.ts'/>

module Checks {

	export interface Check {
		attributes : Array<number>;
		value : number;
		difficulty : number;
		options : {
			minimumQuality : boolean;
			festeMatrix? : boolean;
			wildeMagie? : boolean;
			spruchhemmung? : boolean;
		};
	}

	var MAX_PIPS = 20;
	var COMBINATIONS = buildCombinations();

	function buildCombinations() {
		var combinations = [];
		for (var die1 = 1; die1 <= MAX_PIPS; die1++) {
			for (var die2 = 1; die2 <= MAX_PIPS; die2++) {
				for (var die3 = 1; die3 <= MAX_PIPS; die3++) {
					combinations.push([die1, die2, die3]);
				}
			}
		}
		Object.freeze(combinations);
		return combinations;
	}

	export function calculate(options, attributes : Array<number>, value : number, difficulty : number) {
		var evaluator = _.partial(evaluate, options, attributes, value, difficulty);
		var outcomes = _.map(COMBINATIONS, dice => evaluator(dice));

		var successes = _.filter(outcomes, o => o.success);
		var totalQuality : number = _.reduce(successes, function (sum, success : { quality : number }) {
			return sum + success.quality;
		}, 0);

		var probability = successes.length / outcomes.length;
		var average = totalQuality / successes.length;

		return { probability: probability, average: average };
	}

	export function calculatePartitioned(check : Check) : Array<{count: number}> {
		var evaluator = _.partial(evaluate, check.options, check.attributes, check.value, check.difficulty);
		var outcomes = _.map(COMBINATIONS, dice => evaluator(dice));
		var successes = _.filter(outcomes, "success");
		var counts = _.countBy(successes, "quality"); // { '0': 123, '1': 234 }
		var pairs = _.pairs(counts);
		var sorted = _.sortBy(pairs, function (a, b) {
			return a[0] - b[0];
		}).reverse();
		var partitions = _.map(sorted, function (x) {
			return { quality: x[0], count: x[1]};
		});

		return [
			{label: "gelungen", count: successes.length, partitions: partitions},
			{label: "misslungen", count: 8000 - successes.length}
		];
	}

	export var calculatePartitionedMemoized = _.memoize(calculatePartitioned, function(check) { // simple cache key
		return _.sortBy(check.attributes) + "|" + check.value + "|" + check.difficulty + "|" + check.options.minimumQuality;
	});

}
