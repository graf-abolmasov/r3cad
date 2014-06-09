package com.r3

import static org.springframework.http.HttpStatus.NOT_FOUND

import com.r3.tree.TreeNode
import com.r3.tree.TreeNodeAware
import grails.converters.JSON
import grails.transaction.Transactional

import javax.validation.constraints.NotNull

class ProjectTreeController {

    static allowedMethods = [
            root    : 'GET',
            children: 'GET',
            move    : 'PUT'
    ]

    def root(Long projectId) {
        with(Project.read(projectId)) { Project project ->
            render([project.asTreeNode('')] as JSON)
        }
    }

    def children(Long projectId, String identity, String parentId) {
        def node = findNode(identity, parentId)
        if (node == null) {
            return root(projectId)
        }
        render(node.children() as JSON)
    }

    @Transactional
    def move(String identity) {
        def json = request.JSON
        with(findNode(identity, json['parentId'].toString())) { TreeNode node ->
            with(findNode(json['newParent'])) { TreeNode newParent ->
                with(findNode(json['oldParent'])) { TreeNode oldParent ->
                    final TreeNode before = findNode(json['before'])
                    render(moveNode(node, oldParent, newParent, before, params.boolean('copy')) as JSON)
                }
            }
        }
    }

    private TreeNode moveNode(@NotNull final TreeNode movedNode,
                              @NotNull final TreeNode oldParent,
                              @NotNull final TreeNode newParent,
                              final TreeNode before, Boolean copy) {
        if (!copy) {
            if (!oldParent.removeChild(movedNode)) {
                return movedNode
            }
        }

        if (!newParent.addChild(movedNode, before)) {
            return movedNode
        }

        final TreeNodeAware instance = movedNode.model
        return instance.asTreeNode(newParent.identity)
    }

    private void with(Object object, Closure c) {
        if (object) {
            c.call(object)
        } else {
            render(status: NOT_FOUND)
        }
    }

    private TreeNode findNode(final json) {
        if (json == null) {
            return null
        }
        return findNode(json['identity'].toString(), json['parentId'].toString())
    }

    private TreeNode findNode(final String identity, final String parentId) {
        if (identity == null) {
            return null
        }

        def (className, id) = identity.split('@')
        if (className == null || id == null) {
            return null
        }

        def domainClass = grailsApplication.getDomainClass(className)
        if (domainClass == null) {
            return null
        }

        TreeNodeAware instance = domainClass.clazz.get(id)
        if (instance == null) {
            return null
        }
        return instance.asTreeNode(parentId)
    }
}

