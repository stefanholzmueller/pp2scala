/// <reference path="../../typings/tsd.d.ts" />
/// <reference path='evaluate.ts'/>

module check {

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
}
