//
// This script is executed by Grails after plugin was installed to project.
// This script is a Gant script so you can use all special variables provided
// by Gant (such as 'baseDir' which points on project base dir). You can
// use 'ant' to access a global instance of AntBuilder
//
// For example you can create directory under project tree:
//
//    ant.mkdir(dir:"${basedir}/grails-app/jobs")
//
Ant.mkdir(dir:"${basedir}/grails-app/web-app/WEB-INF/wurfl")
Ant.copy(file:"${pluginBasedir}/grails-app/conf/wurfl/web_browsers_patch.xml",
	todir:"${basedir}/web-app/WEB-INF/wurfl")
Ant.copy(file:"${pluginBasedir}/grails-app/conf/wurfl/wurfl-2.0.25.zip",
	todir:"${basedir}/web-app/WEB-INF/wurfl")