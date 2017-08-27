/**
 * Controller for modal to add a new project.
 */
app.controller('ModalController', ['$scope', '$http', '$rootScope', 'rest', function ($scope, $http, $rootScope, rest) {
    $scope.openModal = false;

    var saveProjectUrl = '/api/projects';
    var saveProjectsSuccess = 'Project saved successfully';
    var saveProjectsError = 'Project could not be saved';
    var toastLife = 1500;

    var _initProject = function () {
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
    };

    /**
     * Show another set of inputs to add a project link in the modal.
     */
    $scope.addLink = function () {
        var emptyLink = {
            'title': '',
            'href': ''
        };
        $scope.project.links.push(emptyLink);
    };

    /**
     * Remove a set of inputs to add a project link in the modal.
     * @param index index of input to delete from modal
     */
    $scope.deleteLink = function (index) {
        $scope.project.links.splice(index, 1);
    };

    /**
     * Parse technologies string into array of strings.
     */
    var _formatTechnologies = function () {
        if ($scope.project.technologies == '' || $scope.project.technologies == null) {
            $scope.project.technologies = null;
        } else {
            $scope.project.technologies = $scope.project.technologies.split(",");
        }
    };

    /**
     * Add new project to scope to update UI without refreshing page.
     * @param data new project data
     */
    var _addProjectToScope = function (data) {
        if ($rootScope.projects[$scope.project.category]) {
            $rootScope.projects[$scope.project.category].push(data);
        } else {
            $rootScope.projects[$scope.project.category] = [data];
        }
    };

    /**
     * Save the project with the input values from the modal to Mongo.
     */
    $scope.saveProject = function () {
        $scope.project.links = $scope.project.links.filter(function (link) {
            return link.title != '';
        });

        _formatTechnologies();

        var successHandler = function (data) {
            $scope.openModal = false;
            _addProjectToScope(data);
            _initProject();
            Materialize.toast(saveProjectsSuccess, toastLife);
        };

        rest.call('POST', saveProjectUrl, {}, $scope.project, successHandler, saveProjectsError);
    };

    /**
     * Initialize datepicker fields/values.
     */
    var _initDatePicker = function () {
        $scope.currentTime = new Date();
        $scope.month =
            ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September',
             'October', 'November', 'December'];
        $scope.monthShort =
            ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
        $scope.weekdaysFull =
            ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
        $scope.weekdaysLetter = ['S', 'M', 'T', 'W', 'T', 'F', 'S'];
        $scope.disable = [];
        $scope.firstDay = 0;
        $scope.today = 'Today';
        $scope.clear = 'Clear';
        $scope.close = 'Select';
        $scope.minDate = "2015-01-01 00:00";
        $scope.maxDate = new Date().toISOString();
    };

    /**
     * Initialize scope values.
     */
    var _init = function () {
        _initProject();
        _initDatePicker();
    };

    _init();
}]);