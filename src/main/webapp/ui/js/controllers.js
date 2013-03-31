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

	$scope.recalculate = function() {
		$http.put('../rest/check/statistics', check).success(function(result) {
			result.check = clone(check);
			$scope.result = result;
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