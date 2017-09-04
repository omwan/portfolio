/**
 * Controller for main body of web app.
 */
app.controller('controller', ['$scope', '$http', 'rest', '$rootScope', function ($scope, $http, rest, $rootScope) {
    var getProjectsUrl = '/api/projects';
    var getProjectsByCategoryUrl = '/api/projects/{0}';
    var deleteProjectUrl = '/api/projects';

    var getProjectsError = 'Projects could not be loaded';
    var filterByPublicError = 'Projects could not be filtered';
    var deleteProjectSucess = 'Project successfully deleted';
    var deleteProjectError = 'Project could not be deleted';

    var toastLife = 1500;

    /**
     * Set projects scope to response from API to get all projects.
     */
    $scope.getAllProjects = function () {
        var successHandler = function (data) {
            $scope.keys = Object.keys(data);
            $rootScope.projects = data;
        };
        rest.call('GET', getProjectsUrl, {}, null, successHandler, getProjectsError);
    };

    /**
     * Set projects scope to response from API to filter by only public projects.
     */
    $scope.filterByPublic = function () {
        var successHandler = function (data) {
            $scope.keys = Object.keys(data);
            $rootScope.projects = data;
        };
        rest.call('GET', getProjectsUrl, {'publicOnly':true}, null, successHandler, filterByPublicError);
    };

    /**
     * Set projects scope to response from API to get all projects for a category.
     * @param category category to get projects for
     */
    $scope.filterByCategory = function (category) {
        var successHandler = function (data) {
            $scope.keys = [category.toUpperCase()];
            $rootScope.projects = {};
            $rootScope.projects[category.toUpperCase()] = data;
        };
        var apiUrl = getProjectsByCategoryUrl.replace('{0}', category);
        rest.call('GET', apiUrl, {}, null, successHandler, getProjectsError);
    };

    /**
     * Delete the project at the given index for the given category.
     * @param category category of project to delete (for mapping)
     * @param index    index of project within category
     */
    $scope.deleteProject = function (category, index) {
        var id = $rootScope.projects[category][index].id;
        var apiUrl = deleteProjectUrl.replace('{0}', id);
        var successHandler = function () {
            $rootScope.projects[category].splice(index, 1);
            Materialize.toast(deleteProjectSucess, toastLife);
        };
        rest.call('DELETE', apiUrl, {"id": id}, null, successHandler, deleteProjectError);
    };

    /**
     * Initialize collapsing sideabar from Materialize.
     */
    var _initSidenavCollapse = function () {
        angular.element(document).ready(function () {
            $(".button-collapse").sideNav({closeOnClick: true});
        });
    };

    /**
     * Initialize projects scope with all projects.
     */
    var _init = function () {
        _initSidenavCollapse();
        $scope.getAllProjects();
    };

    _init();
}]);