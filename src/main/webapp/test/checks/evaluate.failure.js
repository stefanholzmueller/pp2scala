module("check.evaluate.failure");


var defaultOptions = {
    minimumQuality: true,
    festeMatrix: false,
    wildeMagie: false,
    spruchhemmung: false
};

var defaultAttributes = [11, 12, 13];


test("no wilde magie", function () {
    var outcome = check.evaluate(defaultOptions, defaultAttributes, 5, 0, [19, 5, 20]);
    equal(outcome.success, false);
});