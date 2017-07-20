app.controller('controller', ['$scope', '$http', function ($scope, $http) {
    $scope.chips = [];

    var getProjectsUrl = '/api/projects ';
    var filterProjectsByPublicUrl = '/api/projects?isPublic=true';
    //    var getProjectsUrl = 'js/mocks / all - projects.json ';
    //    var filterProjectsByPublicUrl = 'js/mocks/public-projects.json';

    var toastLife = 1500;
    var getProjectsError = 'Projects could not be loaded';
    var filterByPublicError = 'Projects could not be filtered';

    $scope.title = 'Olivia Wan, Project Portfolio';
    $http.get(getProjectsUrl).success(function (data) {
        $scope.projects = _setProjects(data);
    }).error(function () {
        Materialize.toast(getProjectsError, toastLife);
    });

    $scope.filterByPublic = function () {
        $http.get(filterProjectsByPublicUrl).success(function (data) {
            $scope.projects = _setProjects(data);
        }).error(function () {
            Materialize.toast(filterByPublicError, toastLife);
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