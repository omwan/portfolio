/**
 * Controller for main body of web app.
 */
app.controller('controller', ['$scope', '$http', function ($scope, $http) {
    var getProjectsUrl = '/api/projects';
    var filterProjectsByPublicUrl = '/api/projects?publicOnly=true';
    var getProjectsByCategoryUrl = '/api/projects/{0}';
    var deleteProjectUrl = '/api/projects?id={0}';

    var toastLife = 1500;
    var getProjectsError = 'Projects could not be loaded';
    var filterByPublicError = 'Projects could not be filtered';
    var deleteProjectSucess = 'Project successfully deleted';
    var deleteProjectError = 'Project could not be deleted';

    //initialize collapsing sidebar from materialize
    angular.element(document).ready(function () {
        $(".button-collapse").sideNav({closeOnClick: true});
    });

    $scope.title = 'Olivia Wan, Project Portfolio';

    /**
     * Set projects scope to response from API to get all projects.
     */
    $scope.getAllProjects = function () {
        $http.get(getProjectsUrl).success(function (data) {
            $scope.keys = Object.keys(data);
            $scope.projects = data;
        }).error(function () {
            Materialize.toast(getProjectsError, toastLife);
        });
    };

    /**
     * Set projects scope to response from API to filter by only public projects.
     */
    $scope.filterByPublic = function () {
        $http.get(filterProjectsByPublicUrl).success(function (data) {
            $scope.keys = Object.keys(data);
            $scope.projects = data;
        }).error(function () {
            Materialize.toast(filterByPublicError, toastLife);
        });
    };

    /**
     * Set projects scope to response from API to get all projects for a category.
     * @param category category to get projects for
     */
    $scope.filterByCategory = function (category) {
        var apiUrl = getProjectsByCategoryUrl.replace('{0}', category);
        $http.get(apiUrl).success(function (data) {
            $scope.keys = [category.toUpperCase()];
            $scope.projects = {};
            $scope.projects[category.toUpperCase()] = data;
        }).error(function () {
            Materialize.toast(getProjectsError, toastLife);
        });
    };

    /**
     * Delete the project at the given index for the given category.
     * @param category category of project to delete (for mapping)
     * @param index    index of project within category
     */
    $scope.deleteProject = function (category, index) {
        var id = $scope.projects[category][index].id;
        var apiUrl = deleteProjectUrl.replace('{0}', id);
        $http.delete(apiUrl).success(function () {
            $scope.projects[category].splice(index, 1);
            Materialize.toast(deleteProjectSucess, toastLife);
        }).error(function (error, status) {
            Materialize.toast(deleteProjectError, toastLife);
        });
    };

    /**
     * Initialize projects scope with all projects.
     */
    var _init = function () {
        $scope.getAllProjects();
    };

    _init();
}]);