package com.r3.test

import grails.transaction.Transactional
import org.hibernate.tool.hbm2ddl.SchemaExport

@Transactional
class DatabaseCleanerService {

    def grailsApplication

    def symbolLibraryService

    def reset() {
        def sessionFactory = grailsApplication.mainContext.getBean('&sessionFactory')
        def configuration = sessionFactory.configuration

        def schemaExport = new SchemaExport(configuration, sessionFactory.dataSource.connection)
        schemaExport.execute(false, true, false, false)

        symbolLibraryService.createDefaultSymbols()
    }
}
