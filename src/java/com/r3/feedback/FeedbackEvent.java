package com.r3.feedback;

/**
 * User: graf
 * Date: 10/6/13
 * Time: 7:10 AM
 */
public abstract class FeedbackEvent {

    private String action;

    protected FeedbackEvent(String action) {
        if (action == null || action.trim().isEmpty()) {
            throw new IllegalArgumentException("Action identifier cannot be null or empty");
        }
        this.action = action.trim();
    }

    final public String getAction() {
        return action;
    }

    public abstract boolean canMergeWith(FeedbackEvent event);

    public abstract void mergeWith(FeedbackEvent event);
}
