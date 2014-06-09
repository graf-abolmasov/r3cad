package com.r3.tree

import com.r3.drawing.area.PlainDrawArea
import org.codehaus.groovy.grails.web.mapping.LinkGenerator

import javax.validation.constraints.NotNull

class PlainDrawAreaTreeNode extends TreeNode<PlainDrawArea> {

    PlainDrawAreaTreeNode(@NotNull final PlainDrawArea drawingArea,
                          @NotNull final LinkGenerator linkGenerator) {
        super(drawingArea)
        this.parentId = getIdentity(drawingArea.drawing)
        this.editorUrl = linkGenerator.link(resource: 'plainDrawArea', action: 'edit', id: drawingArea.id)
    }

    @Override
    boolean isVisible() {
        return model.visible
    }

    @Override
    String getLabel() {
        return model.label
    }

    @Override
    Collection children() {
        return [ model.dataSet.asTreeNode(getIdentity(), visible, false) ]
    }

    @Override
    String getIconClass() {
        return 'plainDrawAreaIcon'
    }

    boolean addChild(@NotNull DataSetTreeNode child, TreeNode before) {
        model.dataSet = child.model
        return model.save(flash: true, failOnError: true) as boolean
    }
}