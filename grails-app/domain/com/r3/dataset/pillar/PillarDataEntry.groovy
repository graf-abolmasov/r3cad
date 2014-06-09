package com.r3.dataset.pillar

import com.r3.dataset.DataEntry
import com.r3.dataset.DistanceToWayAware
import com.r3.dataset.KmPkPlus

class PillarDataEntry extends DataEntry<PillarDataEntryReadOnly> implements DistanceToWayAware {

    static embedded = ['location']

    static constraints = {
//        plus(unique: ['km', 'pk', 'dataSet'], min: 0)
        number(nullable: true)
        alternativeName(nullable: true)
        type(nullable: true)
        leftWireSuspensionType(nullable: true)
        rightWireSuspensionType(nullable: true)
        beforeIronCrampAnchorType(nullable: true)
        afterIronCrampAnchorType(nullable: true)
        dataSet(bindable: false)
        wireHeights(bindable: false)
        distancesToWays(bindable: false)
    }

    static hasMany = [wireHeights: WireHeight, distancesToWays: PillarDistanceToWay]

    static belongsTo = [dataSet: PillarDataSet]


    String number
    String alternativeName
    PillarType type = PillarType.STEEL

    WireSuspensionType leftWireSuspensionType
    WireSuspensionType rightWireSuspensionType

    IronCrampAnchorType beforeIronCrampAnchorType
    IronCrampAnchorType afterIronCrampAnchorType

    Collection<WireHeight> wireHeights
    Collection<PillarDistanceToWay> distancesToWays

    @Override
    PillarDataEntryReadOnly getValues() {
        return new PillarDataEntryReadOnly(km, pk, plus, number, alternativeName, type,
                leftWireSuspensionType, rightWireSuspensionType, beforeIronCrampAnchorType, afterIronCrampAnchorType)
    }

    @Override
    Map<String, Object> getValuesMap() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result['plus'] = plus
        result['number'] = number
        result['alternativeName'] = alternativeName
        result['type'] = type?.name() ?: ''
        result['leftWireSuspensionType'] = leftWireSuspensionType?.name() ?: ''
        result['rightWireSuspensionType'] = rightWireSuspensionType?.name() ?: ''
        result['beforeIronCrampAnchorType'] = beforeIronCrampAnchorType?.name() ?: ''
        result['afterIronCrampAnchorType'] = afterIronCrampAnchorType?.name() ?: ''
        return result;
    }
}
