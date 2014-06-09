package com.r3.dataset

class SleepersDataEntry extends DataEntry<SleepersDataEntryReadOnly> {

    static embedded = ['location', 'endLocation']

    static belongsTo = [dataSet: SleepersDataSet]

    static constraints = {
//        plus(unique: ['km', 'pk', 'dataSet'], min: 0)
    }


    KmPkPlus endLocation = new KmPkPlus()
    SleepersType sleepersType = SleepersType.WOODEN

    @Override
    SleepersDataEntryReadOnly getValues() {
        return new SleepersDataEntryReadOnly(km, pk, plus, endLocation, sleepersType)
    }

    @Override
    Map<String, Object> getValuesMap() {
        def result = [:]
        result['endLocation'] = 'struct'
        result['endLocation_km'] = endLocation.km
        result['endLocation_pk'] = endLocation.pk
        result['endLocation_plus'] = 1.0 * endLocation.plus / Location.MM_IN_METER
        result['sleepersType'] = sleepersType.name()
        return result
    }
}
