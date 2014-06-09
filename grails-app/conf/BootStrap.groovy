import com.r3.utils.JsonUtils
import grails.util.Environment

class BootStrap {

    def grailsApplication

    def demoService

    def symbolLibraryService

    def init = { servletContext ->

        JsonUtils.registerCustomMarshallers()

        grailsApplication.config.grails.plugin.platform.events.gorm.disabled = true
        symbolLibraryService.createDefaultSymbols()

        if (Environment.currentEnvironment != Environment.TEST) {
            demoService.createDemoData(1)
        }
        grailsApplication.config.grails.plugin.platform.events.gorm.disabled = false
    }
}

