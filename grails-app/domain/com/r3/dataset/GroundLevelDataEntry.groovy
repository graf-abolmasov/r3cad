package com.r3.dataset

class GroundLevelDataEntry extends DataEntry<Double> {

    static embedded = ['location']

    static belongsTo = [dataSet: GroundLevelDataSet]

    static transients = ['values', 'valuesMap']


    double doubleValue

    @Override
    Double getValues() {
        return doubleValue
    }

    @Override
    Map<String, Object> getValuesMap() {
        return ['doubleValue': doubleValue]
    }
}
