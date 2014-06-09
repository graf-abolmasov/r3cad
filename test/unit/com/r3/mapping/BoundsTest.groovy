package com.r3.mapping

class BoundsTest extends GroovyTestCase {

    void testBounds() {

        assertBounds(new Bounds(0, null, 0, null), 0, null, 0, null)

        assertBounds(new Bounds(1, null, 3, null), 0, null, 0, null)

        assertBounds(new Bounds(1, 2, 3, null), 1, 2, 1, 2)

        assertBounds(new Bounds(1, null, 3, 4), 3, 4, 3, 4)

        assertBounds(new Bounds(1, 2, 3, 4), 1, 2, 3, 4)

        assertBounds(new Bounds(new Bounds(1, 2, 3, 4), 5, 6), 1, 2, 5, 6)

        assertBounds(new Bounds(1, 2, new Bounds(3, 4, 5, 6)), 1, 2, 5, 6)

    }

    private static void assertBounds(Bounds bounds, Integer leftBound, Long leftBoundId, Integer rightBound, Long rightBoundId) {
        assert bounds.leftBound == leftBound
        assert bounds.leftBoundId == leftBoundId
        assert bounds.rightBound == rightBound
        assert bounds.rightBoundId == rightBoundId
    }
}
