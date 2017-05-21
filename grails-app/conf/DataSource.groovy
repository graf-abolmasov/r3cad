dataSource {
    pooled = true
    jmxExport = true
}

hibernate {
    cache.use_second_level_cache = false
    cache.use_query_cache = false
    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
    singleSession = true // configure OSIV singleSession mode
}

environments {
    development {
        dataSource {
            dialect = com.r3.hibernate.FixedH2Dialect
            driverClassName = "org.h2.Driver"
            username = "sa"
            password = ""
            logSql = true
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=TRUE"
        }
    }
    test {
        dataSource {
            dialect = com.r3.hibernate.FixedH2Dialect
            driverClassName = "org.h2.Driver"
            username = "sa"
            password = ""
            dbCreate = "create-drop"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=TRUE"
        }
    }
    production {
        dataSource {
            dbCreate = "create-drop"
            driverClassName: org.postgresql.Driver
            url = "jdbc:postgresql://localhost:5432/r3_production"
            username = "r3cad"
            password = "r3cad"
            // jndiName = "java:comp/env/r3prodDS"
            dialect =  com.r3.hibernate.FixedPostgresPlusDialect
            properties {
                // See http://grails.org/doc/latest/guide/conf.html#dataSource for documentation
                jmxEnabled = true
                initialSize = 5
                maxActive = 50
                minIdle = 5
                maxIdle = 25
                maxWait = 10000
                maxAge = 10 * 60000
                timeBetweenEvictionRunsMillis = 5000
                minEvictableIdleTimeMillis = 60000
                testOnBorrow = true
                testWhileIdle = true
                testOnReturn = false
                jdbcInterceptors = "ConnectionState"
                defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED
            }
        }
    }
}
