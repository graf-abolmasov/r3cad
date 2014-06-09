package cucumber.steps

import static cucumber.api.groovy.EN.*

import geb.pages.com.r3.RootPage
import geb.remote.services.Services

Given(~'^I visit root page$') { ->
    to(RootPage)
    at(RootPage)
}

When(~'^I clean the database$') { ->
    Services.databaseCleaner.reset()
}