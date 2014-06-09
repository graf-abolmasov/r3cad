package com.r3.dataset.pillar

import com.r3.dataset.DistanceToWay

class PillarDistanceToWay extends DistanceToWay {
    static belongsTo = [pillar: PillarDataEntry]

    static constraints = {
        railWay(unique: 'pillar')
    }
}
