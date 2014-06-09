package cucumber.support

import geb.Browser
import geb.binding.BindingUpdater
import geb.remote.services.com.r3.test.DatabaseCleanerServiceRemoteControl

import static cucumber.api.groovy.Hooks.*

Before() {
    bindingUpdater = new BindingUpdater(binding, new Browser())
    bindingUpdater.initialize()
}

After() {
    new DatabaseCleanerServiceRemoteControl().reset()
    bindingUpdater.remove()
}

