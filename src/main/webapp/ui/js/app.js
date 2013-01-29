'use strict';


// Declare app level module which depends on filters, and services
angular.module('ppCheck', ['ppCheck.filters']).
  config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/view1', {templateUrl: 'partials/partial1.html', controller: RollController});
    $routeProvider.when('/view2', {templateUrl: 'partials/partial2.html', controller: RollController});
    $routeProvider.otherwise({redirectTo: '/view1'});
  }]);
