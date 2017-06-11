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
            },
            test: {
                singleRun: true,
                autoWatch: false
            }
        }
    });

    grunt.registerTask('default', ['build'])
    grunt.registerTask('build', ['test'])
    grunt.registerTask('dev', ['karma:unit'])
    grunt.registerTask('test', ['karma:test'])
};
