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
		animationEnabled: false,
		creditLink: null,
		creditText: null,
		data: [
			{
				type: "pie",
				startAngle: -90,
				dataPoints: [
					{y: 0.350, color: "green", abc: "abcd", toolTipContent: "huhuhu {abc}" },
					{y: 0.350, color: "yellow", abc: "abcd", toolTipContent: "huhuhu {abc}" },
					{y: 0.350, color: "red", abc: "abcd", toolTipContent: "huhuhu {abc}" }
				]
			}
		]
	});

	$scope.$watch("check", function (newValue) {
		var partitioned = Checks.calculatePartitioned(newValue);
		$scope.canvasjsPieChart.options.data[0].dataPoints = toCanvasjsPieDataPoints(partitioned);
		$scope.canvasjsPieChart.render();
	}, true);

	function toCanvasjsPieDataPoints(partitioned) {
		var partitions : Array<{count; quality;}> = partitioned[0].partitions;
		var dataPoints : Array<{y;}> = _.map(partitions, function (p) {
			return {
				x: p.quality,
				y: p.count,
				color: "#008000",
				toolTipContent: "gelungen mit {x}"
			};
		});
		dataPoints.push({y: partitioned[1].count, color: "#bb0000", toolTipContent: "misslungen"});
		console.log(dataPoints);
		return dataPoints;
	}
}]);
