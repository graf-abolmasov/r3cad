package com.r3.dataset;

public class DataSetMetaInfo {

    private final boolean calculated;

    private final boolean belongsToForever;

    public DataSetMetaInfo(boolean calculated, boolean belongsToForever) {
        this.calculated = calculated;
        this.belongsToForever = belongsToForever;
    }

    public boolean isCalculated() {
        return calculated;
    }

    public boolean isBelongsToForever() {
        return belongsToForever;
    }
}
