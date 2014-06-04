var defaultOptions = {
    minimumQuality: true,
    festeMatrix: false,
    wildeMagie: false,
    spruchhemmung: false
};

var defaultAttributes = [11, 12, 13];

test("todo", function () {
    var outcome = check.evaluate(defaultOptions, defaultAttributes, 3, -2, [1, 1, 1]);
    equal(outcome.success, true);
    equal(outcome.quality, 3);
});
