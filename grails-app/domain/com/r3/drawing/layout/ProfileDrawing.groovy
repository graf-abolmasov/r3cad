package com.r3.drawing.layout

import com.r3.drawing.model.ProfileDrawingModel
import com.r3.drawing.area.StackedDrawArea
import com.r3.tree.ProfileDrawingTreeNode

import javax.validation.constraints.NotNull

class ProfileDrawing extends Drawing {

    private static final TEMPLATE = 'profileDrawing/layout'

    def dataMappingService

    static mapping = {
        rootTableRow fetch: 'join', cascade: 'all'
    }

    StackedDrawArea rootTableRow

    @Override
    String getTemplate() {
        return TEMPLATE
    }

    @Override
    ProfileDrawingModel getModel() {
        return new ProfileDrawingModel(this, dataMappingService)
    }

    @Override
    ProfileDrawingTreeNode asTreeNode(@NotNull final String parentId) {
        return new ProfileDrawingTreeNode(this, grailsLinkGenerator)
    }
}
