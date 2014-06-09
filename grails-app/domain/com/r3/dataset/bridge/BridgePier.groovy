package com.r3.dataset.bridge

class BridgePier {

    static constraints = {
        km(min: 0)
        pk(min: 0, max: 9)
        plus(min: 0)
    }

    static belongsTo = [dataEntry: BridgeDataEntry]

    int km
    int pk
    int plus //in mm
}
