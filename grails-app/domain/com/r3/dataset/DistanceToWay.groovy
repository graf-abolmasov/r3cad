package com.r3.dataset

import com.r3.RailWay

/**
 * User: graf
 * Date: 10/28/13
 * Time: 11:24 AM
 */
class DistanceToWay {

    static mapping = {
        railWay(fetch: 'join')
    }

    RailWay railWay

    int distance //in mm
}
