/**
 * Controller for modal to add a new project.
 */
app.controller('ModalController', ['$scope', '$http', function ($scope, $http) {
    $scope.openModal = false;

    var saveProjectUrl = '/api/projects';
    var saveProjectsSuccess = 'Project saved successfully';
    var saveProjectsError = 'Project could not be saved';
    var toastLife = 1500;

    /**
     * Show another set of inputs to add a project link in the modal.
     */
    $scope.addLink = function () {
        $scope.project.links.push({
            'title': '',
            'href': ''
        });
    };

    /**
     * Remove a set of inputs to add a project link in the modal.
     * @param index index of input to delete from modal
     */
    $scope.deleteLink = function (index) {
        $scope.project.links.splice(index, 1);
    };

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

    /**
     * Save the project with the input values from the modal to Mongo.
     */
    $scope.saveProject = function () {
        $scope.project.links = $scope.project.links.filter(function (link) {
            return link.title != '';
        });

        //parse technologies string into array of strings
        if ($scope.project.technologies == '') {
            $scope.project.technologies = null;
        } else {
            $scope.project.technologies = $scope.project.technologies.split(",");
        }

        $http.post(saveProjectUrl, $scope.project).success(function (data) {
            //update project list
            $scope.$parent.projects[$scope.project.category].push(data);

            //show success toast, close modal
            Materialize.toast(saveProjectsSuccess, toastLife);
            $scope.openModal = false;

            //reset scope variable
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
        }).error(function () {
            Materialize.toast(saveProjectsError, toastLife);
        });
    };

    //datepicker constants
    $scope.currentTime = new Date();
    $scope.month = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    $scope.monthShort = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
    $scope.weekdaysFull = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    $scope.weekdaysLetter = ['S', 'M', 'T', 'W', 'T', 'F', 'S'];
    $scope.disable = [false, 1, 7];
    $scope.today = 'Today';
    $scope.clear = 'Clear';
    $scope.close = 'Select';
    $scope.minDate = "2015-01-01 00:00";
    $scope.maxDate = new Date().toISOString();

}]);