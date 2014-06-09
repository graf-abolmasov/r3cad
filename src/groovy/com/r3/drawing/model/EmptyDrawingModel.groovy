package com.r3.drawing.model

import com.r3.dataset.Location
import com.r3.drawing.layout.Drawing
import com.r3.mapping.EmptyDrawingMap

class EmptyDrawingModel implements DrawingModel {

    private EmptyDrawingMap map

    EmptyDrawingModel(Drawing drawing) {
        this.map = new EmptyDrawingMap(drawing)
    }

    @Override
    int getWidth() {
        return 0
    }

    @Override
    EmptyDrawingMap getMap() {
        return map
    }

    @Override
    double X(long x) {
        return 0
    }

    @Override
    double X(Location x) {
        return 0
    }
}
