/// <reference path="../../typings/tsd.d.ts" />

module Checks {

	export function evaluate(options, attributes : Array<number>, value : number, difficulty : number, dice : Array<number>) : any {
		var special = specialOutcome(options, value, dice);
		if (special) {
			return special;
		} else {
			return successOrFailure(options.minimumQuality, attributes, value, difficulty, dice);
		}
	}

	function successOrFailure(minimumQuality : boolean, attributes : Array<number>, value : number, difficulty : number, dice : Array<number>) {
		var ease = value - difficulty;
		var effectiveValue = Math.max(ease, 0);
		var effectiveAttributes = ease < 0 ? _.map(attributes, a => a + ease) : attributes;
		return successOrFailureInternal(minimumQuality, effectiveAttributes, effectiveValue, value, dice);
	}

	function successOrFailureInternal(minimumQuality : boolean, effectiveAttributes : Array<number>, effectiveValue : number, value : number, dice : Array<number>) {
		var comparisions = [ dice[0] - effectiveAttributes[0], dice[1] - effectiveAttributes[1], dice[2] - effectiveAttributes[2] ];
		var usedPoints : number = sum(_.filter(comparisions, c => c > 0));

		if (usedPoints > effectiveValue) {
			return new Failure(usedPoints - effectiveValue);
		} else {
			var leftoverPoints = effectiveValue - usedPoints;
			var cappedQuality = Math.min(leftoverPoints, value);
			var quality = applyMinimumQuality(minimumQuality, cappedQuality);
			if (usedPoints === 0) {
				var worstDie = _.max(comparisions); // is <= 0 in this case
				return new Success(quality, leftoverPoints - worstDie)
			} else {
				return new Success(quality, leftoverPoints)
			}
		}
	}

	function sum(array : Array<number>) : number {
		return _.reduce(array, function (acc : number, num) {
			return acc + num;
		}, 0);
	}

	function specialOutcome(options, value, dice) {
		if (all3EqualTo(dice, 1)) {
			return new SpectacularSuccess(applyMinimumQuality(options.minimumQuality, value));
		} else if (twoEqualTo(dice, 1)) {
			return new AutomaticSuccess(applyMinimumQuality(options.minimumQuality, value));
		} else if (all3EqualTo(dice, 20)) {
			return new SpectacularFailure();
		} else if (options.wildeMagie && twoGreaterThan(dice, 18)) {
			return new AutomaticFailure();
		} else if (options.festeMatrix && sumGreaterThan(dice, 57) && twoEqualTo(dice, 20)) {
			return new AutomaticFailure();
		} else if (!options.festeMatrix && twoEqualTo(dice, 20)) {
			return new AutomaticFailure();
		} else if (options.spruchhemmung && twoSame(dice)) {
			return new SpruchhemmungFailure();
		}
	}

	function all3EqualTo(dice, n) {
		return dice[0] === n && dice[1] === n && dice[2] === n;
	}

	function twoEqualTo(dice, n) {
		return twoFiltered(dice, d => d === n);
	}

	function twoSame(dice) {
		return dice[0] === dice[1] || dice[1] === dice[2] || dice[0] === dice[2];
	}

	function twoGreaterThan(dice, n) {
		return twoFiltered(dice, d => d > n);
	}

	function sumGreaterThan(dice, n) {
		return (dice[0] + dice[1] + dice[2] > n);
	}

	function twoFiltered(dice, fn) {
		return _.filter(dice, fn).length >= 2;
	}

	function applyMinimumQuality(minimumQuality, rawValue) {
		return Math.max(rawValue, minimumQuality ? 1 : 0);
	}

	export function SpectacularSuccess(quality) {
		this.success = true;
		this.quality = quality;
	}

	export function AutomaticSuccess(quality) {
		this.success = true;
		this.quality = quality;
	}

	export function AutomaticFailure() {
		this.success = false;
	}

	export function SpectacularFailure() {
		this.success = false;
	}

	export function SpruchhemmungFailure() {
		this.success = false;
	}

	export function Success(quality, rest) {
		this.success = true;
		this.quality = quality;
		this.rest = rest;
	}

	export function Failure(gap) {
		this.success = false;
		this.gap = gap;
	}

}
