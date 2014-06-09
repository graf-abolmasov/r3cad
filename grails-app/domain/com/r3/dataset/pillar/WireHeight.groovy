package com.r3.dataset.pillar

import com.r3.RailWay

class WireHeight {

    static belongsTo = [pillar: PillarDataEntry]

    static constraints = {
        height(min: 0)
    }

    static mapping = {
        railWay(fetch: 'join')
    }

    RailWay railWay

    int height // in mm
}
