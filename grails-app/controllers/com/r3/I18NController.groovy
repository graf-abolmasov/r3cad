package com.r3

import org.springframework.web.servlet.support.RequestContextUtils

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

class I18NController {

    def init() {
        def locale = RequestContextUtils.getLocale(request)
        render view: 'init.js', contentType: 'application/javascript;charset=utf-8', model: [lang: locale.language]
    }

    def index(String i18nFileName) {
        def fileName = "grails-app/i18n/${i18nFileName}"
        if (Files.notExists(Paths.get(fileName)))
            fileName = 'grails-app/i18n/messages.properties'
        String content = readFile(fileName, StandardCharsets.UTF_8);
        render text: content, contentType: 'text/plain;charset=utf-8'
    }

    private static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
