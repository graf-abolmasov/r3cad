package com.r3.dataset.bridge;

import org.springframework.context.MessageSourceResolvable;

/**
 * User: Felix-13
 * Date: 26.10.13
 * Time: 17:04
 */
public enum WaterFlowDirection implements MessageSourceResolvable {
    NONE,
    FROM_LEFT_TO_RIGHT,
    FROM_RIGHT_TO_LEFT,
    DRY,
    UNKNOWN;

    @Override
    public String[] getCodes() {
        return new String[]{getClass().getName() + "." + name()};
    }

    @Override
    public Object[] getArguments() {
        return new Object[0];
    }

    @Override
    public String getDefaultMessage() {
        return name();
    }

    public String getName() {
        return name();
    }
}
