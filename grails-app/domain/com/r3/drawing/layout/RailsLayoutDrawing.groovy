package com.r3.drawing.layout

import com.r3.Project
import com.r3.drawing.model.EmptyDrawingModel
import com.r3.drawing.PageSizeFormat
import com.r3.tree.RailsLayoutDrawingTreeNode
import com.r3.tree.TreeNode

class RailsLayoutDrawing extends Drawing {

    def grailsLinkGenerator

    @Override
    String getTemplate() {
        return 'emptyDrawing/layout'
    }

    @Override
    EmptyDrawingModel getModel() {
        return new EmptyDrawingModel(this)
    }

    @Override
    RailsLayoutDrawingTreeNode asTreeNode(String parentId) {
        return new RailsLayoutDrawingTreeNode(this, grailsLinkGenerator)
    }
}
