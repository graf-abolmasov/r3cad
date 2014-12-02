// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination

grails.mime.disable.accept.header.userAgents = ['None']
grails.mime.types = [ // the first one is the default format
    all:           '*/*', // 'all' maps to '*' or the first available format in withFormat
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    hal:           ['application/hal+json','application/hal+xml'],
    xml:           ['text/xml', 'application/xml'],
    embeddedSvg:   'images/embeddedSvg',
    svg:           'image/svg',
    lisp:          'text/lisp'
]

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"
// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'none' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        // filteringCodecForContentType.'text/html' = 'html'
    }
}

grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'
// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// configure passing transaction's read-only attribute to Hibernate session, queries and criterias
// set "singleSession = false" OSIV mode in hibernate configuration after enabling
grails.hibernate.pass.readonly = false
// configure passing read-only to OSIV session by default, requires "singleSession = false" OSIV mode
grails.hibernate.osiv.readonly = false

environments {
    development {
        grails.logging.jul.usebridge = true
        grails.dbconsole.enabled = true
        grails.dbconsole.urlRoot = '/admin/dbconsole'
        grails.converters.default.pretty.print=true
    }
    production {
        grails.logging.jul.usebridge = false
        grails.serverURL = "http://r3cad.com/r3"
    }
}

log4j.main = {
    appenders {
        console name:'stdout', layout: pattern(conversionPattern: '%c{1} [%p]: %m%n')
    }

    error  'org.codehaus.groovy.grails.web.servlet',        // controllers
           'org.codehaus.groovy.grails.web.pages',          // GSP
           'org.codehaus.groovy.grails.web.sitemesh',       // layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping',        // URL mapping
           'org.codehaus.groovy.grails.commons',            // core / classloading
           'org.codehaus.groovy.grails.plugins',            // plugins
           'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
           'org.springframework',
           'org.hibernate'

    debug  'grails.app.services.com.r3'

    debug  'grails.app.filters.com.r3',
           'com.r3',
           'net.sf.ehcache.hibernate',
           'grails.app.services.com.r3.DemoService'
}

grails.cache.enabled = true
grails.cache.config = {
    cache {
        name 'pk2mm'
    }
    cache {
        name 'mapping'
    }
    cache {
        name 'bounds'
    }
}

grails.plugin.angularjs.version = "1.2.16"
grails.plugin.angularjs.i18n = ["ru-ru", "en-us"]
grails.plugin.angularjs.modules = ["animate", "cookies", "loader", "mocks", "resource", "route", "sanitize", "scenario", "touch"]

grails.plugins.twitterbootstrap.fixtaglib = false
grails.plugins.twitterbootstrap.defaultBundle = 'bundle_bootstrap'

//grails.assets.minifyJs = false
grails.assets.bundle = true
grails.assets.excludes = ['**/*.uncompressed.js']
grails.assets.includes = ["**/_*.*"]

metrics.servletEnabled = false

coverage.enabledByDefault = false
