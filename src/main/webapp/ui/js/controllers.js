'use strict';

var module = angular.module('pp2.controllers', []);

module.controller('RangedController', [ '$scope', 'RangedService', function($scope, service) {
	$scope.sizes = service.options.size;
	$scope.ranges = service.options.range;

	$scope.options = {
		size : $scope.sizes[3],
		range : $scope.ranges[1],
		other : 0
	};
	
	$scope.$watch('options', function() {
		$scope.difficulty = service.calculate($scope.options);
	}, true);
} ]);

function clone(obj) {
	return JSON.parse(JSON.stringify(obj));
}

function CheckController($scope, $http) {
	var check = $scope.check = {
		attribute1 : 12,
		attribute2 : 12,
		attribute3 : 12,
		value : 4,
		difficulty : 0,
		minimumQuality : true
	};

	var checkRoll = {
		check : check,
		die1 : 11,
		die2 : 12,
		die3 : 13
	};

	$scope.recalculate = function() {
		$http.put('../rest/check/statistics', check).success(function(statistics) {
			$scope.statistics = statistics;
			statistics.check = clone(check);
			statistics.pieData = [ {
				value : statistics.chance,
				color : "#00bb00"
			}, {
				value : (1 - statistics.chance),
				color : "#cc0000"
			} ];
			var ctx = document.getElementById("chanceChart").getContext("2d");
			new Chart(ctx).Pie(statistics.pieData);
		}).error(function(data, status) {
			alert("status=" + status);
		});

		$http.put('../rest/check/outcome', checkRoll).success(function(outcome) {
			outcome.checkRoll = clone(checkRoll);
			$scope.outcome = outcome;
		}).error(function(data, status) {
			alert("status=" + status);
		});
	};
}

function RollController($scope, $http) {
	$scope.roll = function(pips) {
		$http.get('../rest/random/d' + pips).success(function(data) {
			$scope.result = data;
		}).error(function(data, status) {
			alert("status=" + status);
		});
	}
}