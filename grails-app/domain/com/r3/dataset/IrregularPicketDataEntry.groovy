package com.r3.dataset

class IrregularPicketDataEntry extends DataEntry<Double> {

    static belongsTo = [dataSet: IrregularPicketDataSet]

    static embedded = ['location']

    static constraints = {
        doubleValue(min: 0d)
//        pk(unique: ['km', 'dataSet'], min: 0, max: 9)
    }

    static transients = ['plus', 'values', 'valuesMap']


    double doubleValue = 100d

    @Override
    Double getValues() {
        return doubleValue
    }

    @Override
    Map<String, Object> getValuesMap() {
        return ['doubleValue': doubleValue]
    }
}
