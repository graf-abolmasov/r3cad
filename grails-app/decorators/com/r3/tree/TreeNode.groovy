package com.r3.tree

import javax.validation.constraints.NotNull

public abstract class TreeNode<T> {

    protected T model

    protected String identity

    protected String parentId

    protected String editorUrl

    public TreeNode(T model) {
        this.model = model
        this.identity = getIdentity(model)
    }

    abstract String getLabel()

    abstract Collection<TreeNode> children()
    
    boolean removeChild(@NotNull TreeNode child) {
        return true
    }

    boolean addChild(@NotNull TreeNode child, TreeNode before) {
        return false
    }

    T getModel() {
        return model
    }

    String getIdentity() {
        return identity
    }

    String getParentId() {
        return parentId
    }

    String getEditorUrl() {
        return editorUrl
    }

    String getIconClass() {
        return ''
    }

    boolean isLeaf() {
        return false
    }

    boolean isVisible() {
        return true
    }

    protected static String getIdentity(final Object object) {
        return object.class.name + '@' + object.id
    }
}
