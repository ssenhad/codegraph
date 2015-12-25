'use strict';

module.exports = function (grunt) {
    require('load-grunt-tasks')(grunt);

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),

        karma: {
            options: { configFile: 'test/karma.conf.js' },
            unit: {
                singleRun: false,
                autoWatch: true
            }
        }
    });

    grunt.registerTask('default', ['test'])
    grunt.registerTask('test', ['karma:unit'])
};
