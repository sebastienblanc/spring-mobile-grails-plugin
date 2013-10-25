import org.springframework.mobile.device.DeviceResolverHandlerInterceptor
import org.springframework.mobile.device.LiteDeviceResolver

class SpringMobileGrailsPlugin {
	def version = '0.5.1'
	def grailsVersion = '2.0 > *'
	def title = 'Spring Mobile Grails plugin'
	def description = 'Device resolver based on the Spring Mobile Library'
	def documentation = 'http://grails.org/plugin/spring-mobile'
	def observe = ['controllers']

	def license = 'APACHE'
	def developers = [
		[name: 'Burt Beckwith', email: 'burt@burtbeckwith.com'],
		[name: 'Sebastien Blanc', email: 'scm.blanc@gmail.com']
	]
	def issueManagement = [system: 'GITHUB', url: 'https://github.com/burtbeckwith/grails-spring-mobile/issues']
	def scm = [url: 'https://github.com/burtbeckwith/grails-spring-mobile']

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

		klass.metaClass.isMobile = { -> request.currentDevice.isMobile() }

		klass.metaClass.isTablet = { -> request.currentDevice.isTablet() }

		klass.metaClass.isNormal = { -> request.currentDevice.isNormal() }
	}

	def onChange = { event ->
		for (cc in application.controllerClasses) {
			addDynamicMethods cc.clazz
		}
	}
}
