package com.r3
import grails.util.GrailsNameUtils

import javax.annotation.PostConstruct
import java.util.regex.Pattern

class ResponseTimeFilters {

    private static final String START_TIME_ATTRIBUTE = '__START_TIME__'
    private static final String VIEW_START_TIME_ATTRIBUTE = '__VIEW_START_TIME__'

    def grailsApplication

    def filters = {
        all(controller: '*', controllerExclude: 'assets', action: '*') {
            before = {
                request[START_TIME_ATTRIBUTE] = System.currentTimeMillis()
            }

            after = { Map model ->
                request[VIEW_START_TIME_ATTRIBUTE] = System.currentTimeMillis()
            }

            afterView = { Exception e ->
                long now = System.currentTimeMillis()
                long total = now - request[START_TIME_ATTRIBUTE]
                long view = now - request[VIEW_START_TIME_ATTRIBUTE]
                String formattedCtrlName = GrailsNameUtils.getClassName(controllerName, 'Controller')
                Map actionParamsOnly = getActionParams(params)
                int status = getStatus(response)

                log.info("$status [$request.method] $formattedCtrlName#$actionName in ${total / 1000}s " +
                        "(model: ${(total - view) / 1000}s, view ${view / 1000}s) " +
                        "params: ${actionParamsOnly} ")

            }
        }
    }

    private int getStatus(def response ) {
        def prop = response.hasProperty('status')
        if(prop?.field || prop?.getter) {
            return response.status
        }
        if (response.hasProperty('response')) {
            return getStatus(response.response)
        } else {
            log.warn("Cannot determine http status in this container")
            return 999
        }
    }

    private Map getActionParams(Map params) {
        Map result = new HashMap(params)
        result.remove('action')
        result.remove('controller')
        result.remove('format')
        return result
    }
}
