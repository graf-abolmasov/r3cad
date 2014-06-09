package com.r3.dataset

import static org.springframework.http.HttpStatus.*

import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.HttpHeaders

class DataEntryController {

    static scope = 'prototype'

    private DataSet dataSet

    private Class<? extends DataEntry> dataEntryClass
    private String dataEntryClassName

    static allowedMethods = [
            index : 'GET',
            create: 'GET',
            show  : 'GET',
            edit  : 'GET',
            save  : 'POST',
            update: 'PUT',
            delete: 'DELETE'
    ]

    def beforeInterceptor = {
        dataSet = BaseDataSet.where {
            project.id == params.long('projectId') && id == params.long('dataSetId')
        }.find()

        if (dataSet == null || dataSet.metaInfo.calculated) {
            notFound()
            return false
        }

        def dataEntryClassCanonicalName = dataSet.class.canonicalName.replaceAll('DataSet', 'DataEntry')
        dataEntryClass = grailsApplication.getDomainClass(dataEntryClassCanonicalName).clazz
        dataEntryClassName = dataEntryClass.simpleName
    }

    def index() {
        request.withFormat {
            json {
                def countResources = dataSet.values?.size() ?: 0
                response.addHeader('Content-Range:', "items 0-${countResources - 1}/${countResources}")
                respond(dataSet.values)
            }
            '*' {
                model:
                [dataSetInstance: dataSet, dataSetType: dataEntryClassName.replaceAll('DataEntry', '')]
            }
        }
    }

    def show(Long id) {
        def dataEntry = queryDataEntry(id)
        respond(dataEntry)
    }

    def create() {
        def dataEntry = createNextDataEntry()
        respond(dataEntry)
    }

    def edit(Long id) {
        def dataEntry = queryDataEntry(id)
        respond(dataEntry)
    }

    @Transactional
    def save() {
        def dataEntry = createDataEntry()
        saveAndRespond(dataEntry)
    }

    @Transactional
    def update(Long id) {
        withDataEntry(id) { DataEntry dataEntry ->
            def version = params.long('version')
            if (version != null && dataEntry.version > version) {
                dataEntry.errors.rejectValue("version", "default.optimistic.locking.failure")
                dataEntry.discard()
                respond(dataEntry.errors, status: CONFLICT)
                return
            }
            bindDataToInstance(dataEntry)
            saveAndRespond(dataEntry)
        }
    }

    @Transactional
    def delete(Long id) {
        withDataEntry(id) { DataEntry dataEntry ->
            dataEntry.delete()
            render(status: NO_CONTENT)
        }
    }

    protected void notFound() {
        render(status: NOT_FOUND)
    }

    private void setLocationHeader(final DataEntry instance) {
        def location = g.createLink([resource : 'dataSet/dataEntry',
                                     action   : 'show',
                                     id       : instance.id,
                                     dataSetId: dataSet.id,
                                     params   : [projectId: dataSet.projectId],
                                     absolute : true
        ]).toString()
        response.addHeader(HttpHeaders.LOCATION, location)
    }

    protected DataEntry createDataEntry() {
        final DataEntry dataEntry = dataEntryClass.newInstance()
        bindDataToInstance(dataEntry)
        return dataEntry
    }

    protected DataEntry createNextDataEntry(int increment = 25 * Location.MM_IN_METER) {
        DataEntry dataEntry = createDataEntry()
        Location lastLocation = dataSet.values ? dataSet.values.last() : new Location(0, 0, -increment)
        Location nextLocation = lastLocation + increment
        dataEntry.setLocation(nextLocation)
        return dataEntry
    }

    private void bindDataToInstance(final DataEntry dataEntry) {
        bindData(dataEntry, request)
        dataEntry.dataSet = dataSet
    }

    protected DataEntry queryDataEntry(final Long id) {
        return dataEntryClass.findByDataSetAndId(dataSet, id)
    }

    private saveAndRespond(final DataEntry dataEntry) {
        def successStatus = dataEntry.id == null ? CREATED : OK
        if (dataEntry.save()) {
            setLocationHeader(dataEntry)
            respond(dataEntry, status: successStatus)
        } else {
            respond(dataEntry.errors)
        }
    }

    private void withDataEntry(final Long id, Closure c) {
        def dataEntry = queryDataEntry(id)
        if (dataEntry) {
            c.call(dataEntry)
        } else {
            notFound()
        }
    }
}

