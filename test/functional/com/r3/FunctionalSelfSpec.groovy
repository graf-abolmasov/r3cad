package com.r3

import static geb.remote.domain.Domains.project as Project

import com.r3.test.IsolatedJUnit4Test
import geb.pages.com.r3.RootPage
import geb.remote.services.Services
import org.junit.Test

class FunctionalSelfSpec extends geb.spock.GebReportingSpec {

    void "should go to root page"() {
        expect: to(RootPage)
    }

    void "should click on clickable element"() {
        given: "root page"
        to(RootPage)

        when: "I click 'Create New Project' button"
        page.clickNewProjectBtn()

        then: "there should be one project in the database"
        assert Project.count() == 1
    }

    void "should reset database"(){
        given: "there are 2 projects in the database"
        Services.demoData.createDemoData(2)

        when: "I reset the database"
        Services.databaseCleaner.reset()

        then: "there should be no projects in the database"
        assert Project.count() == 0
    }

    void "should start with empty database"() {
        expect: Project.count() == 0
    }
}
