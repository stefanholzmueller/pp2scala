module("check.calculate");


// TODO: spruchhemmung true, 20,20,20,0,0 => ~14% failure

var defaultCheck = {
    attributes: [12, 12, 12],
    value: 4,
    difficulty: 0,
    options: {
        minimumQuality: true
    }
};

test("no wilde magie", function () {
    var result = Check.calculatePartitioned(defaultCheck);
    equal(result, '[{"label":"gelungen","count":3688,"partitions":[{"quality":"4","count":1752},{"quality":"3","count":429},{"quality":"2","count":465},{"quality":"1","count":1042}]},{"label":"misslungen","count":4312}]');
});