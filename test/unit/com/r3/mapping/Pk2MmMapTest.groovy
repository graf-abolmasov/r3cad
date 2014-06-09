package com.r3.mapping

import com.r3.dataset.Location

/**
 * User: graf
 * Date: 11/7/13
 * Time: 1:57 AM
 */
class Pk2MmMapTest extends GroovyTestCase {
    
    void testGet() {
        final zp = 10

        def arr = new ArrayList(0)
        def mapping = new Pk2MmMap(zp, arr)
        assert mapping[0] == 0
        assert mapping[1] == 1 * Location.MM_IN_PK
        assert mapping[zp] == zp * Location.MM_IN_PK
        assert mapping[zp + 1] == (zp + 1) * Location.MM_IN_PK
        assert mapping[zp + 5] == (zp + 5) * Location.MM_IN_PK

        arr = new ArrayList(10)
        arr[0] = zp * Location.MM_IN_PK
        arr[1] = (zp + 1) * Location.MM_IN_PK - 1 * Location.MM_IN_METER
        arr[2] = (zp + 2) * Location.MM_IN_PK - 1 * Location.MM_IN_METER
        arr[3] = (zp + 3) * Location.MM_IN_PK - 1 * Location.MM_IN_METER
        arr[4] = (zp + 4) * Location.MM_IN_PK  + 5 * Location.MM_IN_METER
        arr[5] = (zp + 5) * Location.MM_IN_PK  + 5 * Location.MM_IN_METER
        arr[6] = (zp + 6) * Location.MM_IN_PK  + 5 * Location.MM_IN_METER
        arr[7] = (zp + 7) * Location.MM_IN_PK  + 5 * Location.MM_IN_METER
        arr[8] = (zp + 8) * Location.MM_IN_PK  + 2 * Location.MM_IN_METER
        arr[9] = (zp + 9) * Location.MM_IN_PK  + 2 * Location.MM_IN_METER

        /**
         * 0  -   0
         * 1  -   100 000
         * 2  -   100 000
         * ......
         * 7  -   800 000
         * 9  -   900 000
         * 10 - 1 000 000
         * 11 - 1 099 000
         * 12 - 1 199 000
         * 13 - 1 299 000
         * 14 - 1 405 000
         * 15 - 1 505 000
         * 16 - 1 605 000
         * 17 - 1 705 000
         * 18 - 1 805 000
         * 19 - 1 902 000
         * 20 - 2 002 000
         * 21 - 2 102 000
         */

        mapping = new Pk2MmMap(zp, arr)
        (0..10).each {
            assert mapping[it] == it * Location.MM_IN_PK
        }
        (11..13).each {
            assert mapping[it] == (it * Location.MM_IN_PK - 1 * Location.MM_IN_METER)
        }
        (14..17).each {
            assert mapping[it] == (it * Location.MM_IN_PK + 5 * Location.MM_IN_METER)
        }
        (18..25).each {
            assert mapping[it] == (it * Location.MM_IN_PK + 2 * Location.MM_IN_METER)
        }
    }
}
