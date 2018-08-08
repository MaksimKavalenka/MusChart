module.exports = function (grunt) {
    grunt.initConfig({
        concat: {
            options: {
                separator: '\n\n//_______________________________________________________________________\n\n'
            },
            dist: {
                src: ['src/main/webapp/static/js/**/*.js'],
                dest: 'src/main/webapp/static/script.js'
            }
        },
        concat_css: {
            all: {
                src: ['src/main/webapp/static/css/**/*.css'],
                dest: 'src/main/webapp/static/style.css'
            },
        },
        copy: {
            compile: {
                files: [{
                    expand: true,
                    src: ['**/*.html', '**/*.css', '**/*.json', '!node_modules/**', '!*'],
                    dest: 'dest/'
                }]
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-concat-css');
    grunt.loadNpmTasks('grunt-contrib-copy');

    grunt.registerTask('default', ['concat', 'concat_css', 'copy']);
};