package com.r3.dataset.bridge

class BridgeSpan {

    static belongsTo = [dataEntry: BridgeDataEntry]

    static constraints = {
        length(min: 0)
        lumenLength(min: 0)
        ballast(min: 0)
        measurementBeginLeft(min: 0)
        measurementBeginRight(min: 0)
        measurementAxisLeft(min: 0)
        measurementAxisRight(min: 0)
        measurementEndLeft(min: 0)
        measurementEndRight(min: 0)
    }

    int length //in mm
    int lumenLength   //in mm

    int ballast // in mm

    int measurementBeginLeft
    int measurementBeginRight
    int measurementAxisLeft
    int measurementAxisRight
    int measurementEndLeft
    int measurementEndRight

    SpansType spansType = SpansType.METALLIC

    MovementType movementType = MovementType.UP
}
