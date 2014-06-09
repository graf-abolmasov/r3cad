package com.r3.drawing.layout

import com.r3.Project
import com.r3.drawing.model.EmptyDrawingModel
import com.r3.drawing.PageSizeFormat
import com.r3.tree.TrackLiningDiagramDrawingTreeNode

class TrackLiningDiagramDrawing extends Drawing {

    @Override
    String getTemplate() {
        return 'emptyDrawing/layout'
    }

    @Override
    EmptyDrawingModel getModel() {
        return new EmptyDrawingModel(this)
    }

    @Override
    TrackLiningDiagramDrawingTreeNode asTreeNode(String parentId) {
        return new TrackLiningDiagramDrawingTreeNode(this, grailsLinkGenerator)
    }
}
