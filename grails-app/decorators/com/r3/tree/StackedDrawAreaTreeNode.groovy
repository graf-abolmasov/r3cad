package com.r3.tree

import com.r3.drawing.area.StackedArea2DataSet
import com.r3.drawing.area.StackedDrawArea
import org.codehaus.groovy.grails.web.mapping.LinkGenerator

import javax.validation.constraints.NotNull

class StackedDrawAreaTreeNode extends TreeNode<StackedDrawArea> {

    StackedDrawAreaTreeNode(@NotNull final StackedDrawArea drawingArea,
                            @NotNull final String parentId,
                            @NotNull final LinkGenerator linkGenerator) {
        super(drawingArea)
        this.parentId = getIdentity(drawingArea.drawing ?: drawingArea.parent)
        this.editorUrl = linkGenerator.link(resource: 'stackedDrawArea', action: 'edit', id: drawingArea.id)
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
        def result = []
        def hasChildren = model.children as boolean
        model.dataSets?.each { result << it.dataSet.asTreeNode(identity, !hasChildren, true) }
        model.children?.each { result << it.asTreeNode(identity) }
        return result
    }

    boolean removeChild(@NotNull DataSetTreeNode child) {
        def stackedArea2DataSet = StackedArea2DataSet.findByTableRowAndDataSet(model, child.model)
        if (stackedArea2DataSet == null) {
            return false
        }
        stackedArea2DataSet.delete(flush: true)
        return true
    }

    boolean addChild(@NotNull DataSetTreeNode dataSet, TreeNode before) {
        if (StackedArea2DataSet.countByTableRowAndDataSet(model, dataSet.model) != 0) {
            return false
        }
        new StackedArea2DataSet(dataSet: dataSet.model, tableRow: model).save(flash: true, failOnError: true) as boolean
    }

    boolean addChild(@NotNull StackedDrawAreaTreeNode child, StackedDrawAreaTreeNode before) {
        child.model.parent = model
        if (before != null) {
            child.model.orderNumber = model.orderNumber - 1
        }
        child.model.save(flash: true, failOnError: false) as boolean
    }

    @Override
    String getIconClass() {
        return 'stackedDrawAreaIcon'
    }
}