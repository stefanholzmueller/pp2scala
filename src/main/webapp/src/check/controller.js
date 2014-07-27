/// <reference path="../../typings/tsd.d.ts" />
"use strict";
var module = angular.module('pp2.check', []);

module.controller('CheckController', [
    '$scope', function ($scope) {
        var check = $scope.check = {
            attributes: [12, 12, 12],
            value: 4,
            difficulty: 0,
            minimumQuality: true
        };

        $scope.calc = function () {
            check.value = 11;
        };
    }]);
//# sourceMappingURL=controller.js.map
