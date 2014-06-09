package com.r3.drawing.model

import com.r3.dataset.Location
import com.r3.drawing.layout.ProfileDrawing
import com.r3.mapping.DataMappingService
import com.r3.mapping.DrawingMap
import com.r3.mapping.ProfileDrawingMap
import com.r3.utils.Utils

class ProfileDrawingModel implements DrawingModel {

    private Map<Long, Double> scaledX
    private ProfileDrawingMap map
    private int tableHeight
    private int width

    ProfileDrawingModel(final ProfileDrawing drawing, final DataMappingService mappingService) {
        this.map = new ProfileDrawingMap(drawing, mappingService)
        this.scaledX = new HashMap<>(map.pk2mm.size())
        this.tableHeight = drawing.rootTableRow.fullHeight
        this.width = Utils.scaleX(map.length)
    }

    @Override
    int getWidth() {
        return width
    }

    @Override
    ProfileDrawingMap getMap() {
        return map
    }

    @Override
    double X(long x) {
        return scaledX[x] ?: scaledX.putAt(x, Utils.scaleX(x - map.zeroX))
    }

    @Override
    double X(final Location x) {
        return X(map.pk2mm[x.toPk()] + x.plus)
    }

    int getTableHeight() {
        return tableHeight
    }
}
