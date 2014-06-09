package com.r3.dataset;

import java.io.Serializable;

/**
 * User: Felix-13
 * Date: 22.10.13
 * Time: 21:23
 */
public class SubgradeEdgeDataEntryReadOnly extends Location implements Serializable {

    private int rightValue; //in mm
    private int rightRight; //in mm
    private int leftValue; //in mm
    private int leftLeft;//in mm

    public SubgradeEdgeDataEntryReadOnly(int km, int pk, int plus, int rightValue, int rightRight, int leftValue, int leftLeft) {
        super(km, pk, plus);
        this.rightValue = rightValue;
        this.rightRight = rightRight;
        this.leftValue = leftValue;
        this.leftLeft = leftLeft;
    }

    public int getRightValue() {
        return rightValue;
    }

    public int getRightRight() {
        return rightRight;
    }

    public int getLeftValue() {
        return leftValue;
    }

    public int getLeftLeft() {
        return leftLeft;
    }
}
