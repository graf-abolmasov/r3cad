package com.r3.drawing.area

import com.r3.dataset.BaseDataSet

class StackedArea2DataSet {

    static belongsTo = [tableRow: StackedDrawArea, dataSet: BaseDataSet]

    static constraints = {
        tableRow(nullable: true)
        dataSet(nullable: true)
    }

    static mapping = {
        dataSet fetch: 'join'
    }

    boolean visible = true
}
