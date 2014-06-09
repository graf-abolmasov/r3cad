package com.r3.dataset

class InterWaySpaceDataEntry extends DataEntry<Double> {

    static embedded = ['location']

    static belongsTo = [dataSet: InterWaySpaceDataSet]

    static constraints = {
        doubleValue(min: 0d)
    }

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
