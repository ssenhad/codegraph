'use strict';

module.exports = function (config) {
    config.set({
        basePath: '../',
        files: [
            // './components/jquery/dist/jquery.min.js',
            // './components/bootstrap/dist/js/bootstrap.min.js',
            // './components/angular/angular.min.js',
            // './components/angular-mocks/angular-mocks.js',
            // './components/angular-route/angular-route.min.js',
            // './components/angular-bootstrap/ui-bootstrap.min.js',
            // './components/angular-bootstrap/ui-bootstrap-tpls.min.js',
            //
            './dist/index.js',
            './app/extensions.js',
            // '../app/*.js',
            // './app/modules/**/*.module.js',
            // './app/**/*.js',
            //
            './test/**/*.js'

        ],
        reporters: ['progress'],
        autoWatch: true,
        singleRun: false,
        frameworks: ['jasmine'],
        browsers: ['ChromeHeadless'],
        plugins: [
            'karma-jasmine',
            'karma-chrome-launcher'
        ]
    });
};
