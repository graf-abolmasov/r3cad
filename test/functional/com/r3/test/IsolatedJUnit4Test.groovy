package com.r3.test

import geb.remote.services.Services
import org.junit.After

abstract class IsolatedJUnit4Test extends geb.junit4.GebReportingTest {

    @After
    void resetDataBase(){
        Services.databaseCleaner.reset()
    }
}
