package com.r3.dataset

import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.LOCATION
import static org.springframework.http.HttpStatus.*

import com.r3.RailWay
import grails.transaction.Transactional
import grails.util.GrailsNameUtils

@Transactional(readOnly = true)
class DataSetController {

    static scope = 'prototype'

    static allowedMethods = [
            index : 'GET',
            create: 'GET',
            show  : 'GET',
            edit  : 'GET',
            save  : 'POST',
            update: 'PUT',
            delete: 'DELETE'
    ]

    private Class<? extends DataSet> dataSetClass
    private String dataSetClassName
    private dataSetType

    def index(Long projectId) {
        def allResources = allDataSets(projectId)
        def countResources = countDataSets(projectId)
        response.addHeader('Content-Range:', "items 0-${allResources.size() - 1}/${countResources}")
        respond(allResources, model: [dataSetCount: countResources, dataSetInstanceList: allResources])
    }

    def create() {
        withDataSet(createDataSet()) { DataSet dataSet ->
            respond(dataSet, model: viewModel(dataSet))
        }
    }

    def show(Long projectId, Long id) {
        withDataSet(projectId, id) { DataSet dataSet ->
            respond(dataSet, model: viewModel(dataSet))
        }
    }

    def edit(Long projectId, Long id) {
        withDataSet(projectId, id) { DataSet dataSet ->
            respond(dataSet, model: viewModel(dataSet))
        }
    }

    @Transactional
    def save() {
        withDataSet(createDataSet()) { DataSet dataSet ->
            saveAndRespond(dataSet, 'default.created.message', 'create')
        }
    }

    @Transactional
    def update(Long projectId, Long id) {
        withDataSet(projectId, id) { DataSet dataSet ->
            bindData(dataSet, request)
            saveAndRespond(dataSet, 'default.updated.message', 'edit')
        }
    }

    @Transactional
    def delete(Long projectId, Long id) {
        withDataSet(projectId, id) { dataSet ->
            dataSet.delete()

            request.withFormat {
                form multipartForm {
                    setFlashMessage('default.deleted.message', dataSet)
                    redirect(action: "index")
                }
                '*' { render(status: NO_CONTENT) }
            }
        }
    }

    private Map<String, Object> viewModel(DataSet dataSet) {
        def railWays = RailWay.findAllByProject(dataSet.project)
        return [dataSetInstance: dataSet, railWayInstances: railWays, dataSetType: dataSetType]
    }

    private String dataSetUserFriendlyType() {
        message(code: "${dataSetClassName}.label", default: dataSetClassName).toString()
    }

    private void saveAndRespond(final DataSet dataSet, final String messageCode, final String redirectToOnFail) {
        def successStatus = dataSet.id == null ? CREATED : OK
        if (dataSet.save()) {
            request.withFormat {
                form multipartForm {
                    setFlashMessage(messageCode, dataSet.id)
                    redirect(resource: 'dataSet', action: 'edit', id: dataSet.id, params: [projectId: dataSet.projectId])
                }
                '*' {
                    setLocationHeader()
                    respond(dataSet, status: successStatus)
                }
            }
        } else {
            respond(dataSet.errors, view: redirectToOnFail, model: viewModel(dataSet))
        }
    }

    private void setFlashMessage(final String messageCode, final Long id) {
        flash.message = message(code: messageCode, args: [dataSetUserFriendlyType(), id])
    }

    private setLocationHeader() {
        def location = g.createLink(resource: controllerName, action: 'show', id: dataSet.id, absolute: true).toString()
        response.addHeader(LOCATION, location)
    }

    private DataSet queryDataSet(final Long projectId, final Long id) {
        final DataSet dataSet = BaseDataSet.where {
            project.id == projectId && id == id
        }.find()

        if (dataSet == null) {
            return null
        }

        specifyDataSetClass(dataSet.class)
        return dataSet
    }

    private void specifyDataSetClass(final Class<DataSet> clazz) {
        dataSetClass = clazz
        dataSetClassName = clazz.simpleName
        dataSetType = GrailsNameUtils.getPropertyName(clazz).replaceAll('DataSet', '')
    }

    private DataSet createDataSet() {
        def domainClass = grailsApplication.getDomainClass("com.r3.dataset.${params['type']}DataSet")
        if (domainClass == null) {
            return null
        }

        specifyDataSetClass(domainClass.clazz)

        final DataSet dataSet = dataSetClass.newInstance()
        bindData(dataSet, request)
        return dataSet
    }

    private static List<? extends DataSet> allDataSets(final Long projectId) {
        return BaseDataSet.where { project.id == projectId }.list()
    }

    private static Integer countDataSets(final Long projectId) {
        return BaseDataSet.where { project.id == projectId }.count()
    }

    private void notFound() {
        request.withFormat {
            form multipartForm {
                setFlashMessage('default.not.found.message', params.long('id'))
                redirect(action: "index")
            }
            '*' { render status: NOT_FOUND }
        }
    }

    private void withDataSet(final Long projectId, final Long id, Closure c) {
        withDataSet(queryDataSet(projectId, id), c)
    }

    private void withDataSet(final DataSet dataSet, Closure c) {
        if (dataSet) {
            c.call(dataSet)
        } else {
            notFound()
        }
    }
}
