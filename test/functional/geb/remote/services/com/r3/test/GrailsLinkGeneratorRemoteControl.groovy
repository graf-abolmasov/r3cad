package geb.remote.services.com.r3.test

import geb.remote.services.ServiceRemoteControl
import grails.plugin.remotecontrol.RemoteControl
import org.codehaus.groovy.grails.web.mapping.LinkGenerator

/**
 * Created by graf on 12/06/14.
 */
class GrailsLinkGeneratorRemoteControl extends ServiceRemoteControl {

    String link(Map params) {
        remote.exec { ctx.grailsLinkGenerator.link(params) }
    }
}
