package com.r3.tree;

import javax.validation.constraints.NotNull;

public interface TreeNodeAware {

    public <T extends TreeNode> T asTreeNode(@NotNull final String parentId);
}
