module("check.evaluate.success");


var defaultOptions = {
    minimumQuality: true,
    festeMatrix: false,
    wildeMagie: false,
    spruchhemmung: false
};

var defaultAttributes = [11, 12, 13];


test("no spruchhemmung", function () {
    var outcome = check.evaluate(defaultOptions, defaultAttributes, 5, 0, [10, 10, 11]);
    equal(outcome.success, true);
});
