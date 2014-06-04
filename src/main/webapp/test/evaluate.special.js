//suite("special");


var defaultOptions = {
    minimumQuality: true,
    festeMatrix: false,
    wildeMagie: false,
    spruchhemmung: false
};

var defaultAttributes = [11, 12, 13];


test("test", function() {
    console.log(check);
});

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

test("automatic success, negative value", function () {
    var outcome = check.evaluate(defaultOptions, defaultAttributes, -2, 5, [1, 9, 1]);
    equal(outcome.success, true);
    equal(outcome.quality, 1);
});

test("spectacular failure", function () {
    var outcome = check.evaluate(defaultOptions, defaultAttributes, 5, 5, [20, 20, 20]);
    equal(outcome.success, false);
});

test("automatic failure", function () {
    var outcome = check.evaluate(defaultOptions, defaultAttributes, 5, 5, [15, 20, 20]);
    equal(outcome.success, false);
});

test("spruchhemmung > success", function () {
    var outcome = check.evaluate({spruchhemmung: true}, defaultAttributes, 5, 0, [5, 8, 5]);
    equal(outcome.success, false);
});

test("spruchhemmung > failure", function () {
    var outcome = check.evaluate({spruchhemmung: true}, defaultAttributes, 5, 0, [15, 18, 15]);
    equal(outcome.success, false);
});

test("spruchhemmung < automatic success", function () {
    var outcome = check.evaluate({spruchhemmung: true}, defaultAttributes, 5, 0, [1, 1, 15]);
    equal(outcome.success, true);
});

test("no spruchhemmung", function () {
    var outcome = check.evaluate(defaultOptions, defaultAttributes, 5, 0, [10, 10, 11]);
    equal(outcome.success, true);
});

test("wilde magie", function () {
    var outcome = check.evaluate(defaultOptions, defaultAttributes, 25, 0, [19, 10, 20]);
    equal(outcome.success, false);
});