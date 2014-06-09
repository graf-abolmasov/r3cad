package geb.remote.services.com.r3

import geb.remote.services.ServiceRemoteControl

/**
 * Created by graf on 12/06/14.
 */
class DemoDataServiceRemoteControl extends ServiceRemoteControl {

    void createDemoData(int count = 1) {
        remote.exec {
            ctx.demoService.createDemoData(count)
            true
        }
    }
}
