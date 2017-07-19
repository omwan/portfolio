/**
 * Created by olivi on 07/16/2017.
 */
app.controller('controller', ['$scope', '$http', function ($scope, $http) {
    var getProjectsUrl = '/api/projects';
    var filterProjectsByPublicUrl = '/api/projects?isPublic=true';
//    var getProjectsUrl = 'js/mocks/all-projects.json';
//    var filterProjectsByPublicUrl = 'js/mocks/public-projects.json';
    
    $scope.title = 'Olivia Wan, Project Portfolio';
    $http.get(getProjectsUrl).success(function (data) {
        $scope.projects = _setProjects(data);
    });

    $scope.filterByPublic = function () {
        $http.get(filterProjectsByPublicUrl).success(function (data) {
            $scope.projects = _setProjects(data);
        });
    };

    var _setProjects = function (data) {
        var projects = [];
        angular.forEach(data, function (value, key) {
            var project = {
                category: key,
                projects: value
            };
            projects.push(project);
        });
        return projects;
    };
}]);