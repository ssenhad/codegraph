module.exports = function (config) {
    config.set({
        basePath: '../',
        files: [
            './src/main/webapp/components/jquery/dist/jquery.min.js',
            './src/main/webapp/components/bootstrap/dist/js/bootstrap.min.js',
            './src/main/webapp/components/angular/angular.min.js',
            './src/main/webapp/components/angular-mocks/angular-mocks.js',
            './src/main/webapp/components/angular-route/angular-route.min.js',
            './src/main/webapp/components/angular-bootstrap/ui-bootstrap.min.js',
            './src/main/webapp/components/angular-bootstrap/ui-bootstrap-tpls.min.js',

            './src/main/webapp/app/modules/**/*.module.js',
            './src/main/webapp/app/**/*.js',

            './test/**/*.js'
        ],
        reporters: ['progress'],
        autoWatch: true,
        singleRun: false,
        frameworks: ['jasmine'],
        browsers: ['PhantomJS'],
        plugins: [
            'karma-jasmine',
            'karma-phantomjs-launcher'
        ]
    });
};