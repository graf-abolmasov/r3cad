grails.servlet.version = "3.0"
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.8
grails.project.source.level = 1.8
grails.project.war.file = "target/${appName}.war"

grails.project.fork =  [
    run: false, //[maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    //test: [maxMemory: 768, minMemory: 64, debug: false, forkReserve: false, daemon: true],
    //test: System.env.CI ? false : [maxMemory: 768, minMemory: 64, debug: false, forkReserve: false, daemon: true],
    test :false,
    war: [maxMemory: 768, minMemory: 64, debug: false],
    console: [maxMemory: 768, minMemory: 64, debug: false]
]

def gebVersion = "0.10.0"
def phantomJSVersion = "1.1.0"
def webdriverVersion = "2.42.2"

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    inherits "global"
    log "error"
    checksums true
    legacyResolve false

    repositories {
        inherits true

        grailsPlugins()
        grailsHome()
        mavenLocal()
        grailsCentral()
        mavenCentral()
    }

    dependencies {
        runtime 'org.postgresql:postgresql:9.3-1101-jdbc41'

        compile "org.springframework:spring-orm:$springVersion"

        test "org.gebish:geb-junit4:${gebVersion}"
        test "org.gebish:geb-spock:${gebVersion}"

        test "org.seleniumhq.selenium:selenium-support:${webdriverVersion}"
        test "org.seleniumhq.selenium:selenium-chrome-driver:${webdriverVersion}"
        test "org.seleniumhq.selenium:selenium-firefox-driver:${webdriverVersion}"
        test "org.seleniumhq.selenium:selenium-ie-driver:${webdriverVersion}"

        test("com.github.detro.ghostdriver:phantomjsdriver:${phantomJSVersion}") {
            transitive = false
        }
    }

    plugins {
        build ":tomcat:7.0.54"

        runtime ":jquery:1.11.1"
        runtime ':twitter-bootstrap:3.2.1'
        compile ":scaffolding:2.1.2"
        compile ":asset-pipeline:1.9.9"
        compile ":sass-asset-pipeline:1.9.1"
        compile ":coffee-asset-pipeline:1.8.0"
        compile ":rendering:1.0.0"
        compile ":haml:0.3"

        runtime ":hibernate4:4.3.6.1"
        runtime ":database-migration:1.4.0"
        compile ":platform-core:1.0.1-SNAPSHOT"
        compile ":constraints:0.8.1"
        compile ":csv:0.3.1"

        compile ':cache:1.1.7'
        compile ':cache-ehcache:1.0.0'

//        compile ':cache:1.1.8'
//        compile ':cache-ehcache:1.0.4'

        compile ":build-test-data:2.2.2"
        compile ":fixtures:1.3"
        compile ":remote-control:1.5"
        test(":cucumber:1.0.1") {
            exclude "resources"
        }
        test ":geb:${gebVersion}"
        test ":code-coverage:2.0.3-3"
    }
}
