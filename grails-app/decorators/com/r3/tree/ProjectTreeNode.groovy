package com.r3.tree

import com.r3.Project
import org.codehaus.groovy.grails.web.mapping.LinkGenerator

import javax.validation.constraints.NotNull

/**
 * User: graf
 * Date: 9/27/13
 * Time: 11:51 PM
 */
class ProjectTreeNode extends TreeNode<Project> {

    ProjectTreeNode(@NotNull final Project project, @NotNull final LinkGenerator linkGenerator) {
        super(project)
        this.editorUrl = linkGenerator.link(resource: 'project', action: 'edit', id: project.id)
    }

    @Override
    String getLabel() {
        return model.name
    }

    @Override
    Collection children() {
        def result = []
        model.dataSets?.findAll { it.railWay == null }?.each { ds ->
            result << ds.asTreeNode(getIdentity())
        }
        model.railWays?.each { result << it.asTreeNode(getIdentity()) }
        model.drawings?.each { result << it.asTreeNode(getIdentity()) }
        return result
    }

    boolean addChild(@NotNull DataSetTreeNode child, TreeNode before) {
        if (child.model.metaInfo.belongsToForever || child.model.railWay == null) {
            return false
        }
        child.model.railWay = null
        child.model.save(flash: true, failOnError: true)
        return true
    }
}