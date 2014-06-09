package com.r3.dataset

import com.r3.Project
import grails.rest.RestfulController

class DistanceToWayController extends RestfulController<DistanceToWay> {

    static scope = 'prototype'

    static allowedMethods = [
            index: 'GET',
            create: 'GET',
            show: 'GET',
            edit: 'GET',
            save: 'POST',
            update: 'PUT',
            delete: 'DELETE'
    ]

    private Project project
    private DataSet dataSet
    private DataEntry dataEntry

    static beforeInterceptor = {
        project = Project.read(params.long('projectId'))
        if (project == null) {
            notFound()
            return false
        }

        dataSet = BaseDataSet.read(params.long('dataSetId'))
        if (dataSet == null || dataSet.metaInfo.calculated) {
            notFound()
            return false
        }

        if (dataSet.projectId != project.id) {
            notFound()
            return false
        }

        def dataEntryClassName = dataSet.class.canonicalName.replaceAll('DataSet', 'DataEntry')
        def dataEntryClass = grailsApplication.getDomainClass(dataEntryClassName).clazz

        dataEntry = dataEntryClass.read(params.long('dataEntryId'))
        if (dataEntry == null || dataEntry.dataSet.id != dataSet.id) {
            notFound()
            return false
        }
    }

    DistanceToWayController() {
        super(DistanceToWay.class)
    }

    @Override
    protected List<DistanceToWay> listAllResources(Map params) {
        return dataEntry.distancesToWays
    }

    @Override
    protected Integer countResources() {
        return dataEntry.distancesToWays.size()
    }
}
