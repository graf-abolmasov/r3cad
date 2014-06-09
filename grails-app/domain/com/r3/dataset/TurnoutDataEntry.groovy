package com.r3.dataset

import com.r3.dataset.turnout.*

class TurnoutDataEntry extends DataEntry<TurnoutDataEntryReadOnly> {

    static constraints = {
        number(min: 0)
        numberSecond(min: 0)
        adjacentLinkLocation(nullable: true)
        stockRailJointLocation(nullable: true)
        tongueLocation(nullable: true)
        tailLocation(nullable: true)
        afterCrossLinkLocation(nullable: true)
        stockRailJointLocationSecond(nullable: true)
        tongueLocationSecond(nullable: true)
        tailLocationSecond(nullable: true)
        afterCrossLinkLocationSecond(nullable: true)
    }

    static embedded = ['location', 'adjacentLinkLocation', 'stockRailJointLocation', 'tongueLocation', 'tailLocation',
            'afterCrossLinkLocation', 'stockRailJointLocationSecond', 'tongueLocationSecond',
            'tailLocationSecond', 'afterCrossLinkLocationSecond']


    int number
    int numberSecond
    TurnoutType turnoutType = TurnoutType.NORMAL
    Direct direct = Direct.DIRECT
    Orientation orientation = Orientation.RIGHT
    Model model = Model.NONE
    RailsType railsType = RailsType.NONE
    SleepersType sleepersType = SleepersType.WOODEN
    ControlType controlType = ControlType.NONE

    static belongsTo = [dataSet: TurnoutDataSet]

    KmPkPlus adjacentLinkLocation //Примыкающее звено
    double adjacentLinkRailHeadValue

    KmPkPlus stockRailJointLocation //Стык рамного рельса
    double stockRailJointRailHeadValue
    KmPkPlus tongueLocation // Остряк
    double tongueRailHeadValue
    KmPkPlus tailLocation // Хвост
    double tailRailHeadValue
    KmPkPlus afterCrossLinkLocation // Закрестовинное звено
    double afterCrossLinkRailHeadValue

    KmPkPlus stockRailJointLocationSecond //Стык рамного рельса
    double stockRailJointRailHeadValueSecond
    KmPkPlus tongueLocationSecond // Остряк
    double tongueRailHeadValueSecond
    KmPkPlus tailLocationSecond // Хвост
    double tailRailHeadValueSecond
    KmPkPlus afterCrossLinkLocationSecond // Закрестовинное звено
    double afterCrossLinkRailHeadValueSecond

    @Override
    TurnoutDataEntryReadOnly getValues() {
        return new TurnoutDataEntryReadOnly(km, pk, plus, number, numberSecond, turnoutType, direct, orientation, model,
                railsType, sleepersType, controlType, adjacentLinkLocation, adjacentLinkRailHeadValue,
                stockRailJointLocation, stockRailJointRailHeadValue, tongueLocation, tongueRailHeadValue,
                tailLocation, tailRailHeadValue, afterCrossLinkLocation, afterCrossLinkRailHeadValue,
                stockRailJointLocationSecond, stockRailJointRailHeadValueSecond, tongueLocationSecond,
                tongueRailHeadValueSecond, tailLocationSecond, tailRailHeadValueSecond,
                afterCrossLinkLocationSecond, afterCrossLinkRailHeadValueSecond)
    }

    @Override
    Map<String, Object> getValuesMap() {
        def result = [:]
        result['number'] = number
        result['numberSecond'] = numberSecond
        result['turnoutType'] = turnoutType.name()
        result['direct'] = direct.name()
        result['orientation'] = orientation.name()
        result['model'] = model.name()
        result['railsType'] = railsType.name()
        result['sleepersType'] = sleepersType.name()
        result['controlType'] = controlType.name()

        result['adjacentLinkRailHead'] = [:]
        result['adjacentLinkRailHead']['km'] = adjacentLinkLocation?.km ?: 0
        result['adjacentLinkRailHead']['pk'] = adjacentLinkLocation?.pk ?: 0
        result['adjacentLinkRailHead']['plus'] = adjacentLinkLocation?.plus ?: 0
        result['adjacentLinkRailHead']['value'] = adjacentLinkRailHeadValue ?: 0

        result['stockRailJointRailHead'] = [:]
        result['stockRailJointRailHead']['km'] = stockRailJointLocation?.km ?: 0
        result['stockRailJointRailHead']['pk'] = stockRailJointLocation?.pk ?: 0
        result['stockRailJointRailHead']['plus'] = stockRailJointLocation?.plus ?: 0
        result['stockRailJointRailHead']['value'] = stockRailJointRailHeadValue ?: 0

        result['tongueRailHead'] = [:]
        result['tongueRailHead']['km'] = tongueLocation?.km ?: 0
        result['tongueRailHead']['pk'] = tongueLocation?.pk ?: 0
        result['tongueRailHead']['plus'] = tongueLocation?.plus ?: 0
        result['tongueRailHead']['value'] = tongueRailHeadValue ?: 0

        result['tailRailHead'] = [:]
        result['tailRailHead']['km'] = tailLocation?.km ?: 0
        result['tailRailHead']['pk'] = tailLocation?.pk ?: 0
        result['tailRailHead']['plus'] = tailLocation?.plus ?: 0
        result['tailRailHead']['value'] = tailRailHeadValue ?: 0

        result['afterCrossLinkRailHead'] = [:]
        result['afterCrossLinkRailHead']['km'] = afterCrossLinkLocation?.km ?: 0
        result['afterCrossLinkRailHead']['pk'] = afterCrossLinkLocation?.pk ?: 0
        result['afterCrossLinkRailHead']['plus'] = afterCrossLinkLocation?.plus ?: 0
        result['afterCrossLinkRailHead']['value'] = afterCrossLinkRailHeadValue ?: 0

        result['stockRailJointRailHeadSecond'] = [:]
        result['stockRailJointRailHeadSecond']['km'] = stockRailJointLocationSecond?.km ?: 0
        result['stockRailJointRailHeadSecond']['pk'] = stockRailJointLocationSecond?.pk ?: 0
        result['stockRailJointRailHeadSecond']['plus'] = stockRailJointLocationSecond?.plus ?: 0
        result['stockRailJointRailHeadSecond']['value'] = stockRailJointRailHeadValueSecond ?: 0

        result['tongueRailHeadSecond'] = [:]
        result['tongueRailHeadSecond']['km'] = tongueLocationSecond?.km ?: 0
        result['tongueRailHeadSecond']['pk'] = tongueLocationSecond?.pk ?: 0
        result['tongueRailHeadSecond']['plus'] = tongueLocationSecond?.plus ?: 0
        result['tongueRailHeadSecond']['value'] = tongueRailHeadValueSecond ?: 0

        result['tailRailHeadSecond'] = [:]
        result['tailRailHeadSecond']['km'] = tailLocationSecond?.km ?: 0
        result['tailRailHeadSecond']['pk'] = tailLocationSecond?.pk ?: 0
        result['tailRailHeadSecond']['plus'] = tailLocationSecond?.plus ?: 0
        result['tailRailHeadSecond']['value'] = tailRailHeadValueSecond ?: 0

        result['afterCrossLinkRailHeadSecond'] = [:]
        result['afterCrossLinkRailHeadSecond']['km'] = afterCrossLinkLocationSecond?.km ?: 0
        result['afterCrossLinkRailHeadSecond']['pk'] = afterCrossLinkLocationSecond?.pk ?: 0
        result['afterCrossLinkRailHeadSecond']['plus'] = afterCrossLinkLocationSecond?.plus ?: 0
        result['afterCrossLinkRailHeadSecond']['value'] = afterCrossLinkRailHeadValueSecond ?: 0

        return result
    }
}
