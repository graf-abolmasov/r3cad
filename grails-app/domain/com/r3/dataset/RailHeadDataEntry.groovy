package com.r3.dataset

class RailHeadDataEntry extends DataEntry<Double> {

    static embedded = ['location']

    static belongsTo = [dataSet: RailHeadDataSet]

    static transients = ['values', 'valuesMap']

    static constraints = {
//        plus(unique: ['km', 'pk', 'dataSet'], min: 0)
    }


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
