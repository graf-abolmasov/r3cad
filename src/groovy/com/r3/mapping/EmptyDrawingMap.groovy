package com.r3.mapping

import com.r3.drawing.layout.Drawing
import com.r3.mapping.dataset.DataSetMap
import grails.util.Pair
import sun.reflect.generics.reflectiveObjects.NotImplementedException

/**
 * User: graf
 * Date: 9/25/13
 * Time: 2:12 AM
 */
class EmptyDrawingMap implements DrawingMap {

    EmptyDrawingMap(Drawing drawing) {
        if (!drawing) {
            throw new IllegalArgumentException("Drawing cannot be null")
        }
    }

    @Override
    Pk2MmMap getPk2mm() {
        return new Pk2MmMap(0, Collections.emptyList())
    }

    @Override
    DataSetMap dataSet(Long id) {
        throw new NotImplementedException()
    }

    @Override
    long getZeroX() {
        return 0
    }

    @Override
    long getZeroY() {
        return 0
    }

    @Override
    Pair<Integer, Integer> getBoundPickets() {
        return new Pair<Integer, Integer>(0, 0)
    }

    @Override
    long getLength() {
        return 0
    }
}