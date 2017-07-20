/**
 * Created by olivi on 07/16/2017.
 */
var app = angular.module("app", ['ui.materialize'])
    .controller('ChipsController', ["$scope", function ($scope) {
        $scope.chips = [{
            tag: 'Apple',
        }, {
            tag: 'Microsoft',
        }, {
            tag: 'Google',
        }];
    }]);