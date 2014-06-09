package cucumber.steps

import static cucumber.api.groovy.EN.*

Then(~'^I should see page with title "([^"]*)"$') { String pageTitle ->
    assert page.title == pageTitle
}

Then(~'^I should see "([^"]*)"$') { String string ->
    waitFor(30, 0.5) { page.contains(string) }
}
