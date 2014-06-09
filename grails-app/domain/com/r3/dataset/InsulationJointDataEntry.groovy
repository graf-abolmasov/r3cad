package com.r3.dataset

class InsulationJointDataEntry extends DataEntry<InsulationJointDataEntryReadOnly> {

    static embedded = ['location']

    static constraints = {
//        plus(unique: ['km', 'pk', 'dataSet'], min: 0)
        name(nullable: true)
    }


    String name
    InsulationJointUsingApATek useApATek = InsulationJointUsingApATek.USE

    static belongsTo = [dataSet: InsulationJointDataSet]

    @Override
    InsulationJointDataEntryReadOnly getValues() {
        return new InsulationJointDataEntryReadOnly(km, pk, plus, name, useApATek)
    }

    @Override
    Map<String, Object> getValuesMap() {
        def result = [:]
        result['name'] = name ?: ''
        result['useApATek'] = useApATek.name()
        return result
    }
}
