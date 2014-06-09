package com.r3.dataset

class SubgradeEdgeDataEntry extends DataEntry<SubgradeEdgeDataEntryReadOnly> {

    static embedded = ['location']

    static belongsTo = [dataSet: SubgradeEdgeDataSet]

    static constraints = {
//        plus(unique: ['km', 'pk', 'dataSet'], min: 0)
        rightValue(min: 0)
        rightRight(min: 0)
        leftValue(min: 0)
        leftLeft(min: 0)
    }


    int rightValue //in mm
    int rightRight //in mm
    int leftValue //in mm
    int leftLeft //in mm

    @Override
    SubgradeEdgeDataEntryReadOnly getValues() {
        return new SubgradeEdgeDataEntryReadOnly(km, pk, plus, rightValue, rightRight, leftValue, leftLeft)
    }

    @Override
    Map<String, Object> getValuesMap() {
        def result = [:]
        result['rightValue'] = 1.0 * rightValue / MM_IN_METER
        result['rightRight'] = 1.0 * rightRight / MM_IN_METER
        result['leftValue'] = 1.0 * leftValue / MM_IN_METER
        result['leftLeft'] = 1.0 * leftLeft / MM_IN_METER
        return result
    }
}
