/// <reference path="../../typings/tsd.d.ts" />
/// <reference path="calculate.ts" />
"use strict";

var module = angular.module('pp2.check', []);

module.controller('CheckController', [ '$scope', function ($scope) {
	var check : Checks.Check = $scope.check = {
		attributes: [12, 12, 12],
		value: 4,
		difficulty: 0,
		options: {
			minimumQuality: true
		}
	};

	$scope.canvasjsPieChart = new CanvasJS.Chart("canvasjsPieChart", {
		data: [
			{
				type: "pie",
				dataPoints: [
					{y: 0.350, color: "green", abc: "abcd", toolTipContent: "huhuhu {abc}" },
					{y: 0.350, color: "yellow", abc: "abcd", toolTipContent: "huhuhu {abc}" },
					{y: 0.350, color: "red", abc: "abcd", toolTipContent: "huhuhu {abc}" }
				]
			}
		]
	});


	$scope.canvasjsPieChart.render();

	$scope.calc = function () {
		return Checks.calculatePartitioned(check);
	};
}]);
