package com.r3.dataset;

import java.io.Serializable;
import java.util.Comparator;

/**
 * User: graf
 * Date: 11/5/13
 * Time: 10:44 PM
 */
public class LocationComparator implements Comparator<Location>, Serializable {

    @Override
    public int compare(Location o1, Location o2) {
        final long otherTotal = o2.toLong();
        final long total = o1.toLong();
        final long diff = total - otherTotal;
        return (int)(diff / Math.abs(diff));
    }
}
