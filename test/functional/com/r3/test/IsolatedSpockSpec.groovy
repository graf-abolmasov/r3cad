package com.r3.test

import geb.remote.services.Services

class IsolatedSpockSpec extends  geb.spock.GebReportingSpec {

    def cleanup() {
        Services.databaseCleaner.reset()
    }
}
