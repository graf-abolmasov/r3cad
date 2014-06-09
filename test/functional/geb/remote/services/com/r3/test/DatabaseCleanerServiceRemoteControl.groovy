package geb.remote.services.com.r3.test

import geb.remote.services.ServiceRemoteControl
import grails.plugin.remotecontrol.RemoteControl

/**
 * Created by graf on 12/06/14.
 */
class DatabaseCleanerServiceRemoteControl extends ServiceRemoteControl {

    void reset() {
        remote.exec {
            ctx.databaseCleanerService.reset()
            true
        }
    }
}
