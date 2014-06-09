package com.r3.drawing.model;

import com.r3.dataset.Location;
import com.r3.mapping.DrawingMap;

/**
 * Это можель чертежа в нужных масштабах
 */
public interface DrawingModel {

    public int getWidth();

    public <T extends DrawingMap> T getMap();

    public double X(long x);

    public double X(Location x);
}
