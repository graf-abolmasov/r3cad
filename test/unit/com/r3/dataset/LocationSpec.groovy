package com.r3.dataset

import spock.lang.Specification

class LocationSpec extends Specification {

    void '#plus'() {
        expect:
        a + b == c

        where:
        a                         | b                                   || c
        new Location(1, 2, 3)     | 4                                   || new Location(1, 2, 7)
        new Location(1, 2, 3)     | 4 * Location.MM_IN_KM               || new Location(5, 2, 3)
        new Location(0, 8, 3)     | 5 * Location.MM_IN_PK + 4           || new Location(1, 3, 7)
        new Location(0, 8, 99999) | 1 * Location.MM_IN_PK + 1           || new Location(1, 0, 0)

        new Location(1, 2, 3)     | 4l                                  || new Location(1, 2, 7)
        new Location(1, 2, 3)     | 4l * Location.MM_IN_KM              || new Location(5, 2, 3)
        new Location(0, 8, 3)     | 5l * Location.MM_IN_PK + 4          || new Location(1, 3, 7)
        new Location(0, 8, 99999) | 1l * Location.MM_IN_PK + 1          || new Location(1, 0, 0)

        new Location(1, 2, 3)     | new Location(2, 3, 4)               || new Location(3, 5, 7)
        new Location(0, 8, 3)     | new Location(0, 5, 4)               || new Location(1, 3, 7)
        new Location(0, 8, 99999) | new Location(0, 1, 1)               || new Location(1, 0, 0)
    }

    void '#compareTo vs #equals vs #hashCode contract'() {
        when:
        def a = new Location(1, 2, 3)
        def b = new Location(1, 2, 3)
        def c = new Location(2, 2, 3)

        then:
        a != null
        !a.equals(null)

        a == a
        a.equals(a)
        a.compareTo(a) == 0

        a == b
        a.equals(b)
        a.compareTo(b) == 0
        a.hashCode() == b.hashCode()

        a != c
        a < c
        !a.equals(c)
        a.compareTo(c) < 0
        a.hashCode() != c.hashCode()
    }

    void '#equals'() {
        expect:
        a.equals(b) == isEquals

        where:
        a                     | b                     || isEquals
        new Location(1, 2, 3) | new Location(1, 2, 3) || true
        new Location(1, 2, 3) | new Location(2, 2, 3) || false
        new Location(1, 2, 3) | new Location(1, 3, 3) || false
        new Location(1, 2, 3) | new Location(1, 2, 4) || false
    }

    void '#Location'() {
        expect:
        location.km == km
        location.pk == pk
        location.plus == plus

        where:
        location                                        || km || pk || plus
        new Location(1, 1, 1)                           || 1  || 1  || 1
        new Location(1, 10, 1)                          || 2  || 0  || 1
        new Location(1, 15, 1)                          || 2  || 5  || 1
        new Location(1, 20, 1)                          || 3  || 0  || 1
        new Location(1, 8, 100 * Location.MM_IN_METER)  || 1  || 9  || 0
        new Location(1, 8, 150 * Location.MM_IN_METER)  || 1  || 9  || 50 * Location.MM_IN_METER
        new Location(1, 8, 200 * Location.MM_IN_METER)  || 2  || 0  || 0
        new Location(1, 9, 100 * Location.MM_IN_METER)  || 2  || 0  || 0
        new Location(1, 11, 100 * Location.MM_IN_METER) || 2  || 2  || 0
    }

    void '#toPk'() {
        expect:
        location.toPk() == pk

        where:
        location              || pk
        new Location(0, 0, 0) || 0
        new Location(0, 1, 0) || 1
        new Location(1, 0, 0) || 10
        new Location(1, 1, 0) || 11
        new Location(1, 1, 2) || 11
    }

    void 'sorting'() {
        given:
        def set = new TreeSet<Location>()

        when:
        set.add(new Location(2, 3, 4))
        set.add(new Location(1, 2, 3))

        then:
        assert set.first() == new Location(1, 2, 3)
        assert set.last() == new Location(2, 3, 4)
    }
}
