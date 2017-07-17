/**
 * Created by olivi on 07/16/2017.
 */
app.controller('controller', ['$scope', '$http', function ($scope, $http) {
    $scope.title = 'Top Sellers in Books';
    $http.get('http://localhost:8080/projects').success(function (data) {
        var projects = [];
        angular.forEach(data, function(value, key) {
            var project = {
                category: key,
                projects: value
            };
            projects.push(project);
        });
        console.log(projects);
        $scope.projects = projects;
    });
}]);