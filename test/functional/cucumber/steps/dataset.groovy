package cucumber.steps

import cucumber.api.PendingException
import geb.pages.com.r3.dataset.DataSetViews
import geb.remote.domain.Domains
import geb.remote.services.Services

import static cucumber.api.groovy.EN.*

When(~'^I open data set "([^"]*)" for edit$') { String dataSetTitle ->
    def projectId = Domains.project.first()
    def dataSetId = Domains.dataSet.findByTitleAndProject(dataSetTitle, projectId)
    to(DataSetViews.edit, projectId, dataSetId)
}