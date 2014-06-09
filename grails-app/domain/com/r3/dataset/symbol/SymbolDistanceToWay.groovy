package com.r3.dataset.symbol

import com.r3.dataset.DistanceToWay

class SymbolDistanceToWay extends DistanceToWay {
    static belongsTo = [symbol: SymbolDataEntry]
}



