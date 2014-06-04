var defaultOptions = {
    minimumQuality: true,
    festeMatrix: false,
    wildeMagie: false,
    spruchhemmung: false
};

var defaultAttributes = [11, 12 , 13];

test("spectacular success", function () {
    var outcome = check.evaluate(defaultOptions, defaultAttributes, 3, -2, [1, 1, 1]);
    equal(outcome.success, true);
    equal(outcome.quality, 3);
});

test("automatic success", function () {
    var outcome = check.evaluate(defaultOptions, defaultAttributes, 5, 5, [1, 9, 1]);
    equal(outcome.success, true);
    equal(outcome.quality, 5);
});
