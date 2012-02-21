import net.sourceforge.wurfl.core.Device;

import org.codehaus.groovy.grails.commons.ApplicationHolder;
import org.codehaus.groovy.grails.commons.ConfigurationHolder;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.mobile.device.LiteDeviceResolver;
import org.springframework.mobile.device.wurfl.WurflDeviceResolver;
import org.springframework.mobile.device.wurfl.WurflManagerFactoryBean

class SpringMobileGrailsPlugin {
	// the plugin version
	def version = "0.3"
	// the version or versions of Grails the plugin is designed for
	def grailsVersion = "1.3.6 > *"
	
	// resources that are excluded from plugin packaging
	def pluginExcludes = [
		"grails-app/views/error.gsp"
	]

	// TODO Fill in these fields
	def author = "Sebastien Blanc"
	def authorEmail = "sblanc@e-id.nl"
	def title = "Spring Mobile Grails plugin"
	def description = '''\\
Device resolver based on the Spring Mobile Library
'''

	// URL to the plugin's documentation
	def documentation = "http://grails.org/plugin/spring-mobile"
	
	def wurflDeviceResolver
	def config = ConfigurationHolder.config
	def doWithWebDescriptor = { xml ->
		// TODO Implement additions to web.xml (optional), this event occurs before
	}
	def watchedResources = ["file:./grails-app/controllers/*Controller.groovy"]
	def doWithSpring = {
		
		
		if(config.springMobile?.deviceResolver=='wurfl'){
			wurflManager(WurflManagerFactoryBean, '/WEB-INF/wurfl/wurfl-2.0.25.zip') { patchLocations = '/WEB-INF/wurfl/web_browsers_patch.xml' }
			deviceResolver(WurflDeviceResolver, ref('wurflManager'))
			deviceResolverHandlerInterceptor(DeviceResolverHandlerInterceptor, ref('deviceResolver'))
			
				
			
		}
		else{
			deviceResolver(LiteDeviceResolver)
			deviceResolverHandlerInterceptor(org.springframework.mobile.device.DeviceResolverHandlerInterceptor, ref('deviceResolver'))
			
		}
	
		
		
	}
	
	def doWithDynamicMethods = { ctx ->
		def application = ApplicationHolder.application

		application.getArtefacts("Controller").each { klass -> addDynamicMethods(klass) }
	}

	private addDynamicMethods(klass) {

		klass.metaClass.withMobileDevice = { Closure closure ->
			def device = request.getAttribute("currentDevice")
			if(device.isMobile()){
				closure.call(device)
			}
		}
	}

	def doWithApplicationContext = { applicationContext ->
		
	}

	def onChange = { event ->
		def application = event.application
		application.getArtefacts("Controller").each { klass -> addDynamicMethods(klass) }	}

	def onConfigChange = { event ->
		// TODO Implement code that is executed when the project configuration changes.
		// The event is the same as for 'onChange'.
	}
}
