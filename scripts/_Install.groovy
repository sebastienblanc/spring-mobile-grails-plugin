ant.mkdir(dir:"$basedir/grails-app/web-app/WEB-INF/wurfl")

ant.copy(file:"$springMobilePluginDir/grails-app/conf/wurfl/web_browsers_patch.xml",
         todir:"$basedir/web-app/WEB-INF/wurfl")

ant.copy(file:"$springMobilePluginDir/grails-app/conf/wurfl/wurfl-2.0.25.zip",
         todir:"$basedir/web-app/WEB-INF/wurfl")
