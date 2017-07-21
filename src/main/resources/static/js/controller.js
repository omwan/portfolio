app.controller('controller', ['$scope', '$http', function ($scope, $http) {
    $scope.chips = [];

    var getProjectsUrl = '/api/projects';
    var filterProjectsByPublicUrl = '/api/projects?publicOnly=true';
    var getProjectsByCategoryUrl = '/api/projects/{0}';
//        var getProjectsUrl = 'js/mocks/all-projects.json ';
//        var filterProjectsByPublicUrl = 'js/mocks/public-projects.json';
//        var getProjectsByCategoryUrl = 'js/mocks/class-projects.json';

    var toastLife = 1500;
    var getProjectsError = 'Projects could not be loaded';
    var filterByPublicError = 'Projects could not be filtered';
    
    angular.element(document).ready(function () {
        $(".button-collapse").sideNav({
            closeOnClick: true
        });
    });

    $scope.title = 'Olivia Wan, Project Portfolio';

    $scope.getAllProjects = function () {
        $http.get(getProjectsUrl).success(function (data) {
            $scope.projects = _buildProjects(data);
        }).error(function () {
            Materialize.toast(getProjectsError, toastLife);
        });
    };

    $scope.filterByPublic = function () {
        $http.get(filterProjectsByPublicUrl).success(function (data) {
            $scope.projects = _buildProjects(data);
        }).error(function () {
            Materialize.toast(filterByPublicError, toastLife);
        });
    };

    $scope.filterByCategory = function (category) {
        var apiUrl = getProjectsByCategoryUrl.replace('{0}', category);
        $http.get(apiUrl).success(function (data) {
            $scope.projects = _buildProjects({
                [category]: data
            });
            console.log($scope.projects);
        }).error(function () {
            Materialize.toast(getProjectsError, toastLife);
        });
    }

    var _buildProjects = function (data) {
        var projects = [];
        angular.forEach(data, function (value, key) {
            var project = {
                category: key.toUpperCase(),
                projects: value
            };
            projects.push(project);
        });
        return projects;
    };

    var _init = function () {
        $scope.getAllProjects();
    };
    
    _init();
}]);