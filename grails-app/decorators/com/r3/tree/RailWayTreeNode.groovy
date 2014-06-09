package com.r3.tree

import com.r3.RailWay
import org.codehaus.groovy.grails.web.mapping.LinkGenerator

import javax.validation.constraints.NotNull

class RailWayTreeNode extends TreeNode<RailWay> {

    RailWayTreeNode(@NotNull final RailWay railWay, @NotNull final LinkGenerator linkGenerator) {
        super(railWay)
        this.parentId = getIdentity(railWay.project)
        this.editorUrl = linkGenerator.link(resource: 'railWay', action: 'edit', id: railWay.id)
    }

    @Override
    String getLabel() {
        return model.label
    }

    @Override
    String getIconClass() {
        return 'railWayIcon'
    }

    @Override
    Collection children() {
        return model.dataSets?.collect { it.asTreeNode(getIdentity()) } ?: []
    }

    boolean addChild(@NotNull DataSetTreeNode child, TreeNode before) {
        if (child.model.metaInfo.belongsToForever || child.model.railWay == model) {
            return false
        }
        child.model.railWay = model
        return child.model.save(flash: true, failOnError: true) as boolean
    }
}