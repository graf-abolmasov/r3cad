package com.r3.dataset;

import java.io.Serializable;

/**
 * User: graf
 * Date: 10/8/13
 * Time: 1:09 AM
 */
public class PlanElementsDataEntryReadOnly extends Location implements Serializable {
    public PlanElementsDataEntryReadOnly(int km, int pk, int plus, CurveType elementType, double angle,
                                         String angleAsString, int radius, int h, int t1, int t2, int length,
                                         int endKm, int endPk, int endPlus, int l1, int l2) {
        super(km, pk, plus);
        this.elementType = elementType;
        this.angle = angle;
        this.radius = radius;
        this.h = h;
        this.t1 = t1;
        this.t2 = t2;
        this.length = length;
        this.endKm = endKm;
        this.endPk = endPk;
        this.endPlus = endPlus;
        this.l1 = l1;
        this.l2 = l2;
        this.angleAsString = angleAsString;
    }

    private CurveType elementType;
    private double angle; //in degree
    private int radius; //in meter
    private int h; //in mm
    private int t1; //in mm
    private int t2; //in mm
    private int length; //in mm
    private int endKm;
    private int endPk;
    private int endPlus; //in mm
    private int l1; //in meter
    private int l2; //in meter
    private String angleAsString;

    public CurveType getElementType() {
        return elementType;
    }

    public double getAngle() {
        return angle;
    }

    public int getRadius() {
        return radius;
    }

    public int getH() {
        return h;
    }

    public int getT1() {
        return t1;
    }

    public int getT2() {
        return t2;
    }

    public int getLength() {
        return length;
    }

    public int getEndKm() {
        return endKm;
    }

    public int getEndPk() {
        return endPk;
    }

    public int getEndPlus() {
        return endPlus;
    }

    public int getL1() {
        return l1;
    }

    public int getL2() {
        return l2;
    }

    public String getAngleAsString() {
        return angleAsString;
    }
}
