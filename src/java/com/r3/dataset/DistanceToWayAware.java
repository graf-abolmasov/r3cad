package com.r3.dataset;

import java.util.Collection;

/**
 * User: graf
 * Date: 10/28/13
 * Time: 2:24 PM
 */
public interface DistanceToWayAware {
    Collection<? extends DistanceToWay> getDistancesToWays();
}
