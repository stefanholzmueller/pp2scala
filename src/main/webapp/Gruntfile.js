module.exports = function (grunt) {
    grunt.initConfig({
        benchmark: {
            all: {
                src: ['benchmarks/*.js'],
                dest: 'benchmarks/results.csv'
            }
        },
        bower: {
            install: {
            }
        },
        tsd: {
            refresh: {
                options: {
                    command: 'reinstall',
                    latest: true,
                    config: 'tsd.json',
                    opts: {
                        // props from tsd.Options
                    }
                }
            }
        },
        watch: {
            files: ['**/*'],
            options: {
                livereload: true
            }
        }
    });

    grunt.loadNpmTasks('grunt-benchmark');
    grunt.loadNpmTasks('grunt-bower-task');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-tsd');

    grunt.registerTask('dist', ['bower']);
}
