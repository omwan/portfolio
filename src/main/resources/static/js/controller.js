/**
 * Created by olivi on 07/16/2017.
 */
app.controller('controller', ['$scope', '$http', function ($scope, $http) {
    $scope.title = 'Olivia Wan, Project Portfolio';
    $http.get('http://localhost:8080/projects').success(function (data) {
        $scope.projects = _setProjects(data);
        console.log($scope.projects);
    });

    $scope.filterByPublic = function() {
        $http.get('http://localhost:8080/projects?isPublic=true').success(function (data) {
            $scope.projects = _setProjects(data);
            console.log($scope.projects);
        });
    };

    var _setProjects = function(data) {
        var projects = [];
        angular.forEach(data, function(value, key) {
            var project = {
                category: key,
                projects: value
            };
            projects.push(project);
        });
        return projects;
    };
}]);