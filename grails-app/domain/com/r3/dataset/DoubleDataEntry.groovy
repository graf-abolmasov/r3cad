package com.r3.dataset

class DoubleDataEntry extends DataEntry<Double> {

    static embedded = ['location']

    static belongsTo = [dataSet: DoubleDataSet]

    static transients = ['values', 'valuesMap']

    static constraints = {
        location(uniqueKmPkPlus: true)
    }

    double doubleValue

    @Override
    int compareTo(Location o) {
        def distanceDiff = super.compareTo(o)
        if (o instanceof DoubleDataEntry) {
            return distanceDiff != 0 ? distanceDiff : ((doubleValue - o.doubleValue) * 10000)
        } else {
            return distanceDiff
        }
    }

    @Override
    boolean equals(Object o) {
        if (!super.equals(o)) {
            return false
        }

        if (o instanceof DoubleDataEntry) {
            return o.doubleValue == this.doubleValue
        } else {
            return false
        }
    }

    @Override
    int hashCode() {
        return 31 * super.hashCode() + doubleValue
    }

    @Override
    Double getValues() {
        return doubleValue
    }

    @Override
    Map<String, Object> getValuesMap() {
        return ['doubleValue': doubleValue]
    }
}
