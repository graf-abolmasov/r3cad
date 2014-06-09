package cucumber.steps

import geb.remote.domain.Domains
import geb.remote.services.Services

import static cucumber.api.groovy.EN.*

Given(~'^I have (\\d+) demo project in the database$') { int projectsInDb ->
    Services.demoData.createDemoData(projectsInDb)
    assert Domains.project.count() == projectsInDb
}

When(~'^I click create new demo project button$') { ->
    page.clickNewProjectBtn()
}

Then(~'^I should have (\\d+) project in the database$') { int projectsInDb ->
    assert Domains.project.count() == projectsInDb
}
