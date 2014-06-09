package com.r3.dataset

import grails.test.mixin.Mock
import grails.test.mixin.TestMixin
import grails.test.mixin.web.GroovyPageUnitTestMixin
import org.junit.Before

import java.lang.reflect.ParameterizedType

@Mock(BaseDataSet)
@TestMixin(GroovyPageUnitTestMixin)
abstract class AbstractDataEntryTest<S extends BaseDataSet, E extends DataEntry> {

    protected Class<S> dataSetClass
    protected Class<E> dataEntryClass

    @Before
    void init() {
        ParameterizedType superclass = getClass().getGenericSuperclass() as ParameterizedType
        def typeArguments = superclass.getActualTypeArguments()
        dataSetClass = typeArguments[0] as Class<S>
        dataEntryClass = typeArguments[1] as Class<E>
        mockForConstraintsTests(dataSetClass, [buildValidDataSet()])
        mockForConstraintsTests(dataEntryClass)
    }

    abstract S buildValidDataSet()

    abstract void testConstraints()
}
