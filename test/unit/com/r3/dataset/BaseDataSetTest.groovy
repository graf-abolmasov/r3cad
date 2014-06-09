package com.r3.dataset

import com.r3.drawing.area.RepresentationRole
import grails.test.mixin.Mock
import grails.test.mixin.TestMixin
import grails.test.mixin.web.GroovyPageUnitTestMixin
import org.codehaus.groovy.grails.web.pages.discovery.GrailsConventionGroovyPageLocator
import org.junit.Before

import java.lang.reflect.ParameterizedType

/**
 * Created by graf on 17/06/14.
 */
@Mock(BaseDataSet)
@TestMixin(GroovyPageUnitTestMixin)
abstract class BaseDataSetTest<T extends BaseDataSet> {

    private static final String TEMPLATE_PATH = '/drawing/dataSet/'

    protected Class<T> dataSetClass

    @Before
    void init() {
        ParameterizedType superclass = getClass().getGenericSuperclass() as ParameterizedType
        def typeArguments = superclass.getActualTypeArguments()
        dataSetClass = typeArguments[0] as Class<T>
    }

    void testTemplateReallyExists() {
        final DataSet dataSet = dataSetClass.newInstance()

        final representationRoles = RepresentationRole.values()
        final Set<String> testedTemplates = new HashSet<String>(representationRoles.size())

        def locator = applicationContext.getBean(GrailsConventionGroovyPageLocator)
        representationRoles.each { representationRole ->
            def template = dataSet.getTemplate(representationRole)
            if (!testedTemplates.contains(template)) {
                assert locator.findTemplate(TEMPLATE_PATH + template)
                testedTemplates.add(template)
            }
        }
    }
}
