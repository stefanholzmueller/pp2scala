/// <reference path="../../typings/tsd.d.ts" />
"use strict";

var module = angular.module('pp2.check', []);

module.controller('CheckController', [ '$scope', function ($scope) {
	var check = $scope.check = {
		attribute1: 12,
		attribute2: 12,
		attribute3: 12,
		attributes: [12,12,12],
		value: 4,
		difficulty: 0,
		minimumQuality: true
	};

	$scope.calc = function () {
		check.value = 11;
	};
}]);
