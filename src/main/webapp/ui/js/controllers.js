'use strict';

/* Controllers */

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
		$http.put('../rest/check/statistics', check).success(
				function(statistics) {
					statistics.check = clone(check);
					$scope.statistics = statistics;
					var data = [ {
						value : statistics.chance,
						color : "#00ff00"
					}, {
						value : (1 - statistics.chance),
						color : "#ff0000"
					} ];
					var ctx = document.getElementById("chanceChart")
							.getContext("2d");
					var myNewChart = new Chart(ctx).Pie(data);
				}).error(function(data, status) {
			alert("status=" + status);
		});

		$http.put('../rest/check/outcome', checkRoll).success(
				function(outcome) {
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