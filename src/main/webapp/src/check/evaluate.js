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
        var ease = value - difficulty;
        var effectiveValue = Math.max(ease, 0);
        var effectiveAttributes = ease < 0 ? _.map(attributes, function (a) {
            return a + ease;
        }) : attributes;
        return successOrFailureInternal(minimumQuality, effectiveAttributes, effectiveValue, value, dice);
    }

    function successOrFailureInternal(minimumQuality, effectiveAttributes, effectiveValue, value, dice) {
        var comparisions = [dice[0] - effectiveAttributes[0], dice[1] - effectiveAttributes[1], dice[2] - effectiveAttributes[2]];
        var usedPoints = sum3(_.filter(comparisions, function (c) {
            return c > 0;
        }));

        if (usedPoints > effectiveValue) {
            return new Failure(usedPoints - effectiveValue);
        } else {
            var leftoverPoints = effectiveValue - usedPoints;
            var cappedQuality = Math.min(leftoverPoints, value);
            var quality = applyMinimumQuality(minimumQuality, cappedQuality);
            if (usedPoints === 0) {
                var worstDie = _.max(comparisions);
                Success(quality, leftoverPoints - worstDie);
            } else {
                Success(quality, leftoverPoints);
            }
        }
    }

    function sum3(array) {
        return array[0] + array[1] + array[2];
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
        } else if (options.festeMatrix && sumGreaterThan(dice, 57) && twoEqualTo(dice, 20)) {
            return new AutomaticFailure();
        } else if (!options.festeMatrix && twoEqualTo(dice, 20)) {
            return new AutomaticFailure();
        } else if (options.spruchhemmung && twoSame(dice)) {
            return new SpruchhemmungFailure();
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

    function sumGreaterThan(dice, n) {
        return (dice[0] + dice[1] + dice[2] > n);
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

    function SpruchhemmungFailure() {
        this.success = false;
    }
    check.SpruchhemmungFailure = SpruchhemmungFailure;

    function Success(quality, rest) {
        this.success = true;
        this.quality = quality;
        this.rest = rest;
    }
    check.Success = Success;

    function Failure(gap) {
        this.success = false;
        this.gap = gap;
    }
    check.Failure = Failure;
})(check || (check = {}));
//# sourceMappingURL=evaluate.js.map
