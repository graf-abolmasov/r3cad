package com.r3

import grails.util.Environment
import org.springframework.web.servlet.support.RequestContextUtils

import java.nio.file.Files
import java.nio.file.Paths

class I18NController {

    def init() {
        def locale = RequestContextUtils.getLocale(request)
        render view: 'init.js', contentType: 'application/javascript;charset=utf-8', model: [lang: locale.language]
    }

    def index(String i18nFileName) {
        String content
        if (Environment.currentEnvironment != Environment.PRODUCTION) {
            content = getI18nForDevelopment(i18nFileName)
        } else {
            content = getI18nForProduction(i18nFileName)
        }
        render text: content, contentType: 'text/plain;charset=utf-8'
    }

    private String getI18nForDevelopment(String i18nFileName){
        def fileName = "grails-app/i18n/${i18nFileName}"
        if (Files.notExists(Paths.get(fileName)))
            fileName = 'grails-app/i18n/messages.properties'
        new FileInputStream(fileName).text
    }

    private String getI18nForProduction(String i18nFileName) {
        def mainContext = grailsApplication.mainContext
        def resource = mainContext.getResource("/WEB-INF/grails-app/i18n/${i18nFileName}")
        if (resource == null) {
            resource = mainContext.getResource("/WEB-INF/grails-app/i18n/messages.properties")
        }
        resource.inputStream.text
    }
}
