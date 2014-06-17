var lodash = require("../bower_components/lodash/dist/lodash.js");
var _ = lodash._;


function evaluate(options, attributes, value, difficulty, dice) {
    var special = specialOutcome(options, value, dice);
    if (special) {
        return special;
    } else {
        return successOrFailure(options.minimumQuality, attributes, value, difficulty, dice);
    }
}

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
    var usedPoints = sum(_.filter(comparisions, function (c) {
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
            return new Success(quality, leftoverPoints - worstDie);
        } else {
            return new Success(quality, leftoverPoints);
        }
    }
}

function sum(array) {
    return _.reduce(array, function (acc, num) {
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

function AutomaticSuccess(quality) {
    this.success = true;
    this.quality = quality;
}

function AutomaticFailure() {
    this.success = false;
}

function SpectacularFailure() {
    this.success = false;
}

function SpruchhemmungFailure() {
    this.success = false;
}

function Success(quality, rest) {
    this.success = true;
    this.quality = quality;
    this.rest = rest;
}

function Failure(gap) {
    this.success = false;
    this.gap = gap;
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
    return combinations;
}

var calculate = function (options, attributes, value, difficulty) {
    var evaluator = _.partial(evaluate, options, attributes, value, difficulty);
    var outcomes = _.map(COMBINATIONS, function (dice) {
        return evaluator(dice);
    });

    var successes = _.filter(outcomes, function (o) {
        return o.success;
    });
    var totalQuality = _.reduce(successes, function (sum, success) {
        return sum + success.quality;
    }, 0);

    var probability = successes.length / outcomes.length;
    var average = totalQuality / successes.length;

    return { probability: probability, average: average };
}

module.exports = function(done) {
    calculate({}, [12, 12, 12], 4, 0);
};
