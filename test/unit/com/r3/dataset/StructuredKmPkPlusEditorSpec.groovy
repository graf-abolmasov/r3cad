package com.r3.dataset

import spock.lang.Shared
import spock.lang.Specification


/**
 * Created by graf on 16/06/14.
 */
class StructuredKmPkPlusEditorSpec extends Specification {

    @Shared StructuredKmPkPlusEditor kmPkPlusEditor = new StructuredKmPkPlusEditor()

    void 'StructuredKmPkPlusEditor#getPropertyValue'() {
        given:
        def params = [km: location_km, pk: location_pk, plus: location_plus]

        when:
        def kmPkPlus = kmPkPlusEditor.getPropertyValue(params)

        then:
        assert kmPkPlus.km == km
        assert kmPkPlus.pk == pk
        assert kmPkPlus.plus == plus

        where:
        location_km | location_pk | location_plus       || km | pk | plus
        null        | null        | null                || 0  | 0  | 0
        0           | null        | null                || 0  | 0  | 0
        1           | null        | null                || 1  | 0  | 0
        1           | 1           | 1.0                 || 1  | 1  | 1000
        '1'         | 1           | 1.0                 || 1  | 1  | 1000
        '1'         | 1           | 1.2                 || 1  | 1  | 1200
        '1'         | 1           | (5.0/3)             || 1  | 1  | 1667
        '1'         | 1           | (5.0/3).toString()  || 1  | 1  | 1667
        '1'         | 1           | 0.00001             || 1  | 1  | 0
        '1'         | 1           | 0.0001              || 1  | 1  | 0
        '1'         | 1           | 0.001               || 1  | 1  | 1
    }
}
