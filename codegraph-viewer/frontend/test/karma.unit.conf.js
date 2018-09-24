'use strict';

module.exports = function (config) {
    config.set({
        basePath: '../',
        files: [
            './dist/index.js',
            './app/extensions.js',

            './test/app/**/*.js'
        ],
        reporters: ['progress'],
        autoWatch: false,
        singleRun: true,
        frameworks: ['jasmine'],
        browsers: ['ChromeHeadless'],
        plugins: [
            'karma-jasmine',
            'karma-chrome-launcher'
        ]
    });
};
