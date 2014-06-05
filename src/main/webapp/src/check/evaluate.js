/// <reference path="../../typings/tsd.d.ts" />
var check;
(function (check) {
    function evaluate(options, attributes, value, difficulty, dice) {
        var special = specialOutcome(options, value, dice);
        if (special) {
            return special;
        } else {
            return successOrFailure(options.minimumQuality, attributes, value, difficulty, dice);
        }
    }
    check.evaluate = evaluate;

    function successOrFailure(minimumQuality, attributes, value, difficulty, dice) {
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
        return _.every(dice, function (d) {
            return d === n;
        });
    }

    function twoEqualTo(dice, n) {
        return twoFiltered(dice, function (d) {
            return d === n;
        });
    }

    function twoSame(dice) {
        return dice[0] === dice[1] || dice[1] === dice[2] || dice[0] === dice[2];
    }

    function twoGreaterThan(dice, n) {
        return twoFiltered(dice, function (d) {
            return d > n;
        });
    }

    function twoFiltered(dice, fn) {
        return _.filter(dice, fn).length >= 2;
    }

    function applyMinimumQuality(minimumQuality, rawValue) {
        return Math.max(rawValue, minimumQuality ? 1 : 0);
    }

    function SpectacularSuccess(quality) {
        this.success = true;
        this.quality = quality;
    }
    check.SpectacularSuccess = SpectacularSuccess;

    function AutomaticSuccess(quality) {
        this.success = true;
        this.quality = quality;
    }
    check.AutomaticSuccess = AutomaticSuccess;

    function AutomaticFailure() {
        this.success = false;
    }
    check.AutomaticFailure = AutomaticFailure;

    function SpectacularFailure() {
        this.success = false;
    }
    check.SpectacularFailure = SpectacularFailure;

    function Spruchhemmung() {
        this.success = false;
    }
    check.Spruchhemmung = Spruchhemmung;
})(check || (check = {}));
//# sourceMappingURL=evaluate.js.map
