/// <reference path="../typings/tsd.d.ts" />
var check;
(function (check) {
    function evaluate(options, attributes, value, difficulty, dice) {
        var special = evaluateSpecial(options, value, dice);
        if (special) {
            return special;
        } else {
            return { success: true };
        }
    }
    check.evaluate = evaluate;

    function evaluateSpecial(options, value, dice) {
        if (allEqualTo(dice, 1)) {
            return fullSuccess(options, value);
        } else if (twoEqualTo(dice, 1)) {
            return fullSuccess(options, value);
        } else if (allEqualTo(dice, 20)) {
            return fullFailure();
        } else if (twoEqualTo(dice, 20)) {
            return fullFailure();
        } else if (twoSame(dice)) {
            return fullFailure();
        } else if (options.wildeMagie && twoGreaterThan(dice, 18)) {
            return fullFailure();
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

    function fullSuccess(options, value) {
        return {
            success: true,
            quality: applyMinimumQuality(options.minimumQuality, value)
        };
    }

    function fullFailure() {
        return {
            success: false
        };
    }

    function applyMinimumQuality(minimumQuality, rawValue) {
        return Math.max(rawValue, minimumQuality ? 1 : 0);
    }
})(check || (check = {}));
//# sourceMappingURL=evaluate.js.map
