package com.r3.feedback;

import com.r3.tree.ProjectTreeNode;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * User: graf
 * Date: 10/6/13
 * Time: 7:12 AM
 */
public class RefreshProjectTreeEvent extends FeedbackEvent {

    private static final String ACTION = "REFRESH_PROJECT_TREE";

    private Set<ProjectTreeNode> nodes;

    public RefreshProjectTreeEvent() {
        super(ACTION);
        nodes = new HashSet<ProjectTreeNode>();
    }

    public RefreshProjectTreeEvent(ProjectTreeNode... nodes) {
        this();
        if (nodes != null) {
            Collections.addAll(this.nodes, nodes);
        }
    }

    public Set<ProjectTreeNode> getNodes() {
        return nodes;
    }

    @Override
    public boolean canMergeWith(FeedbackEvent event) {
        return event != null && ACTION.equals(event.getAction());
    }

    @Override
    public void mergeWith(FeedbackEvent event) {
        nodes.addAll(((RefreshProjectTreeEvent) event).nodes);
    }
}
