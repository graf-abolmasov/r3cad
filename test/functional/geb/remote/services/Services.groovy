package geb.remote.services

import geb.remote.services.com.r3.test.DatabaseCleanerServiceRemoteControl
import geb.remote.services.com.r3.DemoDataServiceRemoteControl
import geb.remote.services.com.r3.test.GrailsLinkGeneratorRemoteControl

final class Services {

    static DemoDataServiceRemoteControl demoData = new DemoDataServiceRemoteControl()

    static DatabaseCleanerServiceRemoteControl databaseCleaner = new DatabaseCleanerServiceRemoteControl()

    static GrailsLinkGeneratorRemoteControl grailsLinkGenerator = new GrailsLinkGeneratorRemoteControl()
}
