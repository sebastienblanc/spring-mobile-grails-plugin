/*
 * Copyright 2009-2015 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 * @author <a href='mailto:scm.blanc@gmail.com'>Sebastien Blanc</a>
 */
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor
import org.springframework.mobile.device.LiteDeviceResolver

class SpringMobileGrailsPlugin {
	def version = '1.1.3'
	def grailsVersion = '2.0 > *'
	def title = 'Spring Mobile Plugin'
	def description = 'Device resolver based on the Spring Mobile Library'

	def documentation = 'http://grails.org/plugin/spring-mobile'
	def license = 'APACHE'

	def developers = [
			[name: 'Burt Beckwith', email: 'burt@burtbeckwith.com'],
			[name: 'Sebastien Blanc', email: 'scm.blanc@gmail.com']
	]
	def issueManagement = [url: 'https://github.com/burtbeckwith/grails-spring-mobile/issues']
	def scm = [url: 'https://github.com/burtbeckwith/grails-spring-mobile']

	def observe = ['controllers']

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
