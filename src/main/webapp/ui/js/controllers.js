'use strict';

/* Controllers */

function CheckController($scope, $http) {
    var check = $scope.check = {
	attribute1 : 12,
	attribute2 : 12,
	attribute3 : 12,
	value : 4,
	difficulty : 0,
	minimumQuality = true
    };
    
    var checks = $scope.checks = [];

    $scope.calculate = function() {
	$http.put('../rest/check/statistics', check).success(function(result) {
	    checks.push(result);
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