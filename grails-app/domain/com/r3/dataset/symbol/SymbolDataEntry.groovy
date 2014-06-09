package com.r3.dataset.symbol

import com.r3.dataset.DataEntry
import com.r3.dataset.DistanceToWayAware
import com.r3.dataset.KmPkPlus
import com.r3.dataset.SymbolDataEntryReadOnly
import com.r3.drawing.symbol.ConventionalSymbol

class SymbolDataEntry extends DataEntry<SymbolDataEntryReadOnly> implements DistanceToWayAware {

    static embedded = ['location']

    static belongsTo = [dataSet: SymbolDataSet]

    static hasMany = [distancesToWays: SymbolDistanceToWay]

    static transients = ['values', 'valuesMap', 'railWay']


    ConventionalSymbol symbolValue

    Collection<SymbolDistanceToWay> distancesToWays

    String label = "Новый знак"

    @Override
    SymbolDataEntryReadOnly getValues() {
        return new SymbolDataEntryReadOnly(this)
    }

    @Override
    Map<String, Object> getValuesMap() {
        def result = [:]
        result['symbolValue'] = [:]
        result['symbolValue']['id'] = symbolValueId
        result['symbolValue']['class'] = ConventionalSymbol.name
        result['label'] = label
        return result
    }
}
