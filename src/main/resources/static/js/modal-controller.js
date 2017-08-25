app.controller('ModalController', ['$scope', '$http', function ($scope, $http) {
    $scope.openModal = false;

    var saveProjectUrl = '/api/projects';
    var saveProjectsSuccess = 'Project saved successfully';
    var saveProjectsError = 'Project could not be saved';
    var toastLife = 1500;

    $scope.addLink = function () {
        $scope.project.links.push({
            'title': '',
            'href': ''
        });
    }

    $scope.deleteLink = function (index) {
        $scope.project.links.splice(index, 1);
    }

    $scope.project = {
        title: '',
        category: '',
        public: true,
        technologies: '',
        links: [{
            'title': '',
            'href': ''
        }]
    };

    $scope.saveProject = function () {
        $scope.project.links = $scope.project.links.filter(function (link) {
            return link.title != '';
        });

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
}]);