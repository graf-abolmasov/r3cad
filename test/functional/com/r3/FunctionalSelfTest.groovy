package com.r3

import static geb.remote.domain.Domains.project as Project

import com.r3.test.IsolatedJUnit4Test
import geb.pages.com.r3.RootPage
import geb.remote.services.Services
import org.junit.Test

class FunctionalSelfTest extends IsolatedJUnit4Test {

    @Test
    void testGoToRootPage() {
        to(RootPage)
        at(RootPage)
    }

    @Test
    void testClickButton() {
        to(RootPage)
        page.clickNewProjectBtn()

        assert Project.count() == 1
    }

    @Test
    void testResetDatabase(){
        Services.demoData.createDemoData(2)
        assert Project.count() == 2
        Services.databaseCleaner.reset()
        assert Project.count() == 0
    }

    @Test
    void testDatabaseEmpty() {
        assert Project.count() == 0
    }
}
