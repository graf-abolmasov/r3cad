package com.r3.dataset;

import java.io.Serializable;

/**
 * User: Felix-13
 * Date: 19.10.13
 * Time: 21:43
 */
public class SleepersDataEntryReadOnly extends Location implements Serializable {

    private Location endLocation;
    private SleepersType sleepers = SleepersType.WOODEN;

    public SleepersDataEntryReadOnly(int km, int pk, int plus, KmPkPlus endLocation, SleepersType sleepers) {
        super(km, pk, plus);
        this.endLocation = endLocation.toLocation();
        this.sleepers = sleepers;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public SleepersType getSleepers() {
        return sleepers;
    }
}
