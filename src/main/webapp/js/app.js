/**
 * Initialize angular application.
 */
var app = angular.module('app', ['ui.materialize']);

/**
 * Constant for time a toast should be displayed on screen.
 */
app.value('toastLife', 1500);