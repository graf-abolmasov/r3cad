package com.r3.dataset

import com.r3.dataset.turnout.BallastDataEntryReadOnly

class BallastDataEntry extends DataEntry<BallastDataEntryReadOnly> {

    static embedded = ['location']

    static belongsTo = [dataSet: BallastDataSet]

    static constraints = {
//        plus(unique: ['km', 'pk', 'dataSet'], min: 0)
    }

    int layer0
    int layer1
    int layer2
    int layer3
    int layer4
    int layer5
    int layer6
    int layer7
    int layer8
    int layer9

    @Override
    BallastDataEntryReadOnly getValues() {
        return new BallastDataEntryReadOnly(km, pk, plus, layer0, layer1, layer2, layer3, layer4, layer5, layer6, layer7, layer8, layer9)
    }

    @Override
    Map<String, Object> getValuesMap() {
        def result = [:]
        result['layer0'] = layer0 / MM_IN_CM
        result['layer1'] = layer1 / MM_IN_CM
        result['layer2'] = layer2 / MM_IN_CM
        result['layer3'] = layer3 / MM_IN_CM
        result['layer4'] = layer4 / MM_IN_CM
        result['layer5'] = layer5 / MM_IN_CM
        result['layer6'] = layer6 / MM_IN_CM
        result['layer7'] = layer7 / MM_IN_CM
        result['layer8'] = layer8 / MM_IN_CM
        result['layer9'] = layer9 / MM_IN_CM
        return result
    }
}
