package com.r3.dataset;

import java.io.Serializable;
import java.util.Map;

/**
 * User: graf
 * Date: 10/16/13
 * Time: 12:41 PM
 */
abstract public class DataEntry<T extends Serializable> extends Location {

    protected KmPkPlus location = new KmPkPlus();

    abstract public T getValues();

    abstract public Map<String, Object> getValuesMap();

    public KmPkPlus getLocation() {
        return location;
    }

    public void setLocation(final KmPkPlus location) {
        this.location = location;
    }

    public void setLocation(final Location location) {
        final KmPkPlus result = new KmPkPlus();
        result.setKm(location.getKm());
        result.setPk(location.getPk());
        result.setPlus(location.getPlus());
        this.location = result;
    }

    @Override
    public int getKm() {
        return location.getKm();
    }

    @Override
    public int getPk() {
        return location.getPk();
    }

    @Override
    public int getPlus() {
        return location.getPlus();
    }
}
