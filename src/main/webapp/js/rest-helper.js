/**
 * Service to modularize making ajax rest calls.
 */
app.service('rest', function ($http, $rootScope) {

    /**
     * Make a rest call for the following parameters; handles showing loading spinner
     * while request is being made, and showing toast upon rest failure.
     *
     * @param method         request method (eg: GET, POST, PUT, DELETE)
     * @param url            url to call
     * @param params         parameters to append to request url
     * @param data           request body
     * @param successHandler function to be executed upon success
     * @param errorMessage   error message to be shown in toast upon failure
     */
    this.call = function (method, url, params, data, successHandler, errorMessage) {
        $rootScope.isLoading = true;
        $http({
                  "method": method,
                  "url": url,
                  "params": params,
                  "data": data
              }).success(successHandler)
            .error(function () {
                Materialize.toast(errorMessage, 1500);
            })
            .finally(function () {
                $rootScope.isLoading = false;
            });
    };
});