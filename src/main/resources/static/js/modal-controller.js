app.controller('ModalController', ['$scope', '$http', function ($scope, $http) {
    $scope.openModal = false;

    var saveProjectUrl = '/api/projects';
    var saveProjectsSuccess = 'Project saved successfully';
    var saveProjectsError = 'Project could not be saved';
    var toastLife = 1500;

    $scope.project = {
        title: '',
        category: '',
        public: true,
        technologies: ''
    };

    $scope.technologies = '';

    $scope.saveProject = function () {
        if ($scope.project.technologies == '') {
            $scope.project.technologies = null;
        } else {
            $scope.project.technologies = $scope.project.technologies.split(",");
        }

        $http.post(saveProjectUrl, $scope.project).success(function (data) {
            Materialize.toast(saveProjectsSuccess, toastLife);
            $scope.openModal = false;
        }).error(function () {
            Materialize.toast(saveProjectsError, toastLife);
        });
    };

//    $scope.hasRequiredFields = false;
}]);