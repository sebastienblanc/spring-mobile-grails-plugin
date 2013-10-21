import org.springframework.mobile.device.DeviceResolverHandlerInterceptor
import org.springframework.mobile.device.LiteDeviceResolver

class SpringMobileGrailsPlugin {
	def version = '0.4'
	def grailsVersion = '2.0 > *'
	def title = 'Spring Mobile Grails plugin'
	def description = 'Device resolver based on the Spring Mobile Library'
	def documentation = 'http://grails.org/plugin/spring-mobile'
	def observe = ['controllers']

	def license = 'APACHE'
	def developers = [
		[name: 'Sebastien Blanc', email: 'scm.blanc@gmail.com']
	]
	def issueManagement = [system: 'GITHUB', url: 'https://github.com/sebastienblanc/spring-mobile-grails-plugin']
	def scm = [url: 'https://github.com/sebastienblanc/spring-mobile-grails-plugin']

	def doWithSpring = {
		deviceResolver(LiteDeviceResolver)
		deviceResolverHandlerInterceptor(DeviceResolverHandlerInterceptor, ref('deviceResolver'))
	}

	def doWithDynamicMethods = { ctx ->
		for (cc in application.controllerClasses) {
			addDynamicMethods cc.clazz
		}
	}

	private void addDynamicMethods(klass) {
		klass.metaClass.withMobileDevice = { Closure closure ->
			def device = request.currentDevice
			if (device?.isMobile()) {
				closure.call device
			}
		}
	}

	def onChange = { event ->
		for (cc in application.controllerClasses) {
			addDynamicMethods cc.clazz
		}
	}
}
