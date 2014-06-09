package com.r3.dataset.bridge;

import com.r3.dataset.KmPkPlus;
import com.r3.dataset.Location;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * User: graf
 * Date: 11/6/13
 * Time: 9:19 PM
 */
public class BridgeDataEntryReadOnly implements Serializable {

    public BridgeDataEntryReadOnly(int km, int pk, int plus, int length, double beginRailHeadValue,
                                   KmPkPlus axisLocation, double axisRailHeadValue, KmPkPlus endLocation,
                                   double endRailHeadValue, int lengthAbutmentLeft, int lengthAbutmentRight,
                                   String waterbodyName, WaterFlowDirection waterFlowDirection,
                                   FlooringType flooringType, Collection<BridgePier> piersValues,
                                   Collection<BridgeSpan> spansValues) {
        this.km = km;
        this.pk = pk;
        this.plus = plus;
        this.length = length;
        this.beginRailHeadValue = beginRailHeadValue;
        this.axisLocation = axisLocation.toLocation();
        this.axisRailHeadValue = axisRailHeadValue;
        this.endLocation = endLocation.toLocation();
        this.endRailHeadValue = endRailHeadValue;
        this.lengthAbutmentLeft = lengthAbutmentLeft;
        this.lengthAbutmentRight = lengthAbutmentRight;
        this.waterbodyName = waterbodyName;
        this.waterFlowDirection = waterFlowDirection;
        this.flooringType = flooringType;
        this.piersValues = piersValues;
        this.spansValues = spansValues;
    }

    private int km;
    private int pk;
    private int plus;
    private int length;
    private double beginRailHeadValue;

    private Location axisLocation;
    private double axisRailHeadValue;

    private Location endLocation ;
    private double endRailHeadValue;

    private int lengthAbutmentLeft;
    private int lengthAbutmentRight;
    private String waterbodyName;
    private WaterFlowDirection waterFlowDirection;
    private FlooringType flooringType;
    private Collection<BridgePier> piersValues;
    private Collection<BridgeSpan> spansValues;

    public int getKm() {
        return km;
    }

    public int getPk() {
        return pk;
    }

    public int getPlus() {
        return plus;
    }

    public int getLength() {
        return length;
    }

    public double getBeginRailHeadValue() {
        return beginRailHeadValue;
    }

    public Location getAxisLocation() {
        return axisLocation;
    }

    public double getAxisRailHeadValue() {
        return axisRailHeadValue;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public double getEndRailHeadValue() {
        return endRailHeadValue;
    }

    public int getLengthAbutmentLeft() {
        return lengthAbutmentLeft;
    }

    public int getLengthAbutmentRight() {
        return lengthAbutmentRight;
    }

    public String getWaterbodyName() {
        return waterbodyName;
    }

    public WaterFlowDirection getWaterFlowDirection() {
        return waterFlowDirection;
    }

    public FlooringType getFlooringType() {
        return flooringType;
    }

    public Collection<BridgePier> getPiersValues() {
        return Collections.unmodifiableCollection(piersValues);
    }

    public Collection<BridgeSpan> getSpansValues() {
        return Collections.unmodifiableCollection(spansValues);
    }
}
