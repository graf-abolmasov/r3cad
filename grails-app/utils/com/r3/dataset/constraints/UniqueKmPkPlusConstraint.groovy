package com.r3.dataset.constraints

import com.r3.dataset.KmPkPlus
import org.codehaus.groovy.grails.orm.hibernate.GrailsHibernateTemplate;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions


/**
 * Created by graf on 06/06/14.
 */
class UniqueKmPkPlusConstraint {

    static persistent = true

    static defaultMessageCode = "default.not.unique.message"
    static defaultMessage = "KmPkPlus is not unique within data set"

    def supports = { propertyClass ->
        propertyClass == KmPkPlus
    }

    def dbCall = { propertyValue, dataEntry, Session session ->
        session.setFlushMode(FlushMode.MANUAL);

        try {
            if(propertyValue != null) {
                Criteria criteria = session.createCriteria(constraintOwningClass)
                        .add(Restrictions.eq(constraintPropertyName, propertyValue))
                        .add(Restrictions.eq('dataSet', dataEntry.dataSet))
                        .add(Restrictions.ne('id', dataEntry.id))
                return criteria.list()
            } else {
                return null
            }
        } finally {
            session.setFlushMode(FlushMode.AUTO)
        }
    }

    def validate = { propertyValue, dataEntry ->
        dbCall.delegate = delegate
        def _v = dbCall.curry(propertyValue, dataEntry) as GrailsHibernateTemplate.HibernateCallback
        def result = hibernateTemplate.executeFind(_v)

        return result ? false : true
    }
}
