package com.r3.dataset.bridge

import com.r3.dataset.DataEntry
import com.r3.dataset.KmPkPlus

class BridgeDataEntry extends DataEntry<BridgeDataEntryReadOnly> {

    static constraints = {
//        plus(unique: ['km', 'pk', 'dataSet'], min: 0)
        length(min: 0)
        beginRailHeadValue(min: 0d)
        axisRailHeadValue(min: 0d)
        endRailHeadValue(min: 0d)
        lengthAbutmentLeft(min: 0)
        lengthAbutmentRight(min: 0)
        waterbodyName(nullable: true)
    }

    static hasMany = [piersValues: BridgePier, spansValues: BridgeSpan]

    static belongsTo = [dataSet: BridgeDataSet]

    static embedded = ['location','axisLocation', 'endLocation']

    int length //in mm
    double beginRailHeadValue

    KmPkPlus axisLocation = new KmPkPlus()
    double axisRailHeadValue

    KmPkPlus endLocation = new KmPkPlus()
    double endRailHeadValue

    int lengthAbutmentLeft //in mm
    int lengthAbutmentRight //in mm

    String waterbodyName
    WaterFlowDirection waterFlowDirection = WaterFlowDirection.NONE
    FlooringType flooringType = FlooringType.ON_BALLAST

    Collection<BridgePier> piersValues
    Collection<BridgeSpan> spansValues

    @Override
    BridgeDataEntryReadOnly getValues() {
        return new BridgeDataEntryReadOnly(km, pk, plus, length, beginRailHeadValue, axisLocation, axisRailHeadValue,
                endLocation, endRailHeadValue, lengthAbutmentLeft, lengthAbutmentRight, waterbodyName, waterFlowDirection,
                flooringType, piersValues, spansValues)
    }

    @Override
    Map<String, Object> getValuesMap() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result['axisLocation'] = [:]
        result['axisLocation']['km'] = axisLocation?.km ?: 0
        result['axisLocation']['pk'] = axisLocation?.pk ?: 0
        result['axisLocation']['plus'] = axisLocation?.plus ? 1.0 * axisLocation.plus / MM_IN_METER : 0
        result['axisLocation_km'] = axisLocation?.km ?: 0
        result['axisLocation_pk'] = axisLocation?.pk ?: 0
        result['axisLocation_plus'] = axisLocation?.plus ? 1.0 * axisLocation.plus / MM_IN_METER : 0

        result['endLocation'] = [:]
        result['endLocation']['km'] = endLocation?.km ?: 0
        result['endLocation']['pk'] = endLocation?.pk ?: 0
        result['endLocation']['plus'] = endLocation?.plus ? 1.0 * endLocation.plus / MM_IN_METER : 0
        result['endLocation_km'] = endLocation?.km ?: 0
        result['endLocation_pk'] = endLocation?.pk ?: 0
        result['endLocation_plus'] = endLocation?.plus ? 1.0 * endLocation.plus / MM_IN_METER : 0

        result['length'] = length ? 1.0 * length / MM_IN_METER : 0
        result['flooringType'] = flooringType.name()
        result['beginRailHeadValue'] = beginRailHeadValue
        result['axisRailHeadValue'] = axisRailHeadValue
        result['endRailHeadValue'] = endRailHeadValue
        result['lengthAbutmentLeft'] = lengthAbutmentLeft ? 0 : 1.0 * lengthAbutmentLeft / MM_IN_METER
        result['lengthAbutmentRight'] = lengthAbutmentRight ? 0 : 1.0 * lengthAbutmentRight / MM_IN_METER
        result['waterbodyName'] = waterbodyName
        result['waterFlowDirection'] = waterFlowDirection.name()
        return result;
    }
}
