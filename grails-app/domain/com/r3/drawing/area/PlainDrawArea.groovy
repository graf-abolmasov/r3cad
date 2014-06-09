package com.r3.drawing.area

import com.r3.dataset.BaseDataSet
import com.r3.drawing.layout.Drawing
import com.r3.drawing.style.LineStyle
import com.r3.drawing.style.TextStyle
import com.r3.tree.PlainDrawAreaTreeNode
import com.r3.tree.TreeNodeAware

import javax.validation.constraints.NotNull

class PlainDrawArea implements TreeNodeAware {

    def grailsLinkGenerator

    static constraints = {
        label(blank: false)
        lineStyle(nullable: true)
        textStyle(nullable: true)
        representationRole(nullable: true)
    }

    static mapping = {
        dataSet fetch: 'join'
    }

    static transients = ['grailsLinkGenerator']

    static belongsTo = [drawing: Drawing]

    BaseDataSet dataSet

    boolean visible = true

    String label
    RepresentationRole representationRole

    LineStyle lineStyle
    TextStyle textStyle

    @Override
    PlainDrawAreaTreeNode asTreeNode(@NotNull final String parentId) {
        return new PlainDrawAreaTreeNode(this, grailsLinkGenerator)
    }
}
