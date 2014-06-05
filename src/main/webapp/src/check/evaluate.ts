/// <reference path="../../typings/tsd.d.ts" />

module check {

	export function evaluate(options, attributes : Array<number>, value : number, difficulty : number, dice : Array<number>) : any {
		var special = specialOutcome(options, value, dice);
		if (special) {
			return special;
		} else {
			return successOrFailure(options.minimumQuality, attributes, value, difficulty, dice);
		}
	}

	function successOrFailure(minimumQuality : boolean, attributes : Array<number>, value : number, difficulty : number, dice : Array<number>) {
		return { success: true };
	}

	function specialOutcome(options, value, dice) {
		if (allEqualTo(dice, 1)) {
			return new SpectacularSuccess(applyMinimumQuality(options.minimumQuality, value));
		} else if (twoEqualTo(dice, 1)) {
			return new AutomaticSuccess(applyMinimumQuality(options.minimumQuality, value));
		} else if (allEqualTo(dice, 20)) {
			return new SpectacularFailure();
		} else if (options.wildeMagie && twoGreaterThan(dice, 18)) {
			return new AutomaticFailure();
		} else if (options.festeMatrix && (dice[0] + dice[1] + dice[2] > 57) && twoEqualTo(dice, 20)) {
			return new AutomaticFailure();
		} else if (!options.festeMatrix && twoEqualTo(dice, 20)) {
			return new AutomaticFailure();
		} else if (options.spruchhemmung && twoSame(dice)) {
			return new Spruchhemmung();
		}
	}

	function allEqualTo(dice, n) {
		return _.every(dice, d => d === n);
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

	export function Spruchhemmung() {
		this.success = false;
	}

}
