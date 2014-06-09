package com.r3.tree

import com.r3.dataset.DataSet
import org.codehaus.groovy.grails.web.mapping.LinkGenerator

import javax.validation.constraints.NotNull

class DataSetTreeNode extends TreeNode<DataSet> {

    private boolean visible

    private String label

    DataSetTreeNode(@NotNull final DataSet dataSet,
                    @NotNull final String parentId,
                    @NotNull final LinkGenerator linkGenerator,
                    boolean visible = true,
                    boolean clarifyRailWay = false) {

        super(dataSet)
        this.label = dataSet.title + (clarifyRailWay ? (dataSet.railWay ? " (${dataSet.railWay.label})" : '') : '')
        this.visible = visible
        this.parentId = parentId
        if (dataSet.metaInfo.calculated) {
            this.editorUrl = linkGenerator.link(controller: 'dataSet', action: 'edit', id: dataSet.id, params: [projectId: dataSet.projectId])
        } else {
            this.editorUrl = linkGenerator.link(resource: 'dataSet/dataEntry', action: 'index',
                    dataSetId: dataSet.id, params: [projectId: dataSet.projectId])
        }
    }

    @Override
    boolean isVisible() {
        return visible
    }

    @Override
    String getLabel() {
        return label
    }

    @Override
    boolean isLeaf() {
        return true
    }

    @Override
    String getIconClass() {
        return model.metaInfo.calculated ? 'calculator-icon' : 'table-edit-icon'
    }

    @Override
    Collection children() {
        return Collections.emptyList()
    }
}