package com.r3.tree

import com.r3.drawing.layout.ProfileDrawing
import org.codehaus.groovy.grails.web.mapping.LinkGenerator

import javax.validation.constraints.NotNull

class ProfileDrawingTreeNode extends TreeNode<ProfileDrawing> {

    ProfileDrawingTreeNode(@NotNull final ProfileDrawing drawing, @NotNull final LinkGenerator linkGenerator) {
        super(drawing)
        this.parentId = getIdentity(drawing.project)
        this.editorUrl = linkGenerator.link(controller: 'drawing', action: 'edit', id: drawing.id)
    }

    @Override
    String getLabel() {
        return model.name
    }

    @Override
    String getIconClass() {
        return 'drawingIcon'
    }

    @Override
    Collection children() {
        def result = []
        model.rootTableRow?.children?.each { result << it.asTreeNode(identity) }
        model.rows?.each { result << it.asTreeNode(identity) }
        return result
    }

    boolean addChild(@NotNull StackedDrawAreaTreeNode child, StackedDrawAreaTreeNode before) {
        child.model.parent = model.rootTableRow
        if (before != null) {
            child.model.orderNumber = before.model.orderNumber - 1
        }
        return child.model.save(flash: true, failOnError: true) as boolean
    }
}