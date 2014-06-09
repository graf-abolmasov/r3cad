package com.r3.dataset

class KmPkPlusTest extends GroovyTestCase {

    void testTypeCastToLocation(){
        def kmPkPlus = new KmPkPlus(km: 1, pk: 2, plus: 3)
        def location = kmPkPlus.toLocation()

        assert location == new Location(1, 2, 3)
    }
}
