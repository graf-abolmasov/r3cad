package com.r3.tree

import com.r3.drawing.layout.RailsLayoutDrawing
import com.r3.drawing.layout.TrackLiningDiagramDrawing
import org.codehaus.groovy.grails.web.mapping.LinkGenerator

import javax.validation.constraints.NotNull

class TrackLiningDiagramDrawingTreeNode extends TreeNode<TrackLiningDiagramDrawing> {

    TrackLiningDiagramDrawingTreeNode(@NotNull final TrackLiningDiagramDrawing drawing,
                                      @NotNull final LinkGenerator linkGenerator) {
        super(drawing)
        this.parentId = getIdentity(drawing.project)
        this.editorUrl = linkGenerator.link(controller: 'drawing', action: 'edit', id: drawing.id)
    }

    @Override
    String getLabel() {
        return model.name
    }

    @Override
    Collection children() {
        return []
    }

    @Override
    boolean isLeaf() {
        return true
    }

    @Override
    String getIconClass() {
        return 'drawingIcon'
    }
}