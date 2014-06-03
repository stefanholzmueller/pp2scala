module.exports = function (grunt) {
    grunt.initConfig({
        bower: {
	    install: {
	    }
        },
	watch: {
		files: ['**/*'],
		options: {
			livereload: true
		}
	}
    });

    grunt.loadNpmTasks('grunt-bower-task');
    grunt.loadNpmTasks('grunt-contrib-watch');

    grunt.registerTask('dist', ['bower']);
}
