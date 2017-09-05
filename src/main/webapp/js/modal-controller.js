/**
 * Controller for modal to add a new project.
 */
app.controller('ModalController', ['$scope', '$http', '$rootScope', 'rest', 'toastLife',
                                   function ($scope, $http, $rootScope, rest, toastLife) {
    $scope.openModal = false;

    var saveProjectUrl = '/api/projects';
    var saveProjectsSuccess = 'Project saved successfully';
    var saveProjectsError = 'Project could not be saved';

    var _initProject = function () {
        $rootScope.modalProject = {
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
     * Allow access to functionality to open modal from outside scope of this controller.
     */
    $rootScope.openProjectModal = function () {
        $scope.openModal = true;
    };

    /**
     * Show another set of inputs to add a project link in the modal.
     */
    $scope.addLink = function () {
        var emptyLink = {
            'title': '',
            'href': ''
        };
        $rootScope.modalProject.links.push(emptyLink);
    };

    /**
     * Remove a set of inputs to add a project link in the modal.
     * @param index index of input to delete from modal
     */
    $scope.deleteLink = function (index) {
        $rootScope.modalProject.links.splice(index, 1);
    };

    /**
     * Parse technologies string into array of strings.
     */
    var _formatTechnologies = function () {
        if ($rootScope.modalProject.technologies === '' || $rootScope.modalProject.technologies === null) {
            $rootScope.modalProject.technologies = null;
        } else {
            $rootScope.modalProject.technologies = $rootScope.modalProject.technologies.split(',');
        }
    };

    /**
     * Save the project with the input values from the modal to Mongo, and refresh view with new
     * or updated project.
     */
    $scope.saveProject = function () {
        $rootScope.modalProject.links = $rootScope.modalProject.links.filter(function (link) {
            return link.title !== '';
        });

        _formatTechnologies();

        var successHandler = function () {
            $rootScope.getAllProjects();
            //clear model for project modal
            _initProject();
            $scope.openModal = false;
            Materialize.toast(saveProjectsSuccess, toastLife);
        };

        rest.postData(saveProjectUrl, {}, $rootScope.modalProject, successHandler, saveProjectsError);
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
        $scope.minDate = '2015-01-01 00:00';
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